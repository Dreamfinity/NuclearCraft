package nc.tile.fission.port;

import nc.recipe.NCRecipes;
import nc.tile.fission.TileFissionCooler;

import static nc.util.FluidStackHelper.INGOT_BLOCK_VOLUME;

public class TileFissionCoolerPort extends TileFissionFluidPort<TileFissionCoolerPort, TileFissionCooler> {
	
	public TileFissionCoolerPort() {
		super("fission_cooler_port", TileFissionCoolerPort.class, INGOT_BLOCK_VOLUME, null, NCRecipes.fission_emergency_cooling);
	}
}
