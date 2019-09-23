package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.Cardinal;
import greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Orientable;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockLog;
//import net.minecraft.block.BlockNewLog;
//import net.minecraft.block.BlockOldLog;
//import net.minecraft.block.BlockPlanks;
//import net.minecraft.init.Blocks;

public class Log {

	public static MetaBlock get(Wood type, Cardinal dir){
		
		MetaBlock log = new MetaBlock(Material.OAK_LOG);
		
		setType(log, type);

		if(dir == null){
			return log;
		}
		
		switch(dir){
		case UP:
		case DOWN: setAxis(log, Axis.Y); break;
		case EAST:
		case WEST: setAxis(log, Axis.X); break;
		case NORTH:
		case SOUTH: setAxis(log, Axis.Z); break;
		}
		
		return log;
		
	}
        
        private static void setAxis(MetaBlock log, Axis axis) {
            Orientable state = (Orientable) log.getState();
            state.setAxis(axis);
            log.setState(state);
        }
	
	public static MetaBlock getLog(Wood type){
		return get(type, Cardinal.UP);
	}
	
	public static Material getBlockId(Wood type){
		switch(type){
		case OAK: return Material.OAK_LOG;
		case SPRUCE: return Material.SPRUCE_LOG;
		case BIRCH: return Material.BIRCH_LOG;
		case JUNGLE: return Material.JUNGLE_LOG;
		case ACACIA: return Material.ACACIA_LOG;
		case DARKOAK: return Material.DARK_OAK_LOG;
		default: return Material.OAK_LOG;
		}
	}
	
	public static void setType(MetaBlock log, Wood type){
		switch(type){
		case OAK: log.setState(Bukkit.createBlockData(Material.OAK_LOG)); return;
		case SPRUCE: log.setState(Bukkit.createBlockData(Material.SPRUCE_LOG)); return;
		case BIRCH: log.setState(Bukkit.createBlockData(Material.BIRCH_LOG)); return;
		case JUNGLE: log.setState(Bukkit.createBlockData(Material.JUNGLE_LOG)); return;
		case ACACIA: log.setState(Bukkit.createBlockData(Material.ACACIA_LOG)); return;
		case DARKOAK: log.setState(Bukkit.createBlockData(Material.DARK_OAK_LOG)); return;
		default: log.setState(Bukkit.createBlockData(Material.OAK_LOG)); return;
		}
	}
}
