/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons.util;

import greymerk.roguelike.worldgen.Coord;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

/**
 *
 * @author Zhehe
 */
public class DelayData {
    public Coord pos;
    public BlockData data;
    public boolean flag;
    
    public DelayData(Coord pos, BlockData data, boolean flag) {
        this.pos = pos;
        this.data = data;
        this.flag = flag;
    }
}

