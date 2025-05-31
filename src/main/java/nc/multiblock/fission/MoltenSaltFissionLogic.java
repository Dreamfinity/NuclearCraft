package nc.multiblock.fission;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.*;
import nc.Global;
import nc.network.multiblock.*;
import nc.recipe.*;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.fission.*;
import nc.tile.fission.port.*;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import nc.util.NCMath;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;

import static nc.config.NCConfig.*;

public class MoltenSaltFissionLogic extends FissionReactorLogic {
	
	public final ObjectSet<SaltFissionVesselBunch> vesselBunches = new ObjectOpenHashSet<>();
	
	public final List<Tank> tanks = Lists.newArrayList(new Tank(FissionReactor.BASE_TANK_CAPACITY, NCRecipes.fission_emergency_cooling.validFluids.get(0)), new Tank(FissionReactor.BASE_TANK_CAPACITY, null));
	
	public RecipeInfo<BasicRecipe> emergencyCoolingRecipeInfo;
	
	public int heaterCount = 0;
	public double meanHeatingSpeedMultiplier = 0D, totalHeatingSpeedMultiplier = 0D;
	
	public MoltenSaltFissionLogic(FissionReactorLogic oldLogic) {
		super(oldLogic);
		if (oldLogic instanceof MoltenSaltFissionLogic oldMoltenSaltLogic) {
			heaterCount = oldMoltenSaltLogic.heaterCount;
			meanHeatingSpeedMultiplier = oldMoltenSaltLogic.meanHeatingSpeedMultiplier;
			totalHeatingSpeedMultiplier = oldMoltenSaltLogic.totalHeatingSpeedMultiplier;
		}
	}
	
	@Override
	public String getID() {
		return "molten_salt";
	}
	
	@Override
	public void onResetStats() {
		heaterCount = 0;
		meanHeatingSpeedMultiplier = totalHeatingSpeedMultiplier = 0D;
	}
	
	@Override
	public void onReactorFormed() {
		tanks.get(0).setCapacity(FissionReactor.BASE_TANK_CAPACITY * getCapacityMultiplier());
		tanks.get(1).setCapacity(FissionReactor.BASE_TANK_CAPACITY * getCapacityMultiplier());
		
		super.onReactorFormed();
	}
	
	@Override
	public boolean isMachineWhole() {
		return !containsBlacklistedPart();
	}
	
	public static final List<Pair<Class<? extends IFissionPart>, String>> MOLTEN_SALT_PART_BLACKLIST = Lists.newArrayList(Pair.of(TileSolidFissionCell.class, Global.MOD_ID + ".multiblock_validation.fission_reactor.prohibit_cells"), Pair.of(TileSolidFissionSink.class, Global.MOD_ID + ".multiblock_validation.fission_reactor.prohibit_sinks"));
	
	@Override
	public List<Pair<Class<? extends IFissionPart>, String>> getPartBlacklist() {
		return MOLTEN_SALT_PART_BLACKLIST;
	}
	
	@Override
	public void refreshConnections() {
		super.refreshConnections();
		refreshFilteredPorts(TileFissionVesselPort.class, TileSaltFissionVessel.class);
		refreshFilteredPorts(TileFissionHeaterPort.class, TileSaltFissionHeater.class);
		formVesselBunches();
	}
	
	public void formVesselBunches() {
		vesselBunches.clear();
		
		for (TileSaltFissionVessel vessel : getParts(TileSaltFissionVessel.class)) {
			vessel.setVesselBunch(null);
		}
		
		for (TileSaltFissionVessel vessel : getParts(TileSaltFissionVessel.class)) {
			boolean setBunch = false;
			SaltFissionVesselBunch bunch = null;
			for (EnumFacing dir : EnumFacing.VALUES) {
				TileSaltFissionVessel other = getPartMap(TileSaltFissionVessel.class).get(vessel.getPos().offset(dir).toLong());
				if (other != null && vessel.getFilterKey().equals(other.getFilterKey())) {
					SaltFissionVesselBunch otherBunch = other.getVesselBunch();
					if (bunch == null) {
						bunch = otherBunch;
					}
					else if (bunch != otherBunch) {
						if (otherBunch != null) {
							vesselBunches.remove(otherBunch);
							for (TileSaltFissionVessel otherBunchVessel : otherBunch.vesselMap.values()) {
								otherBunchVessel.setVesselBunch(bunch);
							}
							otherBunch.vesselMap.clear();
						}
						else {
							other.setVesselBunch(bunch);
						}
					}
					
					if (!setBunch && bunch != null) {
						vessel.setVesselBunch(bunch);
						setBunch = true;
					}
				}
			}
			
			if (bunch == null) {
				bunch = new SaltFissionVesselBunch();
				vessel.setVesselBunch(bunch);
				vesselBunches.add(bunch);
			}
		}
		
		for (SaltFissionVesselBunch bunch : vesselBunches) {
			bunch.init();
		}
	}
	
