package nc.util;

import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MekanismHelper {
	
	// Fix for forcing Mekanism transmitter networks to update
	public static void markTransmitterDirty(World world, BlockPos pos, EnumFacing side) {
		if (world.getTileEntity(pos.offset(side)) instanceof TileEntityTransmitter<?, ?, ?> transmitter) {
			transmitter.markDirtyAcceptor(side.getOpposite());
		}
	}
}
