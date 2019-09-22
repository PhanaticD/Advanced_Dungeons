package greymerk.roguelike.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

//import org.apache.commons.io.FilenameUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.SettingsContainer;
import greymerk.roguelike.dungeon.settings.SettingsResolver;
import greymerk.roguelike.dungeon.tasks.DungeonTaskRegistry;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
//import net.minecraft.block.material.Material;
//import net.minecraft.world.biome.Biome;
//import net.minecraftforge.common.BiomeDictionary;
//import net.minecraftforge.common.BiomeDictionary.Type;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;
import zhehe.advanceddungeons.AdvancedDungeons;
import static zhehe.advanceddungeons.AdvancedDungeons.configDirName;
import zhehe.advanceddungeons.util.BiomeDictionary;
import zhehe.advanceddungeons.util.BiomeDictionary.Type;

public class Dungeon implements IDungeon{
        public static int count = 0;
        public static boolean isSpawning = false;
	public static final int VERTICAL_SPACING = 10;
	public static final int TOPLEVEL = 50;
	
	private static final String SETTINGS_DIRECTORY = configDirName + "/settings";
	public static SettingsResolver settingsResolver;
	
	
	private Coord origin;
	private List<IDungeonLevel> levels;

	private IWorldEditor editor;
        
        private static class Node {
            int x, z;
            public Node(int x, int z) {
                this.x = x;
                this.z = z;
            }
            @Override
            public String toString() {
                return "["+ x + "," + z + "]";
            }
        }
        public final static List<Node> queue = new ArrayList<>();
        private static int max_len = 8;
        private static double THRESHOLD = 60;
        
        private static class SyncBuffer {
            public Set<String> buffer = new HashSet<>();
            public LinkedList<String> bufferl = new LinkedList<>();
            public int buffer_len = 512 * 16 * 16;
        }
        private final static SyncBuffer buffer = new SyncBuffer();
	
	static{
		try{
			RogueConfig.reload(false);
			initResolver();
		} catch(Exception e) {
			// do nothing
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        Bukkit.getLogger().log(Level.SEVERE, sw.toString());
		}
	}
	
	public static void initResolver() throws Exception{
		File settingsDir = new File(SETTINGS_DIRECTORY);
		
		if(settingsDir.exists() && !settingsDir.isDirectory()){
			throw new Exception("Settings directory is a file");
		}
		
		if(!settingsDir.exists()){
			settingsDir.mkdir();
		}
		
		File[] settingsFiles = settingsDir.listFiles();
		Arrays.sort(settingsFiles);
		
		SettingsContainer settings = new SettingsContainer();
		settingsResolver = new SettingsResolver(settings);
		
		Map<String, String> files = new HashMap<>();
		
		for(File file : Arrays.asList(settingsFiles)){
			
			if(!FilenameUtils.getExtension(file.getName()).equals("json")) continue;
			
			try {
				String content = Files.toString(file, Charsets.UTF_8);
				files.put(file.getName(), content); 
			} catch (IOException e) {				
				throw new Exception("Error reading file : " + file.getName());
			}
		}

		settings.parseCustomSettings(files);			
	}
	
	public Dungeon(IWorldEditor editor){
		this.editor = editor;
		this.levels = new ArrayList<>();
	}
	
	public void generateNear(Random rand, int x, int z){
//            Bukkit.getLogger().log(Level.SEVERE, "1");
		if(Dungeon.settingsResolver == null) return;
//		Bukkit.getLogger().log(Level.SEVERE, "1.5");
		int attempts = 50;
		
		for(int i = 0;i < attempts;i++){
//                    Bukkit.getLogger().log(Level.SEVERE, "1.6");
			Coord location = getNearbyCoord(rand, x, z, 40, 100);
			
			if(!validLocation(rand, location)) continue;
//			Bukkit.getLogger().log(Level.SEVERE, "1.65");
			ISettings setting;
			
			try{
				setting = Dungeon.settingsResolver.getSettings(editor, rand, location);	
//                                setting = new SettingsRandom(rand); //***
			} catch(Exception e){
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                e.printStackTrace(pw);
                                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
//				e.printStackTrace();
				return;
			}
			 
//			Bukkit.getLogger().log(Level.SEVERE, "1.75");
			if(setting == null) return;
//			Bukkit.getLogger().log(Level.SEVERE, "2");
                        synchronized(queue) {
                            for(Node n : queue) {
                                int dx = (x-4)/16 - n.x;
                                int dz = (z-4)/16 - n.z;
                                double distance = Math.sqrt(dx*dx+dz*dz);
                                if(distance < THRESHOLD) return;
                            }
                            
                            queue.add(new Node((x-4)/16, (z-4)/16));
                            if(queue.size() > max_len) queue.remove(0);
                            count++;
                        }
			int size = generate(setting, location);

//                        Bukkit.getLogger().log(Level.SEVERE, location.getX() + "," + location.getY() + "," + location.getZ());
                        AdvancedDungeons.logMessage("Place dungeon @" + editor.getWorldName() + " x=" + location.getX() + ", z=" + location.getZ()+",size:"+size);
//			Bukkit.getLogger().log(Level.SEVERE, "3");
			return;
		}
	}
	
