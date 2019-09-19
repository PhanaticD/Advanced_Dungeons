package greymerk.roguelike.treasure.loot;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;

public class Shield {

	public static ItemStack get(Random rand){
		
		ItemStack banner = Banner.get(rand);
		
		ItemStack shield = new ItemStack(Material.SHIELD, 1); 
		
		applyBanner(banner, shield);
		
		return shield;
	}
	
	public static void applyBanner(ItemStack banner, ItemStack shield){
            // TODO
//        NBTTagCompound bannerNBT = banner.getSubCompound("BlockEntityTag");
//        NBTTagCompound shieldNBT = bannerNBT == null ? new NBTTagCompound() : bannerNBT.copy();
//        shieldNBT.setInteger("Base", banner.getMetadata() & 15);
//        shield.setTagInfo("BlockEntityTag", shieldNBT);

	}
	
}
