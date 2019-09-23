package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MobType;
import greymerk.roguelike.treasure.loot.Equipment;
import greymerk.roguelike.treasure.loot.provider.ItemSpecialty;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.World;

public class ProfileVindicator implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		mob.setMobClass(MobType.VINDICATOR, true);
		mob.setSlot(EquipmentSlot.HAND, ItemSpecialty.getRandomItem(Equipment.AXE, rand, level));
	}

}
