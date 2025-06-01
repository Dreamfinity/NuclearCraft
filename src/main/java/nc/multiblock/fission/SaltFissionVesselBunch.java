package nc.multiblock.fission;

import it.unimi.dsi.fastutil.longs.*;
import nc.tile.fission.TileSaltFissionVessel;
import net.minecraft.util.EnumFacing;

public class SaltFissionVesselBunch {
	
	public boolean initialized = false;
	
	public final Long2ObjectMap<TileSaltFissionVessel> vesselMap = new Long2ObjectOpenHashMap<>();
	
	public long sources = 0L, flux = 0L;
	public boolean primed = false, statsRetrieved = false;
	
	public long openFaces = 0L;
	
	public void init() {
		if (!initialized) {
			initialized = true;
			
			for (TileSaltFissionVessel vessel : vesselMap.values()) {
				flux += vessel.cachedFlux;
				for (EnumFacing dir : EnumFacing.VALUES) {
					if (!vesselMap.containsKey(vessel.getPos().offset(dir).toLong())) {
						++openFaces;
					}
				}
			}
		}
	}
	
	protected long getSurfaceFactor() {
		return openFaces / 6L;
	}
	
	protected long getBunchingFactor() {
		return 6L * vesselMap.size() / openFaces;
	}
	
	public void tryPriming(boolean fromSource, boolean simulate) {
		if (!primed) {
			if (fromSource) {
				++sources;
				if (sources >= getSurfaceFactor()) {
					primed = true;
				}
			}
			else {
				primed = true;
			}
		}
		
		if (primed) {
			sources = 0L;
		}
	}
	
	public long getCriticalityFactor(long criticalityFactor) {
		return getSurfaceFactor() * criticalityFactor;
	}
	
	public long getRawHeating(boolean simulate) {
		long rawHeating = 0L;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (vessel.isRunning(simulate)) {
				rawHeating += (long) vessel.baseProcessHeat * vessel.heatMult;
			}
		}
		return getBunchingFactor() * rawHeating;
	}
	
	public long getRawHeatingIgnoreCoolingPenalty(boolean simulate) {
		long rawHeatingIgnoreCoolingPenalty = 0L;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (!vessel.isRunning(simulate)) {
				rawHeatingIgnoreCoolingPenalty += vessel.getDecayHeating();
			}
		}
		return getBunchingFactor() * rawHeatingIgnoreCoolingPenalty;
	}
	
	public double getEffectiveHeating(boolean simulate) {
		double effectiveHeating = 0D;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (vessel.isRunning(simulate)) {
				effectiveHeating += vessel.baseProcessHeat * vessel.heatMult * vessel.baseProcessEfficiency * vessel.getSourceEfficiency() * vessel.getModeratorEfficiencyFactor() * getFluxEfficiencyFactor(vessel.getFloatingPointCriticality());
			}
		}
		return getBunchingFactor() * effectiveHeating;
	}
	
	public double getEffectiveHeatingIgnoreCoolingPenalty(boolean simulate) {
		double effectiveHeatingIgnoreCoolingPenalty = 0D;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (!vessel.isRunning(simulate)) {
				effectiveHeatingIgnoreCoolingPenalty += vessel.getFloatingPointDecayHeating();
			}
		}
		return getBunchingFactor() * effectiveHeatingIgnoreCoolingPenalty;
	}
	
	public long getHeatMultiplier(boolean simulate) {
		long rawHeatMult = 0L;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (vessel.isRunning(simulate)) {
				rawHeatMult += vessel.heatMult;
			}
		}
		return getBunchingFactor() * rawHeatMult;
	}
	
	public double getFluxEfficiencyFactor(double floatingPointCriticalityFactor) {
		return (1D + Math.exp(-2D * floatingPointCriticalityFactor)) / (1D + Math.exp(2D * ((double) flux / (double) getSurfaceFactor() - 2D * floatingPointCriticalityFactor)));
	}
	
	public double getEfficiency(boolean simulate) {
		double efficiency = 0D;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (vessel.isRunning(simulate)) {
				efficiency += vessel.heatMult * vessel.baseProcessEfficiency * vessel.getSourceEfficiency() * vessel.getModeratorEfficiencyFactor() * getFluxEfficiencyFactor(vessel.getFloatingPointCriticality());
			}
		}
		return getBunchingFactor() * efficiency;
	}
	
	public double getEfficiencyIgnoreCoolingPenalty(boolean simulate) {
		double efficiencyIgnoreCoolingPenalty = 0D;
		for (TileSaltFissionVessel vessel : vesselMap.values()) {
			if (!vessel.isRunning(simulate)) {
				++efficiencyIgnoreCoolingPenalty;
			}
		}
		return getBunchingFactor() * efficiencyIgnoreCoolingPenalty;
	}
}
