package nc.block.multiblock;

import nc.Global;
import nc.block.BlockMeta;
import nc.enumm.IBlockMetaEnum;
import nc.multiblock.Multiblock;
import nc.multiblock.internal.MultiblockValidationError;
import nc.render.BlockHighlightTracker;
import nc.tile.fluid.ITileFluid;
import nc.tile.multiblock.ITileMultiblockPart;
import nc.util.*;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public abstract class BlockMultiblockMetaPart<T extends Enum<T> & IStringSerializable & IBlockMetaEnum> extends BlockMeta<T> implements ITileEntityProvider {
	
	public BlockMultiblockMetaPart(Class<T> enumm, PropertyEnum<T> property, Material material, CreativeTabs tab) {
		super(enumm, property, material);
		hasTileEntity = true;
		setCreativeTab(tab);
		canSustainPlant = false;
		canCreatureSpawn = false;
	}
	
	protected boolean rightClickOnPart(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing) {
		return rightClickOnPart(world, pos, player, hand, facing, false);
	}
	
	protected boolean rightClickOnPart(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing facing, boolean prioritiseGui) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ITileFluid tileFluid && FluidUtil.getFluidHandler(player.getHeldItem(hand)) != null) {
			if (BlockHelper.accessTanks(player, hand, facing, tileFluid)) {
				return true;
			}
		}
		if (!world.isRemote && player.getHeldItem(hand).isEmpty()) {
			if (tile instanceof ITileMultiblockPart<?, ?> part) {
				Multiblock<?, ?> multiblock = part.getMultiblock();
				if (multiblock != null) {
					MultiblockValidationError e = multiblock.getLastError();
					if (e != null) {
						e = e.updatedError(world);
						player.sendMessage(e.getChatMessage());
						if (e.getErrorPos() != null) {
							BlockHighlightTracker.sendPacket((EntityPlayerMP) player, e.getErrorPos(), 5000);
						}
						return true;
					}
				}
				else {
					player.sendMessage(new TextComponentString(Lang.localize(Global.MOD_ID + ".multiblock_validation.no_controller")));
					return true;
				}
			}
		}
		return prioritiseGui;
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
