package greymerk.roguelike.treasure.loot;

import java.util.Random;

import com.google.gson.JsonObject;

import greymerk.roguelike.treasure.loot.provider.ItemArmour;
import greymerk.roguelike.treasure.loot.provider.ItemBlock;
import greymerk.roguelike.treasure.loot.provider.ItemBrewing;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBonus;
import greymerk.roguelike.treasure.loot.provider.ItemEnchBook;
import greymerk.roguelike.treasure.loot.provider.ItemFood;
import greymerk.roguelike.treasure.loot.provider.ItemJunk;
import greymerk.roguelike.treasure.loot.provider.ItemMixture;
import greymerk.roguelike.treasure.loot.provider.ItemNovelty;
import greymerk.roguelike.treasure.loot.provider.ItemOre;
import greymerk.roguelike.treasure.loot.provider.ItemPotion;
import greymerk.roguelike.treasure.loot.provider.ItemRecord;
import greymerk.roguelike.treasure.loot.provider.ItemSmithy;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import greymerk.roguelike.treasure.loot.provider.ItemSupply;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import greymerk.roguelike.util.IWeighted;
import greymerk.roguelike.util.TextFormat;
import java.util.ArrayList;
import java.util.List;
//import net.minecraft.server.v1_14_R1.NBTTagCompound;
//import net.minecraft.server.v1_14_R1.NBTTagList;
//import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.Material;
//import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
//import net.minecraft.init.Items;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.nbt.NBTTagString;

public enum Loot {
	
	WEAPON, ARMOUR, BLOCK, JUNK, ORE, TOOL, POTION, FOOD, ENCHANTBOOK,
	ENCHANTBONUS, SUPPLY, MUSIC, SMITHY, SPECIAL, REWARD, BREWING;

	public static ILoot getLoot(){
		
		LootProvider loot = new LootProvider();
		for(int i = 0; i < 5; ++i){
			loot.put(i, new LootSettings(i));
		}
		
		return loot;
	}
	
	public static IWeighted<ItemStack> get(JsonObject data, int weight) throws Exception{
		
		if(!data.has("type")) return new WeightedRandomLoot(data, weight);
		
		String type = data.get("type").getAsString().toLowerCase();
		
		switch(type){
		case "potion": return Potion.get(data, weight);
		case "mixture" : return new ItemMixture(data, weight);
		case "weapon": return new ItemWeapon(data, weight);
		case "specialty": return new ItemSpecialty(data, weight);
		case "novelty" : return ItemNovelty.get(data, weight);
		case "tool" : return new ItemTool(data, weight);
		case "armour" : return new ItemArmour(data, weight);
		case "enchanted_book" : return new ItemEnchBook(data, weight);
		default: throw new Exception("No such loot type as: " + type);
		}
	}
	
	public static IWeighted<ItemStack> getProvider(Loot type, int level){
		switch(type){
		case WEAPON: return new ItemWeapon(0, level);
		case ARMOUR: return new ItemArmour(0, level);
		case BLOCK: return new ItemBlock(0, level);
		case JUNK: return new ItemJunk(0, level);
		case ORE: return new ItemOre(0, level);
		case TOOL: return new ItemTool(0, level);
		case POTION: return new ItemPotion(0, level);
		case BREWING: return new ItemBrewing(0, level);
		case FOOD: return new ItemFood(0, level);
		case ENCHANTBOOK: return new ItemEnchBook(0, level);
		case ENCHANTBONUS: return new ItemEnchBonus(0, level);
		case SUPPLY: return new ItemSupply(0, level);
		case MUSIC: return new ItemRecord(0, level);
		case SMITHY: return new ItemSmithy(0, level);
		case SPECIAL: return new ItemSpecialty(0, level);
		case REWARD:
		}
		
		return new WeightedRandomLoot(Material.STICK, 0, 1);
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, EquipmentSlot slot, int level, boolean enchant){
		if(slot == EquipmentSlot.HAND){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, Slot.getSlot(slot), enchant);
	}
	
	public static ItemStack getEquipmentBySlot(Random rand, Slot slot, int level, boolean enchant){
		
		if(slot == Slot.WEAPON){
			return ItemWeapon.getRandom(rand, level, enchant);
		}
		
		return ItemArmour.getRandom(rand, level, slot, enchant);
	}

	public static void setItemLore(ItemStack item, String loreText){
                ItemMeta im = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                if(im.hasLore()) lore = im.getLore();
                lore.add(loreText);
                im.setLore(lore);
                item.setItemMeta(im);
		
//                net.minecraft.server.v1_14_R1.ItemStack nms_item = CraftItemStack.asNMSCopy(item);
//		NBTTagCompound tag = nms_item.getTag();
//		
//		if (tag == null){
//			tag = new NBTTagCompound();
//			nms_item.setTag(tag);
//		}
//
//		if (!tag.hasKey("display")){
//			tag.set("display", new NBTTagCompound());
//		}
//		
//		NBTTagCompound display = tag.getCompound("display");
//		
//		if (!(display.hasKey("Lore")))
//		{
//			display.set("Lore", new NBTTagList());
//		}
//		
//		NBTTagList lore = display.getList("Lore", 0);
//		
//		NBTTagString toAdd = new NBTTagString(loreText);
//		
//		lore.add(toAdd);
//		
//		display.set("Lore", lore);   
	}
	
	public static void setItemLore(ItemStack item, String loreText, TextFormat option){
		setItemLore(item, TextFormat.apply(loreText, option));
	}
	
	public static void setItemName(ItemStack item, String name, TextFormat option){
		
		if(option == null){
                        ItemMeta im = item.getItemMeta();
                        im.setDisplayName(name);
                        item.setItemMeta(im);
			return;
		}
		ItemMeta im = item.getItemMeta();
                im.setDisplayName(TextFormat.apply(name, option));
                item.setItemMeta(im);
	}
	
	public static void setItemName(ItemStack item, String name){
		setItemName(item, name, null);
	}
}
