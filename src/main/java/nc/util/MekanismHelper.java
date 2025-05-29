package nc.util;

import mekanism.api.transmitters.*;
import mekanism.common.tile.transmitter.TileEntityTransmitter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MekanismHelper {
	
	// Fix for forcing Mekanism transmitter networks to update
	public static void updateTransmitter(World world, BlockPos pos, EnumFacing side) {
		if (world.getTileEntity(pos.offset(side)) instanceof TileEntityTransmitter<?, ?, ?> transmitter) {
			updateTransmitter(transmitter, side);
		}
	}
	
	private static <A, N extends DynamicNetwork<A, N, BUFFER>, BUFFER> void updateTransmitter(TileEntityTransmitter<A, N, BUFFER> transmitter, EnumFacing side) {
		IGridTransmitter<A, N, BUFFER> impl = transmitter.getTransmitter();
		if (impl.hasTransmitterNetwork()) {
			impl.getTransmitterNetwork().updateTransmitterOnSide(impl, side.getOpposite());
		}
	}
}
