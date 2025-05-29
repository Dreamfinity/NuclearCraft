package nc.tile.fission;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.*;
import nc.*;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.fission.*;
import nc.network.tile.multiblock.FissionCoolerUpdatePacket;
import nc.recipe.*;
import nc.tile.fission.IFissionFuelComponent.ModeratorBlockInfo;
import nc.tile.fission.port.*;
import nc.tile.fluid.*;
import nc.tile.internal.fluid.*;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import nc.tile.processor.IBasicProcessor;
import nc.tile.processor.info.ProcessorContainerInfoImpl;
import nc.util.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.*;
import java.util.*;

import static nc.config.NCConfig.enable_mek_gas;
import static nc.util.FluidStackHelper.INGOT_BLOCK_VOLUME;
import static nc.util.PosHelper.DEFAULT_NON;

public class TileFissionCooler extends TileFissionPart implements IBasicProcessor<TileFissionCooler, FissionCoolerUpdatePacket>, ITileFilteredFluid, IFissionCoolingComponent, IFissionPortTarget<TileFissionCoolerPort, TileFissionCooler> {
	
	protected final ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TileFissionCooler, FissionCoolerUpdatePacket> info;
	
	protected final @Nonnull String inventoryName;
	
	protected final @Nonnull NonNullList<ItemStack> inventoryStacks;
	
