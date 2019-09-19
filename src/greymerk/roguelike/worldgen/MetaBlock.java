package greymerk.roguelike.worldgen;

import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

//import net.minecraft.block.Block;
//import net.minecraft.block.material.EnumPushReaction;
//import net.minecraft.block.material.MapColor;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.properties.IProperty;
//import net.minecraft.block.state.BlockFaceShape;
//import net.minecraft.block.state.BlockStateBase;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.util.EnumBlockRenderType;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.Mirror;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.Rotation;
//import net.minecraft.util.math.AxisAlignedBB;
//import zhehe.advanceddungeons.util.BlockPos;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;

public class MetaBlock extends BlockBase{

	private BlockData state;
	private int flag;
    
	public MetaBlock(Material block){
                this.state = Bukkit.createBlockData(block);
		flag = 2;
	}
	
	public MetaBlock(MetaBlock block){
		this.state = block.getState();
		flag = 2;
	}
	
	public MetaBlock(BlockData state){
		this.setState(state);
		flag = 2;
	}
	
	
	public MetaBlock(JsonElement e) throws Exception{
		JsonObject json = (JsonObject)e;
		String name = json.get("name").getAsString();

                try {
                    state = Bukkit.createBlockData(name);
                } catch (Exception ex) {
                    
                }
		
		flag = json.has("flag") ? json.get("flag").getAsInt() : 2;
	}
	
	public void setState(BlockData state){
		
		this.state = state;
	}

	public boolean set(IWorldEditor editor, Coord pos){
		return editor.setBlock(pos, this, true, true);
	}
		
	@Override
	public boolean set(IWorldEditor editor, Random rand, Coord pos, boolean fillAir, boolean replaceSolid) {
		return editor.setBlock(pos, this, fillAir, replaceSolid);
	}

	public BlockData getState(){
		
		return this.state;
	}
	
	public Material getBlock() {
		return this.getState().getMaterial();
	}
	
	public int getFlag(){
		return this.flag;
	}
	
	@Override
	public String toString(){
		return this.state.getAsString();
	}

	public Material getMaterial() {
		return this.state.getMaterial();
	}

	public boolean isFullBlock() {
		return this.state.getMaterial().isSolid();
	}
        
        public boolean isOpaqueCube() {
                return isFullBlock();
        }
}
