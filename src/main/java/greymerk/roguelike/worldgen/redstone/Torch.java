package greymerk.roguelike.worldgen.redstone;


import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockTorch;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.EnumFacing;

public enum Torch {

	REDSTONE, WOODEN, REDSTONE_UNLIT;
	
	public static void generate(IWorldEditor editor, Torch type, Cardinal dir, Coord pos){
		
		BlockData name;
		
		switch(type){
		case WOODEN: name = Bukkit.createBlockData(Material.TORCH); break;
		case REDSTONE: name = Bukkit.createBlockData(Material.REDSTONE_TORCH); break;
		case REDSTONE_UNLIT: name = Bukkit.createBlockData("minecraft:redstone_torch[lit=false]"); break;
		default: name = Bukkit.createBlockData(Material.TORCH); break;
		}		
		
		MetaBlock torch = new MetaBlock(name);
                if(dir == Cardinal.UP) {
                    
                } else if(dir == Cardinal.DOWN){
                    
                } else {
                    torch = new MetaBlock(Material.REDSTONE_WALL_TORCH);
                    Directional dd = (Directional) torch.getState();
                    dd.setFacing(Cardinal.facing(Cardinal.reverse(dir)));
                    torch.setState(dd);
                }
//		if(dir == Cardinal.UP){
//			torch.withProperty(BlockTorch.FACING, EnumFacing.UP);
//		} else if(dir == Cardinal.DOWN){
//			torch.withProperty(BlockTorch.FACING, EnumFacing.DOWN);
//		} else {
//			torch.withProperty(BlockTorch.FACING, Cardinal.facing(Cardinal.reverse(dir)));
//		}
		
		
		torch.set(editor, pos);
		
	}
	
}