	@Override
	public void refreshAllFuelComponentModerators(boolean simulate) {
		for (TileSaltFissionVessel vessel : getParts(TileSaltFissionVessel.class)) {
			refreshFuelComponentModerators(vessel, componentFailCache, assumedValidCache, simulate);
		}
	}
	
	@Override
	public void incrementClusterStatsFromComponents(FissionCluster cluster, boolean simulate) {
		for (SaltFissionVesselBunch bunch : vesselBunches) {
			bunch.statsRetrieved = false;
		}
		
		for (IFissionComponent component : cluster.getComponentMap().values()) {
			if (component.isFunctional(simulate)) {
				++cluster.componentCount;
				if (component instanceof IFissionHeatingComponent) {
					if (component instanceof TileSaltFissionVessel) {
						SaltFissionVesselBunch bunch = ((TileSaltFissionVessel) component).getVesselBunch();
						++cluster.fuelComponentCount;
						if (bunch != null && !bunch.statsRetrieved) {
							bunch.statsRetrieved = true;
							cluster.rawHeating += bunch.getRawHeating(simulate);
							cluster.rawHeatingIgnoreCoolingPenalty += bunch.getRawHeatingIgnoreCoolingPenalty(simulate);
							cluster.effectiveHeating += bunch.getEffectiveHeating(simulate);
							cluster.effectiveHeatingIgnoreCoolingPenalty += bunch.getEffectiveHeatingIgnoreCoolingPenalty(simulate);
							cluster.totalHeatMult += bunch.getHeatMultiplier(simulate);
							cluster.totalEfficiency += bunch.getEfficiency(simulate);
							cluster.totalEfficiencyIgnoreCoolingPenalty += bunch.getEfficiencyIgnoreCoolingPenalty(simulate);
						}
					}
					else {
						cluster.rawHeating += ((IFissionHeatingComponent) component).getRawHeating(simulate);
						cluster.effectiveHeating += ((IFissionHeatingComponent) component).getEffectiveHeating(simulate);
					}
				}
				if (component instanceof IFissionCoolingComponent) {
					cluster.cooling += ((IFissionCoolingComponent) component).getCooling(simulate);
				}
			}
		}
	}
	
	@Override
	public void refreshReactorStats(boolean simulate) {
		super.refreshReactorStats(simulate);
		
		for (FissionCluster cluster : multiblock.getClusterMap().values()) {
			multiblock.usefulPartCount += cluster.componentCount;
			multiblock.fuelComponentCount += cluster.fuelComponentCount;
			multiblock.cooling += cluster.cooling;
			multiblock.rawHeating += cluster.rawHeating;
			// effectiveHeating += cluster.effectiveHeating;
			multiblock.totalHeatMult += cluster.totalHeatMult;
			multiblock.totalEfficiency += cluster.totalEfficiency;
		}
		
		multiblock.usefulPartCount += multiblock.passiveModeratorCache.size() + multiblock.activeModeratorCache.size() + multiblock.activeReflectorCache.size();
		double usefulPartRatio = (double) multiblock.usefulPartCount / (double) multiblock.getInteriorVolume();
		multiblock.sparsityEfficiencyMult = usefulPartRatio >= fission_sparsity_penalty_params[1] ? 1D : (1D - fission_sparsity_penalty_params[0]) * Math.sin(usefulPartRatio * Math.PI / (2D * fission_sparsity_penalty_params[1])) + fission_sparsity_penalty_params[0];
		// effectiveHeating *= sparsityEfficiencyMult;
		multiblock.totalEfficiency *= multiblock.sparsityEfficiencyMult;
		multiblock.meanHeatMult = multiblock.fuelComponentCount == 0 ? 0D : (double) multiblock.totalHeatMult / (double) multiblock.fuelComponentCount;
		multiblock.meanEfficiency = multiblock.fuelComponentCount == 0 ? 0D : multiblock.totalEfficiency / multiblock.fuelComponentCount;
		
		for (FissionCluster cluster : multiblock.getClusterMap().values()) {
			cluster.meanHeatingSpeedMultiplier = cluster.totalHeatingSpeedMultiplier = 0D;
			int clusterHeaters = 0;
			for (IFissionComponent component : cluster.getComponentMap().values()) {
				if (component instanceof TileSaltFissionHeater heater) {
					heater.heatingSpeedMultiplier = cluster.meanEfficiency * multiblock.sparsityEfficiencyMult * (cluster.rawHeating >= cluster.cooling ? 1D : (double) cluster.rawHeating / (double) cluster.cooling);
					cluster.totalHeatingSpeedMultiplier += heater.heatingSpeedMultiplier;
					++clusterHeaters;
				}
			}
			cluster.meanHeatingSpeedMultiplier = clusterHeaters == 0 ? 0D : cluster.totalHeatingSpeedMultiplier / clusterHeaters;
			totalHeatingSpeedMultiplier += cluster.meanHeatingSpeedMultiplier;
		}
		meanHeatingSpeedMultiplier = multiblock.getClusterMap().isEmpty() ? 0D : totalHeatingSpeedMultiplier / multiblock.getClusterMap().size();
	}
	
