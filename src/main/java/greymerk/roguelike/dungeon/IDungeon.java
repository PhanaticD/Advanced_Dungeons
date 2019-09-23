package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.treasure.ITreasureChest;
import greymerk.roguelike.worldgen.Coord;

public interface IDungeon {

	public int generate(ISettings setting, Coord pos);
	
	public void spawnInChunk(Random rand, int chunkX, int chunkZ);
	
	public Coord getPosition();
	
	public List<IDungeonLevel> getLevels();
	
	public List<ITreasureChest> getChests();
	
}
