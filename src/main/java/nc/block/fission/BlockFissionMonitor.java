package nc.block.fission;

import nc.block.tile.IActivatable;
import nc.item.ItemMultitool;
import nc.multiblock.fission.*;
import nc.render.BlockHighlightTracker;
import nc.tile.fission.*;
import nc.util.BlockHelper;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.*;

public class BlockFissionMonitor extends BlockFissionPart implements IActivatable {
	
	public BlockFissionMonitor() {
		super();
		setDefaultState(blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.NORTH).withProperty(ACTIVE, Boolean.FALSE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING_ALL, ACTIVE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta & 7);
		return getDefaultState().withProperty(FACING_ALL, enumfacing).withProperty(ACTIVE, (meta & 8) > 0);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(FACING_ALL).getIndex();
		if (state.getValue(ACTIVE)) {
			i |= 8;
		}
		return i;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileFissionMonitor();
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(ACTIVE, Boolean.FALSE);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		super.onBlockAdded(world, pos, state);
		BlockHelper.setDefaultFacing(world, pos, state, FACING_ALL);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND || player.isSneaking()) {
			return false;
		}
		
		if (!ItemMultitool.isMultitool(player.getHeldItem(hand))) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileFissionMonitor monitor) {
				if (!world.isRemote) {
					FissionReactor reactor = monitor.getMultiblock();
					if (reactor != null) {
						IFissionComponent component = reactor.getPartMap(IFissionComponent.class).get(monitor.getComponentPos().toLong());
						if (component != null) {
							FissionCluster cluster = component.getCluster();
							if (cluster != null) {
								for (long posLong : cluster.getComponentMap().keySet()) {
									BlockHighlightTracker.sendPacket((EntityPlayerMP) player, posLong, 5000);
								}
							}
						}
					}
				}
				return true;
			}
		}
		
		return rightClickOnPart(world, pos, player, hand, facing, true);
	}
}
