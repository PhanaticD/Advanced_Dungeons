package greymerk.roguelike.dungeon;

import java.util.List;
import java.util.Random;

import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.settings.LevelSettings;
import greymerk.roguelike.dungeon.tasks.IDungeonTask;
import greymerk.roguelike.dungeon.tasks.IDungeonTaskRegistry;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import zhehe.advanceddungeons.AdvancedDungeons;
import zhehe.advanceddungeons.util.DelayData;
import zhehe.advanceddungeons.util.DelayNode;

public class DungeonGenerator {
	
	public static int generate(IWorldEditor editor, IDungeon dungeon, ISettings settings, IDungeonTaskRegistry tasks){
try {
		Coord start = dungeon.getPosition();
		Random rand = Dungeon.getRandom(editor, start);
		List<IDungeonLevel> levels = dungeon.getLevels();
		int numLevels = settings.getNumLevels();
		
		// create level objects
		for (int i = 0; i < numLevels; ++i){
			LevelSettings levelSettings = settings.getLevelSettings(i);
			DungeonLevel level = new DungeonLevel(editor, rand, levelSettings, new Coord(start));
			levels.add(level);
		}

//		for(DungeonStage stage : DungeonStage.values()){
//			for(IDungeonTask task : tasks.getTasks(stage)){
//				task.execute(editor, rand, dungeon, settings);
//			}
//		}
//                List<DelayNode> delay = editor.getDelayList();
//                List<DelayData> data = editor.getDataList();
//                editor.resetDataList();
//                editor.resetDelayList();
//                Bukkit.getLogger().log(Level.SEVERE, "create");
            BukkitRunnable run = new BukkitRunnable() {
//                private List<DelayNode> d_delay = delay;
//                private List<DelayData> d_data = data;
                DungeonStage[] stages = DungeonStage.values();
                int index = 0;
                int div = 0;
                private final static int STEP = 12;
                @Override
                public void run() {
                    if(index < stages.length) {
                        DungeonStage stage = stages[index];
                        for(IDungeonTask task : tasks.getTasks(stage)){
                            task.execute(editor, rand, dungeon, settings);
                        }
                        index++;
                    } else {
                        this.cancel();
//                        World world = editor.getWorld();
//                        Bukkit.getLogger().log(Level.SEVERE, "2");
//                        if(div < d_data.size()) {
//                            int len = d_data.size() / STEP + 1;
//                            int end = div + len;
//                            for(; div < end && div < d_data.size(); div++) {
//                                DelayData d = d_data.get(div);
//                                world.getBlockAt(d.pos.getX(), d.pos.getY(), d.pos.getZ()).setBlockData(d.data, d.flag);
//                            }
//                        } else {
//                            int len = d_delay.size();
//                            for(int i = 0; i < len; i++) {
//                                DelayNode d = d_delay.get(i);
//                                world.getBlockAt(d.pos.getX(), d.pos.getY(), d.pos.getZ()).setType(d.material, true);
//                            }
//                            this.cancel();
//                        }
                    }
                }
            };
            run.runTaskTimer(AdvancedDungeons.instance, 1, 1);
//            return data.size();
} catch (Exception ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    Bukkit.getLogger().log(Level.SEVERE, ex.toString());
//    return -1;
}
    return 0;
	}
}
