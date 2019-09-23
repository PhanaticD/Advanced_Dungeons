package greymerk.roguelike.monster.profiles;

import java.util.Random;

import greymerk.roguelike.monster.IEntity;
import greymerk.roguelike.monster.IMonsterProfile;
import greymerk.roguelike.monster.MonsterProfile;
import greymerk.roguelike.treasure.loot.Enchant;
import greymerk.roguelike.treasure.loot.TippedArrow;
import greymerk.roguelike.treasure.loot.provider.ItemWeapon;
import org.bukkit.World;
import org.bukkit.inventory.EquipmentSlot;

public class ProfileArcher implements IMonsterProfile {

	@Override
	public void addEquipment(World world, Random rand, int level, IEntity mob) {
		
		if(Enchant.canEnchant(world.getDifficulty(), rand, level) && rand.nextInt(10) == 0){
			mob.setSlot(EquipmentSlot.OFF_HAND, TippedArrow.getHarmful(rand, 1));
		}
		
		mob.setSlot(EquipmentSlot.HAND, ItemWeapon.getBow(rand, level, Enchant.canEnchant(world.getDifficulty(), rand, level)));
		MonsterProfile.get(MonsterProfile.TALLMOB).addEquipment(world, rand, level, mob);
	}

}
