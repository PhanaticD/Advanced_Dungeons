package greymerk.roguelike.worldgen.blocks;

import greymerk.roguelike.worldgen.BlockJumble;
import greymerk.roguelike.worldgen.IBlockFactory;
import greymerk.roguelike.worldgen.MetaBlock;
import org.bukkit.Material;
//import net.minecraft.block.BlockSilverfish;
//import net.minecraft.init.Blocks;

public enum SilverfishBlock {

	STONE, COBBLE, STONEBRICK, STONEBRICK_MOSSY, STONEBRICK_CRACKED, STONEBRICK_CHISELED;
	
	public static MetaBlock get(SilverfishBlock type){
		
		MetaBlock block;
		
		switch(type){
		case STONE: block = new MetaBlock(Material.INFESTED_STONE); break;
		case COBBLE: block = new MetaBlock(Material.INFESTED_COBBLESTONE); break;
		case STONEBRICK: block = new MetaBlock(Material.INFESTED_STONE_BRICKS); break;
		case STONEBRICK_MOSSY: block = new MetaBlock(Material.INFESTED_MOSSY_STONE_BRICKS); break;
		case STONEBRICK_CRACKED: block = new MetaBlock(Material.INFESTED_CRACKED_STONE_BRICKS); break;
		case STONEBRICK_CHISELED: block = new MetaBlock(Material.INFESTED_CHISELED_STONE_BRICKS); break;
		default: block = new MetaBlock(Material.INFESTED_STONE); break;
		}
		
		return block;
		
	}
	
	public static IBlockFactory getJumble(){
		
		BlockJumble jumble = new BlockJumble();
		
		SilverfishBlock[] types = new SilverfishBlock[]{
				COBBLE,
				STONEBRICK,
				STONEBRICK_MOSSY,
				STONEBRICK_CRACKED
		};
		
		for(SilverfishBlock type : types){
			jumble.addBlock(get(type));	
		}		
		
		return jumble;
		
	}
	
}
