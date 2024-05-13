package nc.multiblock.turbine.tile;

import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.turbine.Turbine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileTurbineCoilConnector extends TileTurbinePartBase {
	
	public boolean isInValidPosition = false;
	
	public TileTurbineCoilConnector() {
		super(CuboidalPartPositionType.WALL);
	}
	
	@Override
	public void onMachineAssembled(Turbine controller) {
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
	}
	
	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
		if (getWorld().isRemote) return;
	}
	
	public boolean isValidPosition() {
		if (isInValidPosition) {
			return true;
		}
		
		for (EnumFacing dir : EnumFacing.VALUES) {
			if (world.getTileEntity(pos.offset(dir)) instanceof TileTurbineDynamoCoil coil && coil.isInValidPosition) {
				isInValidPosition = true;
				return true;
			}
		}
		
		return false;
	}
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		nbt.setBoolean("isInValidPosition", isInValidPosition);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		isInValidPosition = nbt.getBoolean("isInValidPosition");
	}
}
