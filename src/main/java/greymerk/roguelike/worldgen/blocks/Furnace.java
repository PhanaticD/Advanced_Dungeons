package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.config.RogueConfig;
import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.MetaBlock;
import greymerk.roguelike.worldgen.IWorldEditor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.block.BlockFurnace;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityFurnace;

public class Furnace {

	public static final int FUEL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	public static void generate(IWorldEditor editor, Cardinal dir, Coord pos){
		generate(editor, null, false, dir, pos);
	}
	
	public static void generate(IWorldEditor editor, boolean lit, Cardinal dir, Coord pos){
		generate(editor, null, lit, dir, pos);
	}
	
	public static void generate(IWorldEditor editor, ItemStack fuel, boolean lit, Cardinal dir, Coord pos){

		if(!RogueConfig.getBoolean(RogueConfig.FURNITURE)) return;
		
		MetaBlock furnace = new MetaBlock(Material.FURNACE);
		
		if(lit){
                        org.bukkit.block.data.type.Furnace state = (org.bukkit.block.data.type.Furnace) furnace.getState();
                        state.setLit(true);
                        furnace.setState(state);
//			furnace = new MetaBlock(Blocks.LIT_FURNACE);
		}
		
                Directional state = (Directional) furnace.getState();
                state.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
                		
		furnace.set(editor, pos);
		
		if(fuel == null) return;
		
		Block te = editor.getBlock(pos);
		if(te == null) return;
                
                BlockState bs = te.getState();
                if(!(bs instanceof InventoryHolder)) return;
                InventoryHolder inv = (InventoryHolder) bs;
                inv.getInventory().setItem(FUEL_SLOT, fuel);
	}
}
