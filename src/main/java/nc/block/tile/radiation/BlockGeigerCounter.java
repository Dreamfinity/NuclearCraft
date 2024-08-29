package nc.block.tile.radiation;

import nc.block.tile.BlockSimpleTile;
import nc.config.NCConfig;
import nc.enumm.BlockEnums.SimpleTileType;
import nc.radiation.RadiationHelper;
import nc.tile.radiation.TileGeigerCounter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockGeigerCounter extends BlockSimpleTile {

	public BlockGeigerCounter() {
		super(SimpleTileType.GEIGER_BLOCK);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) return false;
		
		if (player != null) {
			if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileGeigerCounter) {
				if (!world.isRemote) {
					TileGeigerCounter geiger = (TileGeigerCounter) world.getTileEntity(pos);
					double radiation = geiger.getChunkRadiationLevel();
					player.sendMessage(new TextComponentTranslation("item.nuclearcraft.geiger_counter.rads").appendText(" ").appendSibling(new TextComponentString(radiation < NCConfig.radiation_lowest_rate ? "0 Rads/t" : RadiationHelper.radsPrefix(radiation, true)).setStyle(new Style().setColor(RadiationHelper.getRadiationTextColor(radiation)))));
				}
				return true;
			}
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
		if (tile instanceof TileGeigerCounter) {
			return ((TileGeigerCounter)tile).comparatorStrength;
		}
		return Container.calcRedstone(tile);
	}
}
