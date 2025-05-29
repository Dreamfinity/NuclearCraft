package nc.block.fission.port;

import nc.tile.fission.TileFissionCooler;
import nc.tile.fission.port.TileFissionCoolerPort;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFissionCoolerPort extends BlockFissionFluidPort<TileFissionCoolerPort, TileFissionCooler> {
	
	public BlockFissionCoolerPort() {
		super(TileFissionCoolerPort.class);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileFissionCoolerPort();
	}
}
