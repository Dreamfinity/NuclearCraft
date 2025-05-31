package nc.tile.fission;

import nc.multiblock.cuboidal.ITileCuboidalLogicMultiblockPart;
import nc.multiblock.fission.*;

public interface IFissionPart extends ITileCuboidalLogicMultiblockPart<FissionReactor, FissionReactorLogic, IFissionPart> {
	
	default boolean isSimulation() {
		FissionReactor reactor = getMultiblock();
		return reactor != null && reactor.isSimulation;
	}
}
