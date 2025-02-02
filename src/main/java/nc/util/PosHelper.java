package nc.util;

import com.google.common.collect.Lists;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.List;

public class PosHelper {
	
	public static final BlockPos DEFAULT_NON = new BlockPos(0, -1, 0);
	
	public static EnumFacing getAxisDirectionDir(@Nonnull EnumFacing.Axis axis, @Nonnull EnumFacing.AxisDirection dir) {
		int index = 1 - getAxisDirIndex(dir);
		
		return switch (axis) {
			case X -> AXIALS_X[index];
			case Y -> AXIALS_Y[index];
			case Z -> AXIALS_Z[index];
		};
	}
	
	// Horizontals
	
	private static final EnumFacing[] HORIZONTALS_X = new EnumFacing[] {EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH};
	private static final EnumFacing[] HORIZONTALS_Y = EnumFacing.HORIZONTALS;
	private static final EnumFacing[] HORIZONTALS_Z = new EnumFacing[] {EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST};
	
	public static EnumFacing[] getHorizontals(EnumFacing dir) {
		return switch (dir) {
			case DOWN -> HORIZONTALS_Y;
			case UP -> HORIZONTALS_Y;
			case NORTH -> HORIZONTALS_Z;
			case SOUTH -> HORIZONTALS_Z;
			case WEST -> HORIZONTALS_X;
			case EAST -> HORIZONTALS_X;
		};
	}
	
	// Axials
	
	private static final EnumFacing[] AXIALS_X = new EnumFacing[] {EnumFacing.WEST, EnumFacing.EAST};
	private static final EnumFacing[] AXIALS_Y = new EnumFacing[] {EnumFacing.DOWN, EnumFacing.UP};
	private static final EnumFacing[] AXIALS_Z = new EnumFacing[] {EnumFacing.NORTH, EnumFacing.SOUTH};
	
	public static List<EnumFacing[]> axialDirsList() {
		return Lists.newArrayList(AXIALS_X, AXIALS_Y, AXIALS_Z);
	}
	
	public static EnumFacing[] getAxialDirs(EnumFacing dir) {
		return switch (dir) {
			case DOWN -> AXIALS_Y;
			case UP -> AXIALS_Y;
			case NORTH -> AXIALS_Z;
			case SOUTH -> AXIALS_Z;
			case WEST -> AXIALS_X;
			case EAST -> AXIALS_X;
		};
	}
	
	// Vertices
	
	public static final EnumFacing[][] VERTEX_DIRS = new EnumFacing[][] {new EnumFacing[] {EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST}};
	
	// Edges
	
	public static final EnumFacing[][] EDGE_DIRS = new EnumFacing[][] {new EnumFacing[] {EnumFacing.DOWN, EnumFacing.NORTH}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.SOUTH}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.DOWN, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.NORTH}, new EnumFacing[] {EnumFacing.UP, EnumFacing.SOUTH}, new EnumFacing[] {EnumFacing.UP, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.UP, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.NORTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.NORTH, EnumFacing.WEST}, new EnumFacing[] {EnumFacing.SOUTH, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.SOUTH, EnumFacing.EAST}};
	
	// Planes
	
	public static final EnumFacing[][] PLANE_DIRS = new EnumFacing[][] {new EnumFacing[] {EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH}, new EnumFacing[] {EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST}, new EnumFacing[] {EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP}};
	
	// Axes
	
	public static final EnumFacing.Axis[] AXES = new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Y, EnumFacing.Axis.Z};
	
	public static int getAxisIndex(@Nonnull EnumFacing.Axis axis) {
		return axis == EnumFacing.Axis.X ? 0 : axis == EnumFacing.Axis.Y ? 1 : 2;
	}
	
	public static final EnumFacing.AxisDirection[] AXIS_DIRS = new EnumFacing.AxisDirection[] {EnumFacing.AxisDirection.POSITIVE, EnumFacing.AxisDirection.NEGATIVE};
	
	public static int getAxisDirIndex(@Nonnull EnumFacing.AxisDirection dir) {
		return dir == EnumFacing.AxisDirection.POSITIVE ? 0 : 1;
	}
}