	// Server
	
	@Override
	public boolean onUpdateServer() {
		if (!multiblock.isSimulation) {
			if (heatBuffer.isFull() && fission_overheat) {
				heatBuffer.setHeatStored(0L);
				casingMeltdown();
				return true;
			}
			
			for (FissionCluster cluster : getClusterMap().values()) {
				long netHeating = cluster.getNetHeating();
				if (netHeating > 0 && cluster.connectedToWall) {
					heatBuffer.changeHeatStored(netHeating);
				}
				else {
					cluster.heatBuffer.changeHeatStored(netHeating);
				}
				
				if (cluster.heatBuffer.isFull() && fission_overheat) {
					cluster.heatBuffer.setHeatStored(0L);
					clusterMeltdown(cluster);
					return true;
				}
			}
		}
		
		updateEmergencyCooling();
		
		updateSounds();
		
		return super.onUpdateServer();
	}
	
	public void updateEmergencyCooling() {
		if (!multiblock.isReactorOn && !heatBuffer.isEmpty()) {
			refreshRecipe();
			if (canProcessInputs()) {
				produceProducts();
			}
		}
	}
	
	public void updateSounds() {
		if (multiblock.isReactorOn) {
			playFuelComponentSounds(TileSaltFissionVessel.class);
		}
	}
	
	public void refreshRecipe() {
		emergencyCoolingRecipeInfo = NCRecipes.fission_emergency_cooling.getRecipeInfoFromInputs(Collections.emptyList(), tanks.subList(0, 1));
	}
	
	public boolean canProcessInputs() {
		if (!setRecipeStats()) {
			return false;
		}
		return canProduceProducts();
	}
	
	public boolean setRecipeStats() {
		if (emergencyCoolingRecipeInfo == null) {
			return false;
		}
		return true;
	}
	
	public boolean canProduceProducts() {
		BasicRecipe recipe = emergencyCoolingRecipeInfo.recipe;
		IFluidIngredient fluidProduct = recipe.getFluidProducts().get(0);
		int productSize = fluidProduct.getMaxStackSize(0);
		if (productSize <= 0 || fluidProduct.getStack() == null) {
			return false;
		}
		
		Tank outputTank = tanks.get(1);
		return outputTank.isEmpty() || outputTank.getFluid().isFluidEqual(fluidProduct.getStack());
	}
	
	public void produceProducts() {
		Tank inputTank = tanks.get(0), outputTank = tanks.get(1);
		
		BasicRecipe recipe = emergencyCoolingRecipeInfo.recipe;
		int usedInput = NCMath.toInt(Math.min(inputTank.getFluidAmount() / recipe.getEmergencyCoolingHeatPerInputMB(), Math.min(heatBuffer.getHeatStored(), (long) FissionReactor.BASE_TANK_CAPACITY * getPartCount(TileFissionVent.class))));
		
		inputTank.changeFluidAmount(-usedInput);
		if (inputTank.getFluidAmount() <= 0) {
			inputTank.setFluidStored(null);
		}
		
		IFluidIngredient fluidProduct = recipe.getFluidProducts().get(0);
		if (fluidProduct.getMaxStackSize(0) > 0) {
			if (outputTank.isEmpty()) {
				outputTank.setFluidStored(fluidProduct.getNextStack(0));
				outputTank.setFluidAmount(usedInput);
			}
			else if (outputTank.getFluid().isFluidEqual(fluidProduct.getStack())) {
				outputTank.changeFluidAmount(usedInput);
			}
		}
		
		heatBuffer.changeHeatStored((long) (-usedInput * recipe.getEmergencyCoolingHeatPerInputMB()));
	}
	
