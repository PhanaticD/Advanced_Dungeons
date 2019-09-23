package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.util.DyeColor;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
//import net.minecraft.block.BlockBed;
//import net.minecraft.init.Blocks;
//import net.minecraft.tileentity.TileEntityBed;

public class Bed {
    
        public final static Material BED_LIST[] = {
            Material.WHITE_BED,
            Material.ORANGE_BED,
            Material.MAGENTA_BED,
            Material.LIGHT_BLUE_BED,
            Material.YELLOW_BED,
            Material.LIME_BED,
            Material.PINK_BED,
            Material.GRAY_BED,
            Material.LIGHT_GRAY_BED,
            Material.CYAN_BED,
            Material.PURPLE_BED,
            Material.BLUE_BED,
            Material.BROWN_BED,
            Material.GREEN_BED,
            Material.RED_BED,
            Material.BLACK_BED,
        };

	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos, DyeColor color){
		Coord cursor = new Coord(pos);

		
		if(RogueConfig.getBoolean(RogueConfig.FURNITURE)){
                        Material bed = BED_LIST[DyeColor.getInt(color)];
			MetaBlock head = new MetaBlock(bed);
                        org.bukkit.block.data.type.Bed state = (org.bukkit.block.data.type.Bed) head.getState();
                        state.setPart(org.bukkit.block.data.type.Bed.Part.HEAD);
                        state.setFacing(Cardinal.facing(dir));
                        head.setState(state);
			head.set(editor, cursor);
		} else {
			ColorBlock.get(ColorBlock.WOOL, DyeColor.WHITE).set(editor, cursor);
		}
		
		cursor.add(dir);
		if(RogueConfig.getBoolean(RogueConfig.FURNITURE)){
                        Material bed = BED_LIST[DyeColor.getInt(color)];
			MetaBlock foot = new MetaBlock(bed);
                        org.bukkit.block.data.type.Bed state = (org.bukkit.block.data.type.Bed) foot.getState();
                        state.setPart(org.bukkit.block.data.type.Bed.Part.FOOT);
                        state.setFacing(Cardinal.facing(dir));
                        foot.setState(state);
			foot.set(editor, cursor);
		} else {
			ColorBlock.get(ColorBlock.WOOL, DyeColor.RED).set(editor, cursor);
		}
	}
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, dir, pos, DyeColor.RED);
	}
}
