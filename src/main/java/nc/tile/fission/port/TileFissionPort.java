package nc.tile.fission.port;

import it.unimi.dsi.fastutil.objects.*;
import nc.multiblock.cuboidal.*;
import nc.multiblock.fission.FissionReactor;
import nc.tile.fission.TileFissionPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.*;

import static nc.block.property.BlockProperties.AXIS_ALL;
import static nc.util.PosHelper.DEFAULT_NON;

public abstract class TileFissionPort<PORT extends TileFissionPort<PORT, TARGET>, TARGET extends IFissionPortTarget<PORT, TARGET>> extends TileFissionPart implements ITickable, IFissionPort<PORT, TARGET> {
	
	protected final Class<PORT> portClass;
	protected BlockPos masterPortPos = DEFAULT_NON;
	protected PORT masterPort = null;
	protected ObjectSet<TARGET> targets = new ObjectOpenHashSet<>();
	public boolean refreshTargetsFlag = false;
	
	public Axis axis = Axis.Z;
	
	public TileFissionPort(Class<PORT> portClass) {
		super(CuboidalPartPositionType.WALL);
		this.portClass = portClass;
	}
	
	@Override
	public void onMachineAssembled(FissionReactor multiblock) {
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
		if (!world.isRemote) {
			EnumFacing posFacing = getPartPosition().getFacing();
			if (posFacing != null) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(AXIS_ALL, posFacing.getAxis()), 2);
			}
		}
	}
	
	@Override
	public @Nonnull PartPosition getPartPosition() {
		PartPosition partPos = super.getPartPosition();
		if (partPos.getFacing() != null) {
			axis = partPos.getFacing().getAxis();
		}
		return partPos;
	}
	
	@Override
	public ObjectSet<TARGET> getTargets() {
		return !DEFAULT_NON.equals(masterPortPos) ? masterPort.getTargets() : targets;
	}
	
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
		masterPort = multiblock == null ? null : multiblock.getPartMap(portClass).get(masterPortPos.toLong());
		if (masterPort == null) {
			masterPortPos = DEFAULT_NON;
		}
	}
	
	@Override
	public void refreshTargets() {
		refreshTargetsFlag = false;
		if (isMultiblockAssembled()) {
			for (TARGET part : getTargets()) {
				part.onPortRefresh();
			}
		}
	}
	
	@Override
	public void setRefreshTargetsFlag(boolean refreshTargetsFlag) {
		this.refreshTargetsFlag = refreshTargetsFlag;
	}
	
	// Ticking
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (refreshTargetsFlag) {
				refreshTargets();
			}
		}
	}
	
	@Override
	public void markDirty() {
		refreshTargetsFlag = true;
		super.markDirty();
	}
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		// nbt.setLong("masterPortPos", masterPortPos.toLong());
		nbt.setString("axis", axis.getName());
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		// masterPortPos = BlockPos.fromLong(nbt.getLong("masterPortPos"));
		Axis ax = Axis.byName(nbt.getString("axis"));
		this.axis = ax == null ? Axis.Z : ax;
	}
	
	// Capability
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
		return super.hasCapability(capability, side);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		return super.getCapability(capability, side);
	}
}
