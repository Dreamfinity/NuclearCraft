package nc.block.tile.radiation;

import nc.block.tile.BlockSimpleTile;
import nc.config.NCConfig;
import nc.enumm.BlockEnums.SimpleTileType;
import nc.radiation.RadiationHelper;
import nc.tile.radiation.TileRadiationScrubber;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockScrubber extends BlockSimpleTile {

	public BlockScrubber() {
		super(SimpleTileType.RADIATION_SCRUBBER);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) return false;
		
		if (player != null) {
			if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileRadiationScrubber) {
				if (!world.isRemote) {
					TileRadiationScrubber scrubber = (TileRadiationScrubber) world.getTileEntity(pos);
					scrubber.checkRadiationEnvironmentInfo();
					double radRemoval = scrubber.getRawScrubberRate();
					player.sendMessage(new TextComponentTranslation("message.nuclearcraft.scrubber_removal_rate").appendText(" ").appendText(Math.abs(radRemoval) < NCConfig.radiation_lowest_rate ? "0 Rads/t" : RadiationHelper.radsPrefix(radRemoval, true)).appendText(" [" + Math.abs(Math.round(100D*scrubber.getRadiationContributionFraction()/TileRadiationScrubber.getMaxScrubberFraction())) + "%]"));
				}
				return true;
			}
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
}
