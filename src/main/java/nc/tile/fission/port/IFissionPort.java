package nc.tile.fission.port;

import nc.multiblock.fission.*;
import nc.tile.fission.*;
import nc.tile.multiblock.port.ITilePort;

public interface IFissionPort<PORT extends IFissionPort<PORT, TARGET>, TARGET extends IFissionPortTarget<PORT, TARGET>> extends ITilePort<FissionReactor, FissionReactorLogic, IFissionPart, PORT, TARGET>, IFissionSpecialPart {
	
	default void postClusterSearch(boolean simulate) {
		if (!simulate) {
			refreshTargets();
		}
	}
}
