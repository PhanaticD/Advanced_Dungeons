package greymerk.roguelike.worldgen;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.treasure.TreasureManager;
import greymerk.roguelike.worldgen.blocks.BlockType;
import greymerk.roguelike.worldgen.shapes.RectSolid;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.init.Blocks;
//import net.minecraft.tileentity.TileEntity;
//import zhehe.roguelikedungeon.util.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//import net.minecraft.world.gen.ChunkProviderServer;
import org.bukkit.block.Block;
import zhehe.advanceddungeons.util.DelayData;
import zhehe.advanceddungeons.util.DelayNode;
import zhehe.advanceddungeons.util.FormatItem;

public class WorldEditor implements IWorldEditor{
	
	World world;
        private List<DelayNode> delay = new ArrayList<>();
        private List<DelayData> data = new ArrayList<>();
	private Map<Material, Integer> stats;
	private TreasureManager chests;
	private static List<Material> invalid;
	{
            // TODO
		invalid = new ArrayList<>();
		invalid.add(Material.OAK_PLANKS);
		invalid.add(Material.WATER);
		invalid.add(Material.CACTUS);
		invalid.add(Material.SNOW);
		invalid.add(Material.GRASS);
		invalid.add(Material.STONE);
		invalid.add(Material.OAK_LEAVES);
		invalid.add(Material.POPPY);
                invalid.add(Material.DANDELION);
	};
        
        @Override
        public String getWorldName() {
            return world.getName();
        }
        
        @Override
        public World getWorld() {
            return world;
        }
        
        @Override
        public Biome getBiome(Coord pos) {
            return world.getBiome(pos.getX(), pos.getZ());
        }
	
	public WorldEditor(World world){
		this.world = world;
		stats = new HashMap<>();
		this.chests = new TreasureManager();
	}
        
        @Override
        public List<DelayNode> getDelayList() {
            return delay;
        }
        
        @Override
        public List<DelayData> getDataList() {
            return data;
        }
        
        @Override
        public void resetDelayList() {
            delay = new ArrayList<>();
        }
        
        @Override
        public void resetDataList() {
            data = new ArrayList<>();
        }
        
        private boolean needUpdate(Coord pos) {
            int x = pos.getX(), y = pos.getY(), z = pos.getZ();
            Material material;
            material = world.getBlockAt(x + 1, y, z).getType();
            if(        material == Material.AIR
                        || material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) return true;
            material = world.getBlockAt(x - 1, y, z).getType();
            if(        material == Material.AIR
                        || material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) return true;
            material = world.getBlockAt(x, y, z + 1).getType();
            if(        material == Material.AIR
                        || material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) return true;
            material = world.getBlockAt(x, y, z - 1).getType();
            if(        material == Material.AIR
                        || material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) return true;
            
            return false;
        }
        
        @Override
        public void setBlockDelay(Coord pos, Material material) {
                try{
                        world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(material, true);
		} catch(NullPointerException npe){
			//ignore it.
		}
        }
        
	
	private boolean setBlock(Coord pos, MetaBlock block, int flags, boolean fillAir, boolean replaceSolid){
		
		MetaBlock currentBlock = getMetaBlock(pos);
		
                Material material = block.getBlock();
                Material mat = currentBlock.getBlock();
                                
		if(mat == Material.CHEST) return false;
		if(mat == Material.TRAPPED_CHEST) return false;
		if(mat == Material.SPAWNER) return false;
                
//                if(material.isOccluding()) flags = 1;
		
		//boolean isAir = world.isAirBlock(pos.getBlockPos());
		boolean isAir = currentBlock.getBlock() == Material.AIR;
		
		if(!fillAir && isAir) return false;
		if(!replaceSolid && !isAir)	return false;
                
                boolean patch = false;
                
                if(        material == Material.IRON_BARS || material == Material.REDSTONE_WIRE
                        || material == Material.WATER || material == Material.LAVA
                        || material == Material.OAK_FENCE || material == Material.SPRUCE_FENCE
                        || material == Material.JUNGLE_FENCE || material == Material.BIRCH_FENCE
                        || material == Material.DARK_OAK_FENCE || material == Material.ACACIA_FENCE
                        || material == Material.NETHER_BRICK_FENCE
                        ) {
                    patch = true;
//                    delay.add(new DelayNode(pos, material));
                }
		
//                if(needUpdate(pos)) flags = 1;
                
		try{
//			world.setBlockState(pos.getBlockPos(), block.getState(), flags);
                        if(!patch) world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setBlockData(block.getState(), flags == 1);
                        else world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).setType(block.getBlock(), true);
                        
//                        if(!patch) data.add(new DelayData(pos, block.getState(), flags == 1));
//                        else delay.add(new DelayNode(pos, block.getBlock()));
		} catch(NullPointerException npe){
			//ignore it.
		}
		
