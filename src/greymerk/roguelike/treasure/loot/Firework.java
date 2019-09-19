package greymerk.roguelike.treasure.loot;

import java.util.Random;

import greymerk.roguelike.util.DyeColor;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagIntArray;
import net.minecraft.server.v1_14_R1.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Firework {
	
	public static ItemStack get(Random rand, int stackSize){
	
		ItemStack rocket = new ItemStack(Material.FIREWORK_ROCKET, stackSize);
		
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagCompound fireworks = new NBTTagCompound();
		
		fireworks.setByte("Flight", (byte) (rand.nextInt(3) + 1));
		
		NBTTagList explosion = new NBTTagList();
		
		NBTTagCompound properties = new NBTTagCompound();
		properties.setByte("Flicker", (byte) (rand.nextBoolean() ? 1 : 0));
		properties.setByte("Trail", (byte) (rand.nextBoolean() ? 1 : 0));
		properties.setByte("Type", (byte) (rand.nextInt(5)));
		
		int size = rand.nextInt(4) + 1;
		int[] colorArr = new int[size];
		for(int i = 0; i < size; ++i){
			colorArr[i] = DyeColor.HSLToColor(rand.nextFloat(), (float)1.0, (float)0.5);
		}
		
		NBTTagIntArray colors = new NBTTagIntArray(colorArr);
		properties.set("Colors", colors);
		
		explosion.add(properties);
		fireworks.set("Explosions", explosion);
		tag.set("Fireworks", fireworks);
		
                net.minecraft.server.v1_14_R1.ItemStack tmp = CraftItemStack.asNMSCopy(rocket);
                tmp.setTag(tag);
                rocket = CraftItemStack.asBukkitCopy(tmp);
		
		return rocket;
	}
}
