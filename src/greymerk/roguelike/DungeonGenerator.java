package greymerk.roguelike;

import java.util.Random;

import greymerk.roguelike.dungeon.Dungeon;
import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.WorldEditor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
//import net.minecraft.world.World;
//import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.IChunkGenerator;
//import net.minecraftforge.fml.common.IWorldGenerator;

public class DungeonGenerator extends BlockPopulator { 

	@Override
	public void populate​(World world, Random random, Chunk source) {
		IWorldEditor editor = new WorldEditor(world);
		IDungeon dungeon = new Dungeon(editor);
		dungeon.spawnInChunk(random, source.getX(), source.getZ());
	}
        
        public void forcePopulate(World world, Random random, Chunk source) {
		IWorldEditor editor = new WorldEditor(world);
		Dungeon dungeon = new Dungeon(editor);
		dungeon.forceSpawnInChunk(random, source.getX(), source.getZ());
	}
}