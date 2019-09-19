package greymerk.roguelike.worldgen.redstone;

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
//import net.minecraft.block.BlockDropper;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.tileentity.TileEntityDropper;

public class Dropper {
	
	public boolean generate(IWorldEditor editor, Cardinal dir, Coord pos){

		MetaBlock container = new MetaBlock(Material.DROPPER);
                Directional state = (Directional) container.getState();
                state.setFacing(Cardinal.facing(dir));
                container.setState(state);
		container.set(editor, pos);
		return true;
	}
	
	public void add(IWorldEditor editor, Coord pos, int slot, ItemStack item){
		
		Block te = editor.getBlock(pos);
		if(te == null) return;
                BlockState state = te.getState();
                if(state instanceof InventoryHolder) {
                    InventoryHolder inv = (InventoryHolder) state;
                    inv.getInventory().setItem(slot, item);
                }
	}
}
