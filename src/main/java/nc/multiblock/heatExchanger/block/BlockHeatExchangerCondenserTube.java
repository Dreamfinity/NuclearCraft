package nc.multiblock.heatExchanger.block;

import nc.block.property.ISidedProperty;
import nc.block.property.PropertySidedEnum;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.multiblock.heatExchanger.HeatExchangerTubeType;
import nc.multiblock.heatExchanger.tile.TileHeatExchangerCondenserTube;
import nc.tile.internal.fluid.FluidConnection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeatExchangerCondenserTube extends BlockHeatExchangerPartBase implements ISidedProperty<HeatExchangerTubeSetting> {
	
	private static EnumFacing placementSide = null;
	
	private final HeatExchangerTubeType tubeType;

	public BlockHeatExchangerCondenserTube(HeatExchangerTubeType tubeType) {
		super();
		this.tubeType = tubeType;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch (tubeType) {
		case COPPER:
			return new TileHeatExchangerCondenserTube.Copper();
		case HARD_CARBON:
			return new TileHeatExchangerCondenserTube.HardCarbon();
		case THERMOCONDUCTING:
			return new TileHeatExchangerCondenserTube.Thermoconducting();
		default:
			return null;
		}
	}
	
	private static final PropertySidedEnum<HeatExchangerTubeSetting> DOWN = PropertySidedEnum.create("down", HeatExchangerTubeSetting.class, EnumFacing.DOWN);
	private static final PropertySidedEnum<HeatExchangerTubeSetting> UP = PropertySidedEnum.create("up", HeatExchangerTubeSetting.class, EnumFacing.UP);
	private static final PropertySidedEnum<HeatExchangerTubeSetting> NORTH = PropertySidedEnum.create("north", HeatExchangerTubeSetting.class, EnumFacing.NORTH);
	private static final PropertySidedEnum<HeatExchangerTubeSetting> SOUTH = PropertySidedEnum.create("south", HeatExchangerTubeSetting.class, EnumFacing.SOUTH);
	private static final PropertySidedEnum<HeatExchangerTubeSetting> WEST = PropertySidedEnum.create("west", HeatExchangerTubeSetting.class, EnumFacing.WEST);
	private static final PropertySidedEnum<HeatExchangerTubeSetting> EAST = PropertySidedEnum.create("east", HeatExchangerTubeSetting.class, EnumFacing.EAST);
	
	@Override
	public HeatExchangerTubeSetting getProperty(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (world.getTileEntity(pos) instanceof TileHeatExchangerCondenserTube) {
			return ((TileHeatExchangerCondenserTube) world.getTileEntity(pos)).getTubeSetting(facing);
		}
		return HeatExchangerTubeSetting.DISABLED;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, DOWN, UP, NORTH, SOUTH, WEST, EAST);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(DOWN, getProperty(world, pos, EnumFacing.DOWN)).withProperty(UP, getProperty(world, pos, EnumFacing.UP)).withProperty(NORTH, getProperty(world, pos, EnumFacing.NORTH)).withProperty(SOUTH, getProperty(world, pos, EnumFacing.SOUTH)).withProperty(WEST, getProperty(world, pos, EnumFacing.WEST)).withProperty(EAST, getProperty(world, pos, EnumFacing.EAST));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand != EnumHand.MAIN_HAND || player == null) return false;
		
		if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileHeatExchangerCondenserTube) {
			TileHeatExchangerCondenserTube tube = (TileHeatExchangerCondenserTube) world.getTileEntity(pos);
			EnumFacing side = player.isSneaking() ? facing.getOpposite() : facing;
			tube.toggleTubeSetting(side);
			if (!world.isRemote) {
				player.sendMessage(getToggleMessage(player, tube, side));
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	private static TextComponentTranslation getToggleMessage(EntityPlayer player, TileHeatExchangerCondenserTube tube, EnumFacing side) {
		HeatExchangerTubeSetting setting = tube.getTubeSetting(side);
		String message = player.isSneaking() ? "nc.block.fluid_toggle_opposite" : "nc.block.fluid_toggle";
		TextFormatting color = setting == HeatExchangerTubeSetting.PRODUCT_OUT ? TextFormatting.LIGHT_PURPLE : (setting == HeatExchangerTubeSetting.INPUT_SPREAD ? TextFormatting.GREEN : (setting == HeatExchangerTubeSetting.DEFAULT ? TextFormatting.WHITE : TextFormatting.GRAY));
		TextComponentTranslation component = new TextComponentTranslation(message);
		Style style = new Style().setColor(color);
		component.appendText(" ");
		component.appendSibling(new TextComponentTranslation("nc.block.exchanger_tube_fluid_side." + setting.getName()).setStyle(style));
		return component;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		placementSide = null;
		if (placer != null && placer.isSneaking()) placementSide = facing.getOpposite();
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placementSide ==  null) return;
		BlockPos from = pos.offset(placementSide);
		if (world.getTileEntity(pos) instanceof TileHeatExchangerCondenserTube && world.getTileEntity(from) instanceof TileHeatExchangerCondenserTube) {
			TileHeatExchangerCondenserTube tube = (TileHeatExchangerCondenserTube) world.getTileEntity(pos);
			TileHeatExchangerCondenserTube other = (TileHeatExchangerCondenserTube) world.getTileEntity(from);
			tube.setFluidConnections(FluidConnection.cloneArray(other.getFluidConnections()));
			tube.setTubeSettings(other.getTubeSettings().clone());
			tube.markDirtyAndNotify();
		}
	}
}
