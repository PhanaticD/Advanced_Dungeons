/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons.util;

import greymerk.roguelike.worldgen.Coord;
import org.bukkit.Material;

/**
 *
 * @author Zhehe
 */
public class DelayNode {
    public Coord pos;
    public Material material;
    
    public DelayNode(Coord pos, Material material) {
        this.pos = pos;
        this.material = material;
    }
}
