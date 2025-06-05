package nc.block.quantum;

import nc.item.ItemMultitool;
import nc.tile.quantum.TileQuantumComputerQubit;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.*;

import javax.annotation.Nullable;

public class BlockQuantumComputerQubit extends BlockQuantumComputerPart {
	
	public BlockQuantumComputerQubit() {
		super();
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileQuantumComputerQubit();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) {
			return false;
		}
		
		if (!ItemMultitool.isMultitool(player.getHeldItem(hand))) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileQuantumComputerQubit) {
				if (!world.isRemote) {
					TileQuantumComputerQubit qubit = (TileQuantumComputerQubit) tile;
					player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.qubit_id", qubit.id)));
				}
				return true;
			}
		}
		
		return rightClickOnPart(world, pos, player, hand, facing, false);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
		return side != null;
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileQuantumComputerQubit qubit) {
			return qubit.redstone ? 15 : 0;
		}
		return 0;
	}
}
