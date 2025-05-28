package nc.tile;

import nc.ModCheck;
import nc.block.property.BlockProperties;
import nc.block.tile.*;
import nc.capability.radiation.source.IRadiationSource;
import nc.util.MekanismHelper;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.*;
import java.util.List;

public interface ITile {
	
	TileEntity getTile();
	
	World getTileWorld();
	
	BlockPos getTilePos();
	
	Block getTileBlockType();
	
	int getTileBlockMeta();
	
	default ItemStack getTileBlockStack() {
		return new ItemStack(getTileBlockType(), 1, getTileBlockMeta());
	}
	
	default String getTileBlockDisplayName() {
		return getTileBlockStack().getDisplayName();
	}
	
	default EnumFacing getFacingHorizontal() {
		return getTileBlockType().getStateFromMeta(getTileBlockMeta()).getValue(BlockProperties.FACING_HORIZONTAL);
	}
	
	default IBlockState getBlockState(BlockPos pos) {
		return getTileWorld().getBlockState(pos);
	}
	
	default Block getBlock(BlockPos pos) {
		return getBlockState(pos).getBlock();
	}
	
	IRadiationSource getRadiationSource();
	
	default boolean shouldSaveRadiation() {
		return true;
	}
	
	default void setActivity(boolean isActive) {
		setState(isActive, getTile());
	}
	
	@Deprecated
	default void setState(boolean isActive, TileEntity tile) {
		if (getTileBlockType() instanceof IActivatable block) {
			block.setActivity(isActive, tile);
		}
	}
	
	default <T extends Enum<T> & IStringSerializable> void setProperty(PropertyEnum<T> property, T value) {
		if (getTileBlockType() instanceof IDynamicState block) {
			block.setProperty(property, value, getTile());
		}
	}
	
	default void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos) {
		refreshIsRedstonePowered(world, pos);
	}
	
	default boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(getTilePos().getX() + 0.5D, getTilePos().getY() + 0.5D, getTilePos().getZ() + 0.5D) <= 64D;
	}
	
	// Redstone
	
	default boolean checkIsRedstonePowered(World world, BlockPos pos) {
		return world.isBlockPowered(pos) || isWeaklyPowered(world, pos);
	}
	
	default int[] weakSidesToCheck(World world, BlockPos pos) {
		return new int[] {};
	}
	
	default boolean isWeaklyPowered(World world, BlockPos pos) {
		for (int i : weakSidesToCheck(world, pos)) {
			EnumFacing side = EnumFacing.byIndex(i);
			BlockPos offPos = pos.offset(side);
			if (world.getRedstonePower(offPos, side) > 0) {
				return true;
			}
			else {
				IBlockState state = world.getBlockState(offPos);
				if (state.getBlock() == Blocks.REDSTONE_WIRE && state.getValue(BlockRedstoneWire.POWER) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	default void refreshIsRedstonePowered(World world, BlockPos pos) {
		setIsRedstonePowered(checkIsRedstonePowered(world, pos));
	}
	
	boolean getIsRedstonePowered();
	
	void setIsRedstonePowered(boolean isRedstonePowered);
	
	boolean getAlternateComparator();
	
	void setAlternateComparator(boolean alternate);
	
	boolean getRedstoneControl();
	
	void setRedstoneControl(boolean redstoneControl);
	
	// State Updating
	
	void markTileDirty();
	
	default void notifyBlockUpdate() {
		IBlockState state = getTileWorld().getBlockState(getTilePos());
		getTileWorld().notifyBlockUpdate(getTilePos(), state, state, 3);
	}
	
	default void notifyNeighborsOfStateChange() {
		World world = getTileWorld();
		BlockPos pos = getTilePos();
		
		world.notifyNeighborsOfStateChange(pos, getTileBlockType(), true);
		
		if (ModCheck.mekanismLoaded()) {
			for (EnumFacing side : EnumFacing.VALUES) {
				MekanismHelper.markTransmitterDirty(world, pos, side);
			}
		}
	}
	
	/**
	 * Call after markDirty if comparators might need to know about the changes made to the TE
	 */
	default void updateComparatorOutputLevel() {
		getTileWorld().updateComparatorOutputLevel(getTilePos(), getTileBlockType());
	}
	
	default void markDirtyAndNotify(boolean notifyNeighbors) {
		markTileDirty();
		notifyBlockUpdate();
		if (notifyNeighbors) {
			notifyNeighborsOfStateChange();
		}
	}
	
	@Deprecated
	default void markDirtyAndNotify() {
		markDirtyAndNotify(true);
	}
	
	// Capabilities
	
	/**
	 * Use when the capability provider side argument must be non-null
	 */
	default @Nonnull EnumFacing nonNullSide(@Nullable EnumFacing side) {
		return side == null ? EnumFacing.DOWN : side;
	}
	
	default <T> @Nullable T getCapabilitySafe(Capability<T> capability, TileEntity tile, EnumFacing side) {
		if (tile == null) {
			return null;
		}
		
		if (tile.hasCapability(capability, side)) {
			return tile.getCapability(capability, side);
		}
		else {
			return null;
		}
	}
	
	default <T> @Nullable T getCapabilitySafe(Capability<T> capability, BlockPos pos, EnumFacing side) {
		return getCapabilitySafe(capability, getTileWorld().getTileEntity(pos), side);
	}
	
	default <T> @Nullable T getAdjacentCapabilitySafe(Capability<T> capability, @Nonnull EnumFacing side) {
		return getCapabilitySafe(capability, getTilePos().offset(side), side.getOpposite());
	}
	
	// HWYLA
	
	default @Nonnull List<String> addToHWYLATooltip(@Nonnull List<String> tooltip) {
		return tooltip;
	}
}