                Material type = material;
//		Material type = block.getBlock();
		Integer count = stats.get(type);
		if(count == null){
			stats.put(type, 1);	
		} else {
			stats.put(type, count + 1);
		}
		
		return true;
		
	}
	
	@Override
	public boolean setBlock(Coord pos, MetaBlock block, boolean fillAir, boolean replaceSolid){
		return this.setBlock(pos, block, block.getFlag(), fillAir, replaceSolid);
	}
        
        @Override
        public Block getBlock(Coord pos) {
                return world.getBlockAt(pos.getX(), pos.getY(), pos.getZ());
        }
	
	@Override
	public boolean isAirBlock(Coord pos){
		return world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).getType() == Material.AIR;
	}
	
	@Override
	public long getSeed(){
		return this.world.getSeed();
	}
	
//	@Override
//	public Random getSeededRandom(int a, int b, int c){
//		return world.setRandomSeed(a, b, c);
//	}
		
	@Override
	public void spiralStairStep(Random rand, Coord origin, IStair stair, IBlockFactory fill){
		
		MetaBlock air = BlockType.get(BlockType.AIR);
		Coord cursor;
		Coord start;
		Coord end;
		
		start = new Coord(origin);
		start.add(new Coord(-1, 0, -1));
		end = new Coord(origin);
		end.add(new Coord(1, 0, 1));
		
		RectSolid.fill(this, rand, start, end, air);
		fill.set(this, rand, origin);
		
		Cardinal dir = Cardinal.directions[origin.getY() % 4];
		cursor = new Coord(origin);
		cursor.add(dir);
		stair.setOrientation(Cardinal.left(dir), false).set(this, cursor);
		cursor.add(Cardinal.right(dir));
		stair.setOrientation(Cardinal.right(dir), true).set(this, cursor);
		cursor.add(Cardinal.reverse(dir));
		stair.setOrientation(Cardinal.reverse(dir), true).set(this, cursor);
	}
	
	@Override
	public void fillDown(Random rand, Coord origin, IBlockFactory blocks){

		Coord cursor = new Coord(origin);
		
		while(!getMetaBlock(cursor).isOpaqueCube() && cursor.getY() > 1){
			blocks.set(this, rand, cursor);
			cursor.add(Cardinal.DOWN);
		}
	}
	
	@Override
	public MetaBlock getMetaBlock(Coord pos){
		return new MetaBlock(world.getBlockAt(pos.getX(), pos.getY(), pos.getZ()).getBlockData());
	}
	
//	@Override
//	public TileEntity getTileEntity(Coord pos){
//		return world.getTileEntity(pos.getBlockPos());
//	}
	
	@Override
	public boolean validGroundBlock(Coord pos){
		if(isAirBlock(pos)) return false;
		MetaBlock block = this.getMetaBlock(pos);
		return !invalid.contains(block.getMaterial());
	}
	
	@Override
	public int getStat(Block type){
		if(!this.stats.containsKey(type)) return 0;
		return this.stats.get(type);
	}
	
	@Override
	public Map<Material, Integer> getStats(){
		return this.stats;
	}
	
	@Override
	public void addChest(ITreasureChest toAdd){
		this.chests.add(toAdd);
	}
	
	@Override
	public TreasureManager getTreasure(){
		return this.chests;
	}
	
	@Override
	public boolean canPlace(MetaBlock block, Coord pos, Cardinal dir){
		if(!this.isAirBlock(pos)) return false;
                return true;
//		return block.getBlock().canPlaceBlockOnSide(world, pos.getBlockPos(), Cardinal.facing(dir));
	}

	@Override
	public IPositionInfo getInfo(Coord pos) {
		return new PositionInfo(this.world, pos);
	}

//	@Override
//	public Coord findNearestStructure(VanillaStructure type, Coord pos) {
//		
//		ChunkProviderServer chunkProvider = ((WorldServer)world).getChunkProvider();
//		String structureName = VanillaStructure.getName(type);
//		
//		BlockPos structurebp = null;
//		
//		try{
//			structurebp = chunkProvider.getNearestStructurePos(world, structureName, pos.getBlockPos(), false);	
//		} catch(NullPointerException e){
//			// happens for some reason if structure type is disabled in Chunk Generator Settings
//		}
//		
//		if(structurebp == null) return null;
//		
//		return new Coord(structurebp);		
//	}
	
	@Override
	public String toString(){
		String toReturn = "";

		for(Map.Entry<Material, Integer> pair : stats.entrySet()){
			toReturn += (new FormatItem(pair.getKey())).getUnlocalizedName() + ": " + pair.getValue() + "\n";
		}
		
		return toReturn;
	}
}

