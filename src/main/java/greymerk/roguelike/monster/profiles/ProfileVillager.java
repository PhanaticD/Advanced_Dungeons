package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.Shield;
import greymerk.roguelike.treasure.loot.provider.ItemTool;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.World;

public class ProfileVillager implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		mob.setMobClass(MobType.ZOMBIEVILLAGER, false);
		ItemStack weapon = ItemTool.getRandom(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level));
		mob.setSlot(EquipmentSlot.HAND, weapon);
		mob.setSlot(EquipmentSlot.OFF_HAND, Shield.get(rand));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}

}
