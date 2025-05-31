package nc.tile.fission;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.*;
import nc.*;
import nc.handler.TileInfoHandler;
import nc.multiblock.PlacementRule;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.fission.*;
import nc.network.tile.multiblock.SaltFissionHeaterUpdatePacket;
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.*;
import java.util.*;

import static nc.config.NCConfig.enable_mek_gas;
import static nc.init.NCCoolantFluids.COOLANTS;
import static nc.util.FluidStackHelper.INGOT_BLOCK_VOLUME;
import static nc.util.PosHelper.DEFAULT_NON;

public class TileSaltFissionHeater extends TileFissionPart implements IBasicProcessor<TileSaltFissionHeater, SaltFissionHeaterUpdatePacket>, ITileFilteredFluid, IFissionCoolingComponent, IFissionPortTarget<TileFissionHeaterPort, TileSaltFissionHeater> {
	
	public static final Object2ObjectMap<String, String> DYN_COOLANT_NAME_MAP = new Object2ObjectOpenHashMap<>();
	
	protected final ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TileSaltFissionHeater, SaltFissionHeaterUpdatePacket> info;
	
	protected final @Nonnull String inventoryName;
	
	protected final @Nonnull NonNullList<ItemStack> inventoryStacks;
	protected final @Nonnull NonNullList<ItemStack> consumedStacks;
	