        @Override
	public int generate(ISettings settings, Coord pos){
		this.origin = new Coord(pos.getX(), Dungeon.TOPLEVEL, pos.getZ());
		return DungeonGenerator.generate(editor, this, settings, DungeonTaskRegistry.getTaskRegistry());	
	}
	
	public static boolean canSpawnInChunk(int chunkX, int chunkZ, IWorldEditor editor){
		
		if(!RogueConfig.getBoolean(RogueConfig.DONATURALSPAWN)) return false;
//                if(isSpawning) return false;
		
//		String dim = editor.getInfo(new Coord(chunkX * 16, 0, chunkZ * 16)).getDimension();
//		List<String> wl = new ArrayList<>();
//		wl.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONWL));
//		List<String> bl = new ArrayList<>();
//		bl.addAll(RogueConfig.getIntList(RogueConfig.DIMENSIONBL));
//		if(!SpawnCriteria.isValidDimension(dim, wl, bl)) return false;
		
		if(!isVillageChunk(editor, chunkX, chunkZ)) return false;
		
		double spawnChance = RogueConfig.getDouble(RogueConfig.SPAWNCHANCE);
		Random rand = new Random(Objects.hash(chunkX, chunkZ, 31));
		
                if(rand.nextFloat() < spawnChance) {
                    synchronized(queue) {
                            for(Node n : queue) {
                                int dx = chunkX - n.x;
                                int dz = chunkZ - n.z;
                                double distance = Math.sqrt(dx*dx+dz*dz);
                                if(distance < THRESHOLD) return false;
                            }
                    }
                    return true;
                }
                return false;
	}
	
	public static boolean isVillageChunk(IWorldEditor editor, int chunkX, int chunkZ){
            // TODO
            return true;
//		int frequency = RogueConfig.getInt(RogueConfig.SPAWNFREQUENCY);
//		int min = 8 * frequency / 10;
//		int max = 32 * frequency / 10;
//		
//		min = min < 2 ? 2 : min;
//		max = max < 8 ? 8 : max;
//		
//		int tempX = chunkX < 0 ? chunkX - (max - 1) : chunkX;
//		int tempZ = chunkZ < 0 ? chunkZ - (max - 1) : chunkZ;
//
//		int m = tempX / max;
//		int n = tempZ / max;
//		
//		Random r = editor.getSeededRandom(m, n, 10387312);
//		
//		m *= max;
//		n *= max;
//		
//		m += r.nextInt(max - min);
//		n += r.nextInt(max - min);
//		
//		return chunkX == m && chunkZ == n;
	}
	
        @Override
	public void spawnInChunk(Random rand, int chunkX, int chunkZ) {
		if(Dungeon.canSpawnInChunk(chunkX, chunkZ, editor)){
                    
			int x = chunkX * 16 + 4;
			int z = chunkZ * 16 + 4;
//			isSpawning = true;
			generateNear(rand, x, z);
//                        isSpawning = false;
		}
	}
        
        public void forceSpawnInChunk(Random rand, int chunkX, int chunkZ) {
		int x = chunkX * 16 + 4;
		int z = chunkZ * 16 + 4;
			
		generateNear(rand, x, z);
	}
	
	public static int getLevel(int y){
		
		if (y < 15)	return 4;
		if (y < 25) return 3;
		if (y < 35) return 2;
		if (y < 45) return 1;
		return 0;
	}
        
