package nc.block.tile.radiation;

import nc.block.tile.BlockSimpleTile;
import nc.radiation.RadiationHelper;
import nc.tile.radiation.TileGeigerCounter;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static nc.config.NCConfig.radiation_lowest_rate;

public class BlockGeigerCounter extends BlockSimpleTile<TileGeigerCounter> {
	
	private static final String RADIATION = Lang.localize("item.nuclearcraft.geiger_counter.rads");
	
	public BlockGeigerCounter(String name) {
		super(name);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) {
			return false;
		}
		
		if (player.getHeldItem(hand).isEmpty()) {
			TileEntity tile = world.getTileEntity(pos);
			if (!world.isRemote && tile instanceof TileGeigerCounter geiger) {
				double radiation = geiger.getChunkRadiationLevel();
				player.sendMessage(new TextComponentString(RADIATION + " " + RadiationHelper.getRadiationTextColor(radiation) + (radiation < radiation_lowest_rate ? "0 Rad/t" : RadiationHelper.radsPrefix(radiation, true))));
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileGeigerCounter geigerCounter) {
			return geigerCounter.comparatorStrength;
		}
		return 0;
	}
}