	protected @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Collections.emptyList());
	
	protected final @Nonnull List<Tank> tanks;
	protected final @Nonnull List<Tank> consumedTanks;
	
	protected final @Nonnull List<Tank> filterTanks;
	
	protected @Nonnull FluidConnection[] fluidConnections;
	
	protected @Nonnull FluidTileWrapper[] fluidSides = ITileFluid.getDefaultFluidSides(this);
	protected @Nonnull GasTileWrapper gasWrapper = new GasTileWrapper(this);
	
	protected int baseProcessCooling;
	protected PlacementRule<FissionReactor, IFissionPart> placementRule = FissionPlacement.RULE_MAP.get("");
	
	public double heatingSpeedMultiplier; // Based on the cluster efficiency, but with heat/cooling taken into account
	
	public double time, resetTime;
	public boolean isProcessing, canProcessInputs, hasConsumed;
	public boolean isRunningSimulated;
	
	protected RecipeInfo<BasicRecipe> recipeInfo = null;
	
	protected final Set<EntityPlayer> updatePacketListeners = new ObjectOpenHashSet<>();
	
	public String heaterType, coolantName;
	
	protected FissionCluster cluster = null;
	protected long heat = 0L;
	protected boolean isInValidPosition = false;
	
	public long clusterHeatStored, clusterHeatCapacity;
	
	protected BlockPos masterPortPos = DEFAULT_NON;
	protected TileFissionHeaterPort masterPort = null;
	
	/**
	 * Don't use this constructor!
	 */
	public TileSaltFissionHeater() {
		super(CuboidalPartPositionType.INTERIOR);
		info = TileInfoHandler.getProcessorContainerInfo("salt_fission_heater");
		
		inventoryName = Global.MOD_ID + ".container." + info.name;
		
		inventoryStacks = NonNullList.withSize(0, ItemStack.EMPTY);
		consumedStacks = info.getConsumedStacks();
		
		tanks = Lists.newArrayList(new Tank(INGOT_BLOCK_VOLUME, null), new Tank(INGOT_BLOCK_VOLUME, new ObjectOpenHashSet<>()));
		consumedTanks = Lists.newArrayList(new Tank(INGOT_BLOCK_VOLUME, new ObjectOpenHashSet<>()));
		
		filterTanks = Lists.newArrayList(new Tank(1000, null), new Tank(1000, new ObjectOpenHashSet<>()));
		
		fluidConnections = ITileFluid.fluidConnectionAll(info.nonTankSorptions());
	}
	
	public TileSaltFissionHeater(String heaterType, String coolantName) {
		this();
		this.heaterType = heaterType;
		this.coolantName = coolantName;
		tanks.get(0).setAllowedFluids(Collections.singleton(coolantName));
		filterTanks.get(0).setAllowedFluids(Collections.singleton(coolantName));
	}
	
	public static class Meta extends TileSaltFissionHeater {
		
		public Meta(String heaterType, String coolantName) {
			super(heaterType, coolantName);
		}
		
		protected Meta(int coolantID) {
			super(COOLANTS.get(coolantID), COOLANTS.get(coolantID) + "_nak");
		}
		
		@Override
		public boolean shouldRefresh(World worldIn, BlockPos posIn, IBlockState oldState, IBlockState newState) {
			return oldState != newState;
		}
	}
	
	public static class Standard extends Meta {
		
		public Standard() {
			super("standard", "nak");
		}
	}
	
	public static class Iron extends Meta {
		
		public Iron() {
			super(1);
		}
	}
	
	public static class Redstone extends Meta {
		
		public Redstone() {
			super(2);
		}
	}
	
	public static class Quartz extends Meta {
		
		public Quartz() {
			super(3);
		}
	}
	
	public static class Obsidian extends Meta {
		
		public Obsidian() {
			super(4);
		}
	}
	
	public static class NetherBrick extends Meta {
		
		public NetherBrick() {
			super(5);
		}
	}
	
	public static class Glowstone extends Meta {
		
		public Glowstone() {
			super(6);
		}
	}
	
	public static class Lapis extends Meta {
		
		public Lapis() {
			super(7);
		}
	}
	
	public static class Gold extends Meta {
		
		public Gold() {
			super(8);
		}
	}
	
	public static class Prismarine extends Meta {
		
		public Prismarine() {
			super(9);
		}
	}
	
	public static class Slime extends Meta {
		
		public Slime() {
			super(10);
		}
	}
	
	public static class EndStone extends Meta {
		
		public EndStone() {
			super(11);
		}
	}
	
	public static class Purpur extends Meta {
		
		public Purpur() {
			super(12);
		}
	}
	
	public static class Diamond extends Meta {
		
		public Diamond() {
			super(13);
		}
	}
	
	public static class Emerald extends Meta {
		
		public Emerald() {
			super(14);
		}
	}
	
	public static class Copper extends Meta {
		
		public Copper() {
			super(15);
		}
	}
	
	public static class Tin extends Meta {
		
		public Tin() {
			super(16);
		}
	}
	
	public static class Lead extends Meta {
		
		public Lead() {
			super(17);
		}
	}
	
	public static class Boron extends Meta {
		
		public Boron() {
			super(18);
		}
	}
	
	public static class Lithium extends Meta {
		
		public Lithium() {
			super(19);
		}
	}
	
	public static class Magnesium extends Meta {
		
		public Magnesium() {
			super(20);
		}
	}
	
	public static class Manganese extends Meta {
		
		public Manganese() {
			super(21);
		}
	}
	
	public static class Aluminum extends Meta {
		
		public Aluminum() {
			super(22);
		}
	}
	
	public static class Silver extends Meta {
		
		public Silver() {
			super(23);
		}
	}
	
	public static class Fluorite extends Meta {
		
		public Fluorite() {
			super(24);
		}
	}
	
	public static class Villiaumite extends Meta {
		
		public Villiaumite() {
			super(25);
		}
	}
	
	public static class Carobbiite extends Meta {
		
		public Carobbiite() {
			super(26);
		}
	}
	
	public static class Arsenic extends Meta {
		
		public Arsenic() {
			super(27);
		}
	}
	
	public static class LiquidNitrogen extends Meta {
		
		public LiquidNitrogen() {
			super(28);
		}
	}
	
	public static class LiquidHelium extends Meta {
		
		public LiquidHelium() {
			super(29);
		}
	}
	
	public static class Enderium extends Meta {
		
		public Enderium() {
			super(30);
		}
	}
	
	public static class Cryotheum extends Meta {
		
		public Cryotheum() {
			super(31);
		}
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
	public boolean isValidHeatConductor(final Long2ObjectMap<IFissionComponent> componentFailCache, final Long2ObjectMap<IFissionComponent> assumedValidCache, boolean simulate) {
		if (!isProcessing(false, simulate) || componentFailCache.containsKey(pos.toLong())) {
			return isInValidPosition = false;
		}
		else if (placementRule.requiresRecheck()) {
			isInValidPosition = placementRule.satisfied(this, simulate);
			if (isInValidPosition) {
				assumedValidCache.put(pos.toLong(), this);
			}
			return isInValidPosition;
		}
		else if (isInValidPosition) {
			return true;
		}
		return isInValidPosition = placementRule.satisfied(this, simulate);
	}
	
	@Override
	public boolean isFunctional(boolean simulate) {
		return isRunning(simulate);
	}
	
	@Override
	public void resetStats() {
		isInValidPosition = false;
		heatingSpeedMultiplier = 0;
		
		refreshAll();
	}
	
	@Override
	public boolean isClusterRoot() {
		return false;
	}
	
	@Override
	public void clusterSearch(Integer id, final Object2IntMap<IFissionComponent> clusterSearchCache, final Long2ObjectMap<IFissionComponent> componentFailCache, final Long2ObjectMap<IFissionComponent> assumedValidCache, boolean simulate) {
		refreshDirty();
		
		IFissionCoolingComponent.super.clusterSearch(id, clusterSearchCache, componentFailCache, assumedValidCache, simulate);
		
		refreshIsProcessing(true, simulate);
	}
	
	public void refreshIsProcessing(boolean checkValid, boolean simulate) {
		if (simulate) {
			isProcessing = false;
			isRunningSimulated = isProcessing(checkValid, simulate);
		}
		else {
			isProcessing = isProcessing(checkValid, simulate);
			isRunningSimulated = false;
		}
		hasConsumed = hasConsumed();
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
	public boolean isNullifyingSources(EnumFacing side, boolean simulate) {
		return false;
	}
	
	@Override
	public long getCooling(boolean simulate) {
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
		masterPort = multiblock == null ? null : multiblock.getPartMap(TileFissionHeaterPort.class).get(masterPortPos.toLong());
		if (masterPort == null) {
			masterPortPos = DEFAULT_NON;
		}
	}
	
	@Override
	public void onPortRefresh() {
		refreshAll();
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
			boolean shouldRefresh = reactor != null && reactor.isReactorOn && (cluster == null || !isInValidPosition) && isProcessing(false, false);
			
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
		refreshIsProcessing(true, false);
	}
	
	@Override
	public void refreshRecipe() {
		boolean hasConsumed = getHasConsumed();
		recipeInfo = NCRecipes.coolant_heater.getRecipeInfoFromHeaterInputs(heaterType, getFluidInputs(hasConsumed));
		if (info.consumesInputs) {
			consumeInputs();
		}
	}
	
	@Override
	public void refreshActivity() {
		canProcessInputs = canProcessInputs();
	}
	
	public boolean isRunning(boolean simulate) {
		return simulate ? isRunningSimulated : isProcessing;
	}
	
	// IProcessor
	
	@Override
	public ProcessorContainerInfoImpl.BasicProcessorContainerInfo<TileSaltFissionHeater, SaltFissionHeaterUpdatePacket> getContainerInfo() {
		return info;
	}
	
	@Override
	public BasicRecipeHandler getRecipeHandler() {
		return NCRecipes.coolant_heater;
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
		baseProcessCooling = recipe == null ? 0 : recipe.getCoolantHeaterCoolingRate();
		placementRule = FissionPlacement.RULE_MAP.get(recipe == null ? "" : recipe.getCoolantHeaterPlacementRule());
	}
	
	@Override
	public @Nonnull NonNullList<ItemStack> getConsumedStacks() {
		return consumedStacks;
	}
	
	@Override
	public @Nonnull List<Tank> getConsumedTanks() {
		return consumedTanks;
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
		return hasConsumed;
	}
	
	@Override
	public void setHasConsumed(boolean hasConsumed) {
		this.hasConsumed = hasConsumed;
	}
	
	@Override
	public double getSpeedMultiplier() {
		return heatingSpeedMultiplier;
	}
	
	@Override
	public double getPowerMultiplier() {
		return 0D;
	}
	
	@Override
	public boolean isProcessing() {
		return !isSimulation() && isProcessing(true, false);
	}
	
	public boolean isProcessing(boolean checkValid, boolean simulate) {
		return readyToProcess(checkValid);
	}
	
	@Override
	public boolean readyToProcess() {
		return readyToProcess(true);
	}
	
	public boolean readyToProcess(boolean checkValid) {
		return canProcessInputs && hasConsumed && isMultiblockAssembled() && (!checkValid || (cluster != null && isInValidPosition));
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
		for (Tank tank : consumedTanks) {
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
		return heaterType;
	}
	
	// ITileGui
	
	@Override
	public Set<EntityPlayer> getTileUpdatePacketListeners() {
		return updatePacketListeners;
	}
	
	@Override
	public SaltFissionHeaterUpdatePacket getTileUpdatePacket() {
		return new SaltFissionHeaterUpdatePacket(pos, isProcessing, time, 1D, getTanks(), masterPortPos, getFilterTanks(), cluster);
	}
	
	@Override
	public void onTileUpdatePacket(SaltFissionHeaterUpdatePacket message) {
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
		nbt.setString("heaterName", heaterType);
		writeTanks(nbt);
		
		writeProcessorNBT(nbt);
		
		nbt.setInteger("baseProcessCooling", baseProcessCooling);
		nbt.setDouble("heatingSpeedMultiplier", heatingSpeedMultiplier);
		
		nbt.setLong("clusterHeat", heat);
		nbt.setBoolean("isInValidPosition", isInValidPosition);
		
		nbt.setBoolean("isRunningSimulated", isRunningSimulated);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		if (nbt.hasKey("heaterName")) {
			heaterType = nbt.getString("heaterName");
		}
		
		if (DYN_COOLANT_NAME_MAP.containsKey(heaterType)) {
			coolantName = DYN_COOLANT_NAME_MAP.get(heaterType);
			tanks.get(0).setAllowedFluids(Collections.singleton(coolantName));
			filterTanks.get(0).setAllowedFluids(Collections.singleton(coolantName));
		}
		
		readTanks(nbt);
		
		readProcessorNBT(nbt);
		
		baseProcessCooling = nbt.getInteger("baseProcessCooling");
		heatingSpeedMultiplier = nbt.getDouble("heatingSpeedMultiplier");
		
		heat = nbt.getLong("clusterHeat");
		isInValidPosition = nbt.getBoolean("isInValidPosition");
		
		isRunningSimulated = nbt.getBoolean("isRunningSimulated");
	}
	
	@Override
	public NBTTagCompound writeTanks(NBTTagCompound nbt) {
		for (int i = 0; i < tanks.size(); ++i) {
			tanks.get(i).writeToNBT(nbt, "tanks" + i);
		}
		for (int i = 0; i < filterTanks.size(); ++i) {
			filterTanks.get(i).writeToNBT(nbt, "filterTanks" + i);
		}
		for (int i = 0; i < consumedTanks.size(); ++i) {
			consumedTanks.get(i).writeToNBT(nbt, "consumedTanks" + i);
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
		for (int i = 0; i < consumedTanks.size(); ++i) {
			consumedTanks.get(i).readFromNBT(nbt, "consumedTanks" + i);
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
		return "heater";
	}
	
	@Override
	public Object getOCInfo() {
		Object2ObjectMap<String, Object> entry = new Object2ObjectLinkedOpenHashMap<>();
		List<Tank> tanks = getTanks();
		entry.put("coolant", OCHelper.tankInfo(tanks.get(0)));
		entry.put("hot_coolant", OCHelper.tankInfo(tanks.get(1)));
		entry.put("type", heaterType);
		entry.put("cooling", getCooling(false));
		entry.put("speed_multiplier", getSpeedMultiplier());
		entry.put("is_processing", getIsProcessing());
		entry.put("current_time", getCurrentTime());
		entry.put("base_process_time", getBaseProcessTime());
		return entry;
	}
}
