/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons.util;

import greymerk.roguelike.dungeon.IDungeon;
import greymerk.roguelike.dungeon.settings.ISettings;
import greymerk.roguelike.dungeon.tasks.IDungeonTask;
import greymerk.roguelike.worldgen.IWorldEditor;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author Zhehe
 */
public class DungeonDelayTask implements IDungeonTask {

	@Override
	public void execute(IWorldEditor editor, Random rand, IDungeon dungeon, ISettings settings) {
            List<DelayNode> list = editor.getDelayList();
            
            for(DelayNode node : list) {
                editor.setBlockDelay(node.pos, node.material);
            }
            editor.resetDelayList();
	}
}