	public long getNetClusterHeating() {
		return multiblock.rawHeating - multiblock.cooling;
	}
	
	@Override
	public void clusterMeltdown(FissionCluster cluster) {
		final Iterator<IFissionComponent> componentIterator = cluster.getComponentMap().values().iterator();
		while (componentIterator.hasNext()) {
			IFissionComponent component = componentIterator.next();
			component.onClusterMeltdown(componentIterator);
		}
		super.clusterMeltdown(cluster);
	}
	
	// Component Logic
	
	@Override
	public void distributeFluxFromFuelComponent(IFissionFuelComponent fuelComponent, final ObjectSet<IFissionFuelComponent> fluxSearchCache, final Long2ObjectMap<IFissionComponent> lineFailCache, final Long2ObjectMap<IFissionComponent> currentAssumedValidCache, boolean simulate) {
		fuelComponent.defaultDistributeFlux(fluxSearchCache, lineFailCache, assumedValidCache, simulate);
	}
	
	@Override
	public IFissionFuelComponent getNextFuelComponent(IFissionFuelComponent fuelComponent, BlockPos pos) {
		return getPartMap(TileSaltFissionVessel.class).get(pos.toLong());
	}
	
	@Override
	public void refreshFuelComponentLocal(IFissionFuelComponent fuelComponent, boolean simulate) {
		fuelComponent.defaultRefreshLocal(simulate);
	}
	
	@Override
	public void refreshFuelComponentModerators(IFissionFuelComponent fuelComponent, final Long2ObjectMap<IFissionComponent> currentComponentFailCache, final Long2ObjectMap<IFissionComponent> currentAssumedValidCache, boolean simulate) {
		fuelComponent.defaultRefreshModerators(componentFailCache, assumedValidCache, simulate);
	}
	
	@Override
	public boolean isShieldActiveModerator(TileFissionShield shield, boolean activeModeratorPos) {
		return super.isShieldActiveModerator(shield, activeModeratorPos);
	}
	
	@Override
	public @Nonnull List<Tank> getVentTanks(List<Tank> backupTanks) {
		return multiblock.isAssembled() ? tanks : backupTanks;
	}
	
	// Client
	
	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason) {
		super.writeToLogicTag(logicTag, syncReason);
		writeTanks(tanks, logicTag, "tanks");
		logicTag.setInteger("heaterCount", heaterCount);
		logicTag.setDouble("meanHeatingSpeedMultiplier", meanHeatingSpeedMultiplier);
		logicTag.setDouble("totalHeatingSpeedMultiplier", totalHeatingSpeedMultiplier);
	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason) {
		super.readFromLogicTag(logicTag, syncReason);
		readTanks(tanks, logicTag, "tanks");
		heaterCount = logicTag.getInteger("heaterCount");
		meanHeatingSpeedMultiplier = logicTag.getDouble("meanHeatingSpeedMultiplier");
		totalHeatingSpeedMultiplier = logicTag.getDouble("totalHeatingSpeedMultiplier");
	}
	
	// Packets
	
	@Override
	public SaltFissionUpdatePacket getMultiblockUpdatePacket() {
		return new SaltFissionUpdatePacket(multiblock.controller.getTilePos(), multiblock.isReactorOn, heatBuffer, multiblock.clusterCount, multiblock.cooling, multiblock.rawHeating, multiblock.totalHeatMult, multiblock.meanHeatMult, multiblock.fuelComponentCount, multiblock.usefulPartCount, multiblock.totalEfficiency, multiblock.meanEfficiency, multiblock.sparsityEfficiencyMult, meanHeatingSpeedMultiplier, totalHeatingSpeedMultiplier);
	}
	
	@Override
	public void onMultiblockUpdatePacket(FissionUpdatePacket message) {
		super.onMultiblockUpdatePacket(message);
		if (message instanceof SaltFissionUpdatePacket packet) {
			meanHeatingSpeedMultiplier = packet.meanHeatingSpeedMultiplier;
			totalHeatingSpeedMultiplier = packet.totalHeatingSpeedMultiplier;
		}
	}
	
	// Clear Material
	
	@Override
	public void clearAllMaterial() {
		super.clearAllMaterial();
		for (Tank tank : tanks) {
			tank.setFluidStored(null);
		}
	}
}
