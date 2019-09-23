package greymerk.roguelike.treasure;

import java.util.Objects;
import java.util.Random;

import greymerk.roguelike.worldgen.Coord;
import greymerk.roguelike.worldgen.IWorldEditor;
import greymerk.roguelike.worldgen.MetaBlock;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntityChest;
//import net.minecraft.util.ResourceLocation;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public class TreasureChest implements ITreasureChest{

	protected Inventory inventory;
	protected Treasure type;
	protected Random rand;
	private long seed;
	private InventoryHolder chest;
        private Block block;
	private int level;

	public TreasureChest(Treasure type){
		this.type = type;
		this.level = 0;
	}
	
        @Override
	public ITreasureChest generate(IWorldEditor editor, Random rand, Coord pos, int level, boolean trapped) throws ChestPlacementException {

		this.rand = rand;
		this.level = level;
		
		MetaBlock chestType = new MetaBlock(trapped ? Material.TRAPPED_CHEST : Material.CHEST);
		
		boolean success = chestType.set(editor, pos);
		
		if(!success){
			throw new ChestPlacementException("Failed to place chest in world");
		}
		
                BlockState bs = editor.getBlock(pos).getState();
		if(bs instanceof InventoryHolder) this.chest = (InventoryHolder) bs;
                this.block = editor.getBlock(pos);
		this.inventory = new Inventory(rand, chest);
		this.seed = (long)Objects.hash(pos.hashCode(), editor.getSeed());
		
		editor.addChest(this);
		return this;
	}
	
	@Override
	public boolean setSlot(int slot, ItemStack item){
		return this.inventory.setInventorySlot(slot, item);
	}
	
	@Override
	public boolean setRandomEmptySlot(ItemStack item){
		return this.inventory.setRandomEmptySlot(item);
	}
	
	@Override
	public boolean isEmptySlot(int slot){
		return this.inventory.isEmptySlot(slot);
	}

	@Override
	public Treasure getType(){
		return this.type;
	}
	
	@Override
	public int getSize(){
		return this.inventory.getInventorySize();
	}

	@Override
	public int getLevel() {
		if(level < 0) return 0;
		if(level > 4) return 4;
		return this.level;
	}

	@Override
	public void setLootTable(LootTable table) {
//		Lootable tmp = (Lootable) this.block.getState();
//                tmp.setLootTable(table);
            BlockState bs = this.block.getState();
            if(bs instanceof Chest) {
                Chest chest = (Chest) bs;
                chest.setLootTable(table);
                chest.update();
                
                Bukkit.getLogger().log(Level.SEVERE, this.block.getLocation().toString());
            }
	}
}
