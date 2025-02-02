package nc.tile.turbine;

import nc.multiblock.turbine.Turbine;

public class TileTurbineCoilConnector extends TileTurbineDynamoPart {
	
	public TileTurbineCoilConnector() {
		super("connector", null, "connector");
	}
	
	@Override
	public void onMachineAssembled(Turbine multiblock) {
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}
}