        public boolean validLocation(Random rand, Coord column) {
            return true;
//            int x = column.getX();
//            int z = column.getZ();
//            String pos = x + ":" + z;
//            synchronized(buffer) {
//                if(buffer.buffer.contains(pos)) return false;
//                buffer.buffer.add(pos);
//                buffer.bufferl.addLast(pos);
//                if(buffer.bufferl.size() > buffer.buffer_len) {
//                    buffer.buffer.remove(buffer.bufferl.getFirst());
//                    buffer.bufferl.removeFirst();
//                }
//            }
//            return validLocationWithOutBuffer(rand, column);
        }
	
	public boolean validLocationWithOutBuffer(Random rand, Coord column){
		
//		Biome biome = editor.getInfo(column).getBiome();
                Biome biome = editor.getBiome(column);
//                Bukkit.getLogger().log(Level.SEVERE, biome.toString());
//                Bukkit.getLogger().log(Level.SEVERE, column.toString());
		Type[] invalidBiomes = new Type[]{
				BiomeDictionary.Type.RIVER,
				BiomeDictionary.Type.BEACH,
				BiomeDictionary.Type.MUSHROOM,
				BiomeDictionary.Type.OCEAN
		};
//		Bukkit.getLogger().log(Level.SEVERE, "C0");
		for(Type type : invalidBiomes){
			if(BiomeDictionary.hasType(biome, type)) return false;
		}
		
//		Coord stronghold = editor.findNearestStructure(VanillaStructure.STRONGHOLD, column);
//		if(stronghold != null){
//			double strongholdDistance = column.distance(stronghold);
//			if(strongholdDistance < 300) return false;
//		}
				
		int upperLimit = RogueConfig.getInt(RogueConfig.UPPERLIMIT);
		int lowerLimit = RogueConfig.getInt(RogueConfig.LOWERLIMIT);
                
//                Bukkit.getLogger().log(Level.SEVERE, "C1");
		
		Coord cursor = new Coord(column.getX(), upperLimit, column.getZ());
		
		if(!editor.isAirBlock(cursor)){
			return false;
		}
//		Bukkit.getLogger().log(Level.SEVERE, "C2");
                
		while(!editor.validGroundBlock(cursor)){
			cursor.add(Cardinal.DOWN);
			if(cursor.getY() < lowerLimit) return false;
			if(editor.getBlock(cursor).getType() == Material.WATER) return false;
		}
//                Bukkit.getLogger().log(Level.SEVERE, "C3");

		Coord start;
		Coord end;
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(new Coord(-4, 4, -4));
		end.add(new Coord(4, 4, 4));
		
		for (Coord c : new RectSolid(start, end)){
			if(editor.validGroundBlock(c)){
				return false;
			}
		}
//		Bukkit.getLogger().log(Level.SEVERE, "C4");
		start = new Coord(cursor);
		end = new Coord(cursor);
		start.add(new Coord(-4, -3, -4));
		end.add(new Coord(4, -3, 4));
		int airCount = 0;
		for (Coord c : new RectSolid(start, end)){
			if(!editor.validGroundBlock(c)){
				airCount++;
			}
			if(airCount > 8){
				return false;
			}
		}
//		Bukkit.getLogger().log(Level.SEVERE, "C5");
		return true;
	}
	
	public static Coord getNearbyCoord(Random rand, int x, int z, int min, int max){
		
		int distance = min + rand.nextInt(max - min);
		
		double angle = rand.nextDouble() * 2 * Math.PI;
		
		int xOffset = (int) (Math.cos(angle) * distance);
		int zOffset = (int) (Math.sin(angle) * distance);
		
		Coord nearby = new Coord(x + xOffset, 0, z + zOffset);		
		return nearby;
	}
	
	public static Random getRandom(IWorldEditor editor, Coord pos){
		return new Random(Objects.hash(editor.getSeed(), pos));
	}

	@Override
	public List<ITreasureChest> getChests() {
		return this.editor.getTreasure().getChests();
	}

	@Override
	public Coord getPosition(){
		return new Coord(this.origin);
	}

	@Override
	public List<IDungeonLevel> getLevels(){
		return this.levels;
	}
}
