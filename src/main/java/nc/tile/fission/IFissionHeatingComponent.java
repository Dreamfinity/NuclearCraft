package nc.tile.fission;

public interface IFissionHeatingComponent extends IFissionComponent {
	
	long getRawHeating(boolean simulate);
	
	long getRawHeatingIgnoreCoolingPenalty(boolean simulate);
	
	double getEffectiveHeating(boolean simulate);
	
	double getEffectiveHeatingIgnoreCoolingPenalty(boolean simulate);
}
