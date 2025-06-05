package nc.block.tile;

import nc.block.NCBlock;
import nc.tile.*;
import nc.tile.fluid.ITileFluid;
import nc.tile.processor.IProcessor;
import nc.util.BlockHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public abstract class BlockTile extends NCBlock implements ITileEntityProvider {
	
	public BlockTile(Material material) {
		super(material);
		hasTileEntity = true;
		setDefaultState(blockState.getBaseState());
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND) {
			return false;
		}
		
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ITileInstallable installable && installable.tryInstall(player, hand, facing)) {
			return true;
		}
		
		if (player.isSneaking()) {
			return false;
		}
		
		boolean isTileFluid = tile instanceof ITileFluid, isTileGui = tile instanceof ITileGui;
		if (!isTileFluid && !isTileGui) {
			return false;
		}
		if (isTileFluid && !isTileGui && FluidUtil.getFluidHandler(player.getHeldItem(hand)) == null) {
			return false;
		}
		
		if (tile instanceof ITileFluid tileFluid) {
			if (world.isRemote) {
				return true;
			}
			boolean accessedTanks = BlockHelper.accessTanks(player, hand, facing, tileFluid);
			if (accessedTanks) {
				if (tile instanceof IProcessor<?, ?, ?> processor) {
					processor.refreshRecipe();
					processor.refreshActivity();
				}
				return true;
			}
		}
		if (isTileGui) {
			if (world.isRemote) {
				onGuiOpened(world, pos);
				return true;
			}
			else {
				onGuiOpened(world, pos);
				if (tile instanceof IProcessor<?, ?, ?> processor) {
					processor.refreshRecipe();
					processor.refreshActivity();
				}
				((ITileGui<?, ?, ?>) tile).openGui(world, pos, player);
			}
		}
		else {
			return false;
		}
		
		return true;
	}
	
	public void onGuiOpened(World world, BlockPos pos) {
	
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!keepInventory) {
			TileEntity tileentity = world.getTileEntity(pos);
			
			IInventory inv = null;
			if (tileentity instanceof IInventory) {
				inv = (IInventory) tileentity;
			}
			
			if (inv != null) {
				dropItems(world, pos, inv);
				world.updateComparatorOutputLevel(pos, this);
			}
		}
		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
		super.harvestBlock(world, player, pos, state, tile, stack);
		world.setBlockToAir(pos);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity != null && tileentity.receiveClientEvent(id, param);
	}
}