	protected @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Collections.emptyList());
	
	protected final @Nonnull List<Tank> tanks;
	
	protected final @Nonnull List<Tank> filterTanks;
	
	protected @Nonnull FluidConnection[] fluidConnections;
	
	protected @Nonnull FluidTileWrapper[] fluidSides = ITileFluid.getDefaultFluidSides(this);
	protected @Nonnull GasTileWrapper gasWrapper = new GasTileWrapper(this);
	
	protected int baseProcessCooling;
	
	public double time, resetTime;
	public boolean isProcessing, canProcessInputs;
	
	protected RecipeInfo<BasicRecipe> recipeInfo = null;
	
	protected final Set<EntityPlayer> updatePacketListeners = new ObjectOpenHashSet<>();
	
	protected FissionCluster cluster = null;
	protected long heat = 0L;
	
	public long clusterHeatStored, clusterHeatCapacity;
	
	protected BlockPos masterPortPos = DEFAULT_NON;
	protected TileFissionCoolerPort masterPort = null;
	
	public TileFissionCooler() {
		super(CuboidalPartPositionType.INTERIOR);
		info = TileInfoHandler.getProcessorContainerInfo("fission_cooler");
		
		inventoryName = Global.MOD_ID + ".container." + info.name;
		
		inventoryStacks = NonNullList.withSize(0, ItemStack.EMPTY);
		
		Set<String> validFluids = NCRecipes.fission_emergency_cooling.validFluids.get(0);
		tanks = Lists.newArrayList(new Tank(INGOT_BLOCK_VOLUME, validFluids), new Tank(INGOT_BLOCK_VOLUME, new ObjectOpenHashSet<>()));
		
		filterTanks = Lists.newArrayList(new Tank(1000, validFluids), new Tank(1000, new ObjectOpenHashSet<>()));
		
		fluidConnections = ITileFluid.fluidConnectionAll(info.nonTankSorptions());
	}
	
	@Override
	public void onMachineAssembled(FissionReactor multiblock) {
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}
	
	// IFissionComponent
	
	@Override
	public @Nullable FissionCluster getCluster() {
		return cluster;
	}
	
	@Override
	public void setClusterInternal(@Nullable FissionCluster cluster) {
		this.cluster = cluster;
	}
	
	@Override
	public boolean isValidHeatConductor(final Long2ObjectMap<IFissionComponent> componentFailCache, final Long2ObjectMap<IFissionComponent> assumedValidCache) {
		return true;
	}
	
	@Override
	public boolean isFunctional() {
		return isProcessing;
	}
	
	@Override
	public void resetStats() {
		refreshAll();
	}
	
	@Override
	public boolean isClusterRoot() {
		return false;
	}
	
	@Override
	public void clusterSearch(Integer id, final Object2IntMap<IFissionComponent> clusterSearchCache, final Long2ObjectMap<IFissionComponent> componentFailCache, final Long2ObjectMap<IFissionComponent> assumedValidCache) {
		refreshDirty();
		
		IFissionCoolingComponent.super.clusterSearch(id, clusterSearchCache, componentFailCache, assumedValidCache);
		
		refreshIsProcessing(true);
	}
	
	public void refreshIsProcessing(boolean checkCluster) {
		isProcessing = isProcessing(checkCluster);
	}
	
	@Override
	public long getHeatStored() {
		return heat;
	}
	
	@Override
	public void setHeatStored(long heat) {
		this.heat = heat;
	}
	
	@Override
	public void onClusterMeltdown(Iterator<IFissionComponent> componentIterator) {
	
	}
	
	@Override
	public boolean isNullifyingSources(EnumFacing side) {
		return false;
	}
	
	@Override
	public long getCooling() {
		return baseProcessCooling;
	}
	
	// Moderator Line
	
	@Override
	public ModeratorBlockInfo getModeratorBlockInfo(EnumFacing dir, boolean activeModeratorPos) {
		return new ModeratorBlockInfo(pos, this, false, false, 0, 0D);
	}
	
	@Override
	public void onAddedToModeratorCache(ModeratorBlockInfo thisInfo) {}
	
	// IFissionPortTarget
	
	@Override
	public BlockPos getMasterPortPos() {
		return masterPortPos;
	}
	
	@Override
	public void setMasterPortPos(BlockPos pos) {
		masterPortPos = pos;
	}
	
	@Override
	public void clearMasterPort() {
		masterPort = null;
		masterPortPos = DEFAULT_NON;
	}
	
	@Override
	public void refreshMasterPort() {
		FissionReactor multiblock = getMultiblock();
		masterPort = multiblock == null ? null : multiblock.getPartMap(TileFissionCoolerPort.class).get(masterPortPos.toLong());
		if (masterPort == null) {
			masterPortPos = DEFAULT_NON;
		}
	}
	
	@Override
	public boolean onPortRefresh(boolean simulateMultiblockRefresh) {
		if (simulateMultiblockRefresh) {
			FissionReactor reactor = getMultiblock();
			return reactor != null && reactor.isReactorOn && cluster == null && isProcessing(false);
		}
		else {
			refreshAll();
			return false;
		}
	}
	
	// Ticking
	
	@Override
	public void onLoad() {
		super.onLoad();
		if (!world.isRemote) {
			refreshMasterPort();
			refreshAll();
		}
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			FissionReactor reactor = getMultiblock();
			boolean shouldRefresh = reactor != null && reactor.isReactorOn && cluster == null && isProcessing(false);
			
			if (onTick()) {
				markDirty();
			}
			
			if (shouldRefresh) {
				getMultiblock().refreshFlag = true;
			}
		}
	}
	
	@Override
	public void refreshAll() {
		refreshDirty();
		refreshIsProcessing(true);
	}
	
	@Override
	public void refreshActivity() {
		canProcessInputs = canProcessInputs();
	}
	
	// IProcessor
	
	@Override
	public ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TileFissionCooler, FissionCoolerUpdatePacket> getContainerInfo() {
		return info;
	}
	
	@Override
	public BasicRecipeHandler getRecipeHandler() {
		return NCRecipes.fission_emergency_cooling;
	}
	
	@Override
	public RecipeInfo<BasicRecipe> getRecipeInfo() {
		return recipeInfo;
	}
	
	@Override
	public void setRecipeInfo(RecipeInfo<BasicRecipe> recipeInfo) {
		this.recipeInfo = recipeInfo;
	}
	
	@Override
	public void setRecipeStats(@Nullable BasicRecipe recipe) {
		// TODO
		baseProcessCooling = recipe == null ? 0 : recipe.getCoolantHeaterCoolingRate();
	}
	
	@Override
	public @Nonnull NonNullList<ItemStack> getConsumedStacks() {
		return getInventoryStacks();
	}
	
	@Override
	public @Nonnull List<Tank> getConsumedTanks() {
		return getTanks();
	}
	
	@Override
	public double getBaseProcessTime() {
		return 1D;
	}
	
	@Override
	public void setBaseProcessTime(double baseProcessTime) {}
	
	@Override
	public double getBaseProcessPower() {
		return 0D;
	}
	
	@Override
	public void setBaseProcessPower(double baseProcessPower) {}
	
	@Override
	public double getCurrentTime() {
		return time;
	}
	
	@Override
	public void setCurrentTime(double time) {
		this.time = time;
	}
	
	@Override
	public double getResetTime() {
		return resetTime;
	}
	
	@Override
	public void setResetTime(double resetTime) {
		this.resetTime = resetTime;
	}
	
	@Override
	public boolean getIsProcessing() {
		return isProcessing;
	}
	
	@Override
	public void setIsProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}
	
	@Override
	public boolean getCanProcessInputs() {
		return canProcessInputs;
	}
	
	@Override
	public void setCanProcessInputs(boolean canProcessInputs) {
		this.canProcessInputs = canProcessInputs;
	}
	
	@Override
	public boolean getHasConsumed() {
		return false;
	}
	
	@Override
	public void setHasConsumed(boolean hasConsumed) {}
	
	@Override
	public double getSpeedMultiplier() {
		return 1D;
	}
	
	@Override
	public double getPowerMultiplier() {
		return 0D;
	}
	
	@Override
	public boolean isProcessing() {
		return isProcessing(true);
	}
	
	public boolean isProcessing(boolean checkCluster) {
		return readyToProcess(checkCluster);
	}
	
	@Override
	public boolean readyToProcess() {
		return readyToProcess(true);
	}
	
	public boolean readyToProcess(boolean checkCluster) {
		return canProcessInputs && isMultiblockAssembled() && (!checkCluster || cluster != null);
	}
	
	@Override
	public void finishProcess() {
		int oldProcessCooling = baseProcessCooling;
		produceProducts();
		refreshRecipe();
		time = Math.max(0D, time - 1D);
		refreshActivityOnProduction();
		if (!canProcessInputs) {
			time = 0;
		}
		
		FissionReactor multiblock = getMultiblock();
		if (multiblock != null) {
			if (canProcessInputs) {
				if (oldProcessCooling != baseProcessCooling) {
					multiblock.addClusterToRefresh(cluster);
				}
			}
			else {
				multiblock.refreshFlag = true;
			}
		}
	}
	
	// ITileInventory
	
	@Override
	public String getName() {
		return inventoryName;
	}
	
	@Override
	public @Nonnull NonNullList<ItemStack> getInventoryStacks() {
		return inventoryStacks;
	}
	
	@Override
	public void markDirty() {
		refreshDirty();
		super.markDirty();
	}
	
	@Override
	public @Nonnull InventoryConnection[] getInventoryConnections() {
		return inventoryConnections;
	}
	
	@Override
	public void setInventoryConnections(@Nonnull InventoryConnection[] connections) {
		inventoryConnections = connections;
	}
	
	@Override
	public ItemOutputSetting getItemOutputSetting(int slot) {
		return ItemOutputSetting.DEFAULT;
	}
	
	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting) {}
	
	// ITileFluid
	
	@Override
	public @Nonnull List<Tank> getTanks() {
		return !DEFAULT_NON.equals(masterPortPos) ? masterPort.getTanks() : tanks;
	}
	
	@Override
	public @Nonnull FluidConnection[] getFluidConnections() {
		return fluidConnections;
	}
	
	@Override
	public void setFluidConnections(@Nonnull FluidConnection[] connections) {
		fluidConnections = connections;
	}
	
	@Override
	public @Nonnull FluidTileWrapper[] getFluidSides() {
		return !DEFAULT_NON.equals(masterPortPos) ? masterPort.getFluidSides() : fluidSides;
	}
	
	@Override
	public @Nonnull GasTileWrapper getGasWrapper() {
		return !DEFAULT_NON.equals(masterPortPos) ? masterPort.getGasWrapper() : gasWrapper;
	}
	
	@Override
	public boolean getInputTanksSeparated() {
		return false;
	}
	
	@Override
	public void setInputTanksSeparated(boolean separated) {}
	
	@Override
	public boolean getVoidUnusableFluidInput(int tankNumber) {
		return false;
	}
	
	@Override
	public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput) {}
	
	@Override
	public TankOutputSetting getTankOutputSetting(int tankNumber) {
		return TankOutputSetting.DEFAULT;
	}
	
	@Override
	public void setTankOutputSetting(int tankNumber, TankOutputSetting setting) {}
	
	@Override
	public boolean hasConfigurableFluidConnections() {
		return false;
	}
	
	@Override
	public void clearAllTanks() {
		for (Tank tank : tanks) {
			tank.setFluidStored(null);
		}
		refreshAll();
	}
	
	// ITileFilteredFluid
	
	@Override
	public @Nonnull List<Tank> getTanksInternal() {
		return tanks;
	}
	
	@Override
	public @Nonnull List<Tank> getFilterTanks() {
		return !DEFAULT_NON.equals(masterPortPos) ? masterPort.getFilterTanks() : filterTanks;
	}
	
	@Override
	public boolean canModifyFilter(int tank) {
		return !isMultiblockAssembled();
	}
	
	@Override
	public void onFilterChanged(int slot) {
		markDirty();
	}
	
	@Override
	public Object getFilterKey() {
		return getFilterTanks().get(0).getFluidName();
	}
	
	// ITileGui
	
	@Override
	public Set<EntityPlayer> getTileUpdatePacketListeners() {
		return updatePacketListeners;
	}
	
	@Override
	public FissionCoolerUpdatePacket getTileUpdatePacket() {
		return new FissionCoolerUpdatePacket(pos, isProcessing, time, 1D, getTanks(), masterPortPos, getFilterTanks(), cluster);
	}
	
	@Override
	public void onTileUpdatePacket(FissionCoolerUpdatePacket message) {
		IBasicProcessor.super.onTileUpdatePacket(message);
		if (DEFAULT_NON.equals(masterPortPos = message.masterPortPos) ^ masterPort == null) {
			refreshMasterPort();
		}
		TankInfo.readInfoList(message.filterTankInfos, getFilterTanks());
		clusterHeatStored = message.clusterHeatStored;
		clusterHeatCapacity = message.clusterHeatCapacity;
	}
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		writeTanks(nbt);
		writeProcessorNBT(nbt);
		
		nbt.setInteger("baseProcessCooling", baseProcessCooling);
		
		nbt.setLong("clusterHeat", heat);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		readTanks(nbt);
		readProcessorNBT(nbt);
		
		baseProcessCooling = nbt.getInteger("baseProcessCooling");
		
		heat = nbt.getLong("clusterHeat");
	}
	
	@Override
	public NBTTagCompound writeTanks(NBTTagCompound nbt) {
		for (int i = 0; i < tanks.size(); ++i) {
			tanks.get(i).writeToNBT(nbt, "tanks" + i);
		}
		for (int i = 0; i < filterTanks.size(); ++i) {
			filterTanks.get(i).writeToNBT(nbt, "filterTanks" + i);
		}
		return nbt;
	}
	
	@Override
	public void readTanks(NBTTagCompound nbt) {
		for (int i = 0; i < tanks.size(); ++i) {
			tanks.get(i).readFromNBT(nbt, "tanks" + i);
		}
		for (int i = 0; i < filterTanks.size(); ++i) {
			filterTanks.get(i).readFromNBT(nbt, "filterTanks" + i);
		}
	}
	
	// Capability
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || (ModCheck.mekanismLoaded() && enable_mek_gas && capability == CapabilityHelper.GAS_HANDLER_CAPABILITY)) {
			return !getTanks().isEmpty() && hasFluidSideCapability(side);
		}
		return super.hasCapability(capability, side);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			if (!getTanks().isEmpty() && hasFluidSideCapability(side)) {
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidSide(nonNullSide(side)));
			}
			return null;
		}
		else if (ModCheck.mekanismLoaded() && capability == CapabilityHelper.GAS_HANDLER_CAPABILITY) {
			if (enable_mek_gas && !getTanks().isEmpty() && hasFluidSideCapability(side)) {
				return CapabilityHelper.GAS_HANDLER_CAPABILITY.cast(getGasWrapper());
			}
			return null;
		}
		return super.getCapability(capability, side);
	}
	
	// OpenComputers
	
	@Override
	public String getOCKey() {
		return "cooler";
	}
	
	@Override
	public Object getOCInfo() {
		Object2ObjectMap<String, Object> entry = new Object2ObjectLinkedOpenHashMap<>();
		List<Tank> tanks = getTanks();
		entry.put("coolant", OCHelper.tankInfo(tanks.get(0)));
		entry.put("hot_coolant", OCHelper.tankInfo(tanks.get(1)));
		entry.put("cooling", getCooling());
		entry.put("speed_multiplier", getSpeedMultiplier());
		entry.put("is_processing", getIsProcessing());
		entry.put("current_time", getCurrentTime());
		entry.put("base_process_time", getBaseProcessTime());
		return entry;
	}
}
