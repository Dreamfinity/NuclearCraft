package nc.block.quantum;

import nc.enumm.IBlockMetaEnum;
import nc.item.ItemMultitool;
import nc.multiblock.quantum.QuantumGateEnums;
import nc.tile.quantum.TileQuantumComputerGate;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public abstract class BlockQuantumComputerGate<T extends Enum<T> & IStringSerializable & IBlockMetaEnum> extends BlockQuantumComputerMetaPart<T> {
	
	public BlockQuantumComputerGate(Class<T> enumm, PropertyEnum<T> property) {
		super(enumm, property);
	}
	
	public static class Basic extends BlockQuantumComputerGate<QuantumGateEnums.BasicType> {
		
		public final static PropertyEnum<QuantumGateEnums.BasicType> TYPE = PropertyEnum.create("type", QuantumGateEnums.BasicType.class);
		
		public Basic() {
			super(QuantumGateEnums.BasicType.class, TYPE);
		}
		
		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, TYPE);
		}
		
		@Override
		public TileEntity createNewTileEntity(World world, int meta) {
			return QuantumGateEnums.BasicType.values()[meta].getTile();
		}
	}
	
	public static class Control extends BlockQuantumComputerGate<QuantumGateEnums.ControlType> {
		
		public final static PropertyEnum<QuantumGateEnums.ControlType> TYPE = PropertyEnum.create("type", QuantumGateEnums.ControlType.class);
		
		public Control() {
			super(QuantumGateEnums.ControlType.class, TYPE);
		}
		
		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, TYPE);
		}
		
		@Override
		public TileEntity createNewTileEntity(World world, int meta) {
			return QuantumGateEnums.ControlType.values()[meta].getTile();
		}
	}
	
	public static class Swap extends BlockQuantumComputerGate<QuantumGateEnums.SwapType> {
		
		public final static PropertyEnum<QuantumGateEnums.SwapType> TYPE = PropertyEnum.create("type", QuantumGateEnums.SwapType.class);
		
		public Swap() {
			super(QuantumGateEnums.SwapType.class, TYPE);
		}
		
		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, TYPE);
		}
		
		@Override
		public TileEntity createNewTileEntity(World world, int meta) {
			return QuantumGateEnums.SwapType.values()[meta].getTile();
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) {
			return false;
		}
		
		if (!ItemMultitool.isMultitool(player.getHeldItem(hand))) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileQuantumComputerGate) {
				if (!world.isRemote) {
					TileQuantumComputerGate gate = (TileQuantumComputerGate) tile;
					gate.sendGateInfo((EntityPlayerMP) player);
				}
				return true;
			}
		}
		
		return rightClickOnPart(world, pos, player, hand, facing, false);
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null;
	}
}
