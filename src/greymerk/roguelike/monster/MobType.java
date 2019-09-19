package greymerk.roguelike.monster;

//import net.minecraft.entity.Entity;
//import net.minecraft.entity.monster.EntityCreeper;
//import net.minecraft.entity.monster.EntityEvoker;
//import net.minecraft.entity.monster.EntityHusk;
//import net.minecraft.entity.monster.EntityPigZombie;
//import net.minecraft.entity.monster.EntitySkeleton;
//import net.minecraft.entity.monster.EntitySpider;
//import net.minecraft.entity.monster.EntityStray;
//import net.minecraft.entity.monster.EntityVindicator;
//import net.minecraft.entity.monster.EntityWitch;
//import net.minecraft.entity.monster.EntityWitherSkeleton;
//import net.minecraft.entity.monster.EntityZombie;
//import net.minecraft.entity.monster.EntityZombieVillager;
//import net.minecraft.world.World;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public enum MobType {
	
	ZOMBIE, ZOMBIEVILLAGER, HUSK, SKELETON, STRAY, SPIDER, CREEPER, WITHERSKELETON, PIGZOMBIE, EVOKER, VINDICATOR, WITCH;
	
	
	public static Entity getEntity(World world, Location loc, MobType type){
		switch(type){
		case ZOMBIE: return world.spawnEntity(loc, EntityType.ZOMBIE);
		case ZOMBIEVILLAGER: return world.spawnEntity(loc, EntityType.ZOMBIE_VILLAGER);
		case HUSK: return world.spawnEntity(loc, EntityType.HUSK);
		case SKELETON: return world.spawnEntity(loc, EntityType.SKELETON);
		case STRAY: return world.spawnEntity(loc, EntityType.STRAY);
		case SPIDER: return world.spawnEntity(loc, EntityType.SPIDER);
		case CREEPER: return world.spawnEntity(loc, EntityType.CREEPER);
		case WITHERSKELETON: return world.spawnEntity(loc, EntityType.WITHER_SKELETON);
		case PIGZOMBIE: return world.spawnEntity(loc, EntityType.PIG_ZOMBIE);
		case EVOKER: return world.spawnEntity(loc, EntityType.EVOKER);
		case VINDICATOR: return world.spawnEntity(loc, EntityType.VINDICATOR);
		case WITCH: return world.spawnEntity(loc, EntityType.WITCH);
		
		default: return world.spawnEntity(loc, EntityType.ZOMBIE);
		}
	}
}
