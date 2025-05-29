package nc.tile.quantum;

import it.unimi.dsi.fastutil.ints.*;
import nc.multiblock.quantum.*;
import nc.render.BlockHighlightTracker;
import nc.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import static nc.multiblock.quantum.QuantumGateWrapper.*;

public abstract class TileQuantumComputerGate extends TileQuantumComputerPart implements ITickable {
	
	protected final String gateID;
	protected String toolMode = "";
	public boolean pulsed = false;
	
	public static abstract class Single extends TileQuantumComputerGate {
		
		protected final IntSet targets;
		
		public Single(String gateID) {
			super(gateID);
			targets = new IntRBTreeSet();
			toolMode = "getTarget";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, targets);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.single_gate_info", getTileBlockDisplayName(), intsToString(targets))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getTarget") && player.isSneaking()) {
					targets.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, targets, "qubitIDSet");
					highlightQubits(player, targets);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intsToString(targets))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, targets, "nQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, targets, "nQubits");
		}
	}
	
	public static class X extends Single {
		
		public X() {
			super("x");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.X(qc, targets.toIntArray());
		}
	}
	
	public static class Y extends Single {
		
		public Y() {
			super("y");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.Y(qc, targets.toIntArray());
		}
	}
	
	public static class Z extends Single {
		
		public Z() {
			super("z");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.Z(qc, targets.toIntArray());
		}
	}
	
	public static class H extends Single {
		
		public H() {
			super("h");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.H(qc, targets.toIntArray());
		}
	}
	
	public static class S extends Single {
		
		public S() {
			super("s");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.S(qc, targets.toIntArray());
		}
	}
	
	public static class Sdg extends Single {
		
		public Sdg() {
			super("sdg");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.Sdg(qc, targets.toIntArray());
		}
	}
	
	public static class T extends Single {
		
		public T() {
			super("t");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.T(qc, targets.toIntArray());
		}
	}
	
	public static class Tdg extends Single {
		
		public Tdg() {
			super("tdg");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.Tdg(qc, targets.toIntArray());
		}
	}
	
	public static abstract class SingleAngle extends Single {
		
		protected double angle = 0;
		
		public SingleAngle(String gateID) {
			super(gateID);
			toolMode = "getAngle";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, targets);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.single_angle_gate_info", getTileBlockDisplayName(), intsToString(targets), NCMath.decimalPlaces(angle, 5))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getAngle") && player.isSneaking()) {
					angle = 0D;
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_angle", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "angle");
					toolMode = "setAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setAngle") && !player.isSneaking()) {
					angle = nbt.getDouble("qGateAngle");
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_angle", getTileBlockDisplayName(), NCMath.decimalPlaces(angle, 5))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					targets.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, targets, "qubitIDSet");
					highlightQubits(player, targets);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intsToString(targets))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			nbt.setDouble("qGateAngle", angle);
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			angle = nbt.getDouble("qGateAngle");
		}
	}
	
	public static class P extends SingleAngle {
		
		public P() {
			super("p");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.P(qc, angle, targets.toIntArray());
		}
	}
	
	public static class RX extends SingleAngle {
		
		public RX() {
			super("rx");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.RX(qc, angle, targets.toIntArray());
		}
	}
	
	public static class RY extends SingleAngle {
		
		public RY() {
			super("ry");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.RY(qc, angle, targets.toIntArray());
		}
	}
	
	public static class RZ extends SingleAngle {
		
		public RZ() {
			super("rz");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.RZ(qc, angle, targets.toIntArray());
		}
	}
	
	public static abstract class Control extends TileQuantumComputerGate {
		
		protected final IntSet controls, targets;
		
		public Control(String gateID) {
			super(gateID);
			controls = new IntRBTreeSet();
			targets = new IntRBTreeSet();
			toolMode = "getControl";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, controls);
			highlightQubits(player, targets);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_gate_info", getTileBlockDisplayName(), intsToString(targets), intsToString(controls))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getControl") && player.isSneaking()) {
					controls.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, controls, "qubitIDSet");
					highlightQubits(player, controls);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intsToString(controls))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					targets.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, targets, "qubitIDSet");
					highlightQubits(player, targets);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intsToString(targets))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, controls, "cQubits");
			NBTHelper.writeIntCollection(nbt, targets, "tQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, controls, "cQubits");
			NBTHelper.readIntCollection(nbt, targets, "tQubits");
		}
	}
	
	public static class CX extends Control {
		
		public CX() {
			super("cx");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CX(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CY extends Control {
		
		public CY() {
			super("cy");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CY(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CZ extends Control {
		
		public CZ() {
			super("cz");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CZ(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CH extends Control {
		
		public CH() {
			super("ch");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CH(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CS extends Control {
		
		public CS() {
			super("cs");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CS(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CSdg extends Control {
		
		public CSdg() {
			super("csdg");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CSdg(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CT extends Control {
		
		public CT() {
			super("ct");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CT(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CTdg extends Control {
		
		public CTdg() {
			super("ctdg");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CTdg(qc, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static abstract class ControlAngle extends Control {
		
		protected double angle = 0;
		
		public ControlAngle(String gateID) {
			super(gateID);
			toolMode = "getAngle";
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, controls);
			highlightQubits(player, targets);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_angle_gate_info", getTileBlockDisplayName(), intsToString(targets), NCMath.decimalPlaces(angle, 5), intsToString(controls))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getAngle") && player.isSneaking()) {
					angle = 0D;
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_angle", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "angle");
					toolMode = "setAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setAngle") && !player.isSneaking()) {
					angle = nbt.getDouble("qGateAngle");
					player.sendMessage(new TextComponentString(TextFormatting.GREEN + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_angle", getTileBlockDisplayName(), NCMath.decimalPlaces(angle, 5))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getControl") && player.isSneaking()) {
					controls.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, controls, "qubitIDSet");
					highlightQubits(player, controls);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intsToString(controls))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getTarget") && player.isSneaking()) {
					targets.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_target_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setTarget";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setTarget") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, targets, "qubitIDSet");
					highlightQubits(player, targets);
					player.sendMessage(new TextComponentString(TextFormatting.BLUE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_target_set", getTileBlockDisplayName(), intsToString(targets))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getAngle";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			nbt.setDouble("qGateAngle", angle);
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			angle = nbt.getDouble("qGateAngle");
		}
	}
	
	public static class CP extends ControlAngle {
		
		public CP() {
			super("cp");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CP(qc, angle, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CRX extends ControlAngle {
		
		public CRX() {
			super("crx");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CRX(qc, angle, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CRY extends ControlAngle {
		
		public CRY() {
			super("cry");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CRY(qc, angle, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class CRZ extends ControlAngle {
		
		public CRZ() {
			super("crz");
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.CRZ(qc, angle, controls.toIntArray(), targets.toIntArray());
		}
	}
	
	public static class Swap extends TileQuantumComputerGate {
		
		protected final IntList from, to;
		
		public Swap() {
			super("swap");
			from = new IntArrayList();
			to = new IntArrayList();
			toolMode = "getFirst";
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.Swap(qc, from.toIntArray(), to.toIntArray());
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, from);
			highlightQubits(player, to);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.swap_gate_info", getTileBlockDisplayName(), intsToString(from), intsToString(to))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getFirst") && player.isSneaking()) {
					from.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_first_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setFirst") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, from, "qubitIDList");
					highlightQubits(player, from);
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_first_swap_list", getTileBlockDisplayName(), intsToString(from))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getSecond") && player.isSneaking()) {
					to.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_second_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setSecond") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, to, "qubitIDList");
					highlightQubits(player, to);
					player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_second_swap_list", getTileBlockDisplayName(), intsToString(to))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, from, "iQubits");
			NBTHelper.writeIntCollection(nbt, to, "jQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, from, "iQubits");
			NBTHelper.readIntCollection(nbt, to, "jQubits");
		}
	}
	
	public static class ControlSwap extends TileQuantumComputerGate {
		
		protected final IntSet controls;
		protected final IntList from, to;
		
		public ControlSwap() {
			super("cswap");
			controls = new IntRBTreeSet();
			from = new IntArrayList();
			to = new IntArrayList();
			toolMode = "getControl";
		}
		
		@Override
		protected QuantumGateWrapper newGate(QuantumComputer qc) {
			return new QuantumGateWrapper.ControlSwap(qc, controls.toIntArray(), from.toIntArray(), to.toIntArray());
		}
		
		@Override
		public void sendGateInfo(EntityPlayerMP player) {
			highlightQubits(player, controls);
			highlightQubits(player, from);
			highlightQubits(player, to);
			player.sendMessage(new TextComponentString(Lang.localize("info.nuclearcraft.multitool.quantum_computer.control_swap_gate_info", getTileBlockDisplayName(), intsToString(from), intsToString(to), intsToString(controls))));
		}
		
		@Override
		public boolean onUseMultitool(ItemStack multitool, EntityPlayerMP player, World worldIn, EnumFacing facing, float hitX, float hitY, float hitZ) {
			NBTTagCompound nbt = NBTHelper.getStackNBT(multitool, "ncMultitool");
			if (nbt != null) {
				if (toolMode.equals("getControl") && player.isSneaking()) {
					controls.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_control_set", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "set");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setControl") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, controls, "qubitIDSet");
					highlightQubits(player, controls);
					player.sendMessage(new TextComponentString(TextFormatting.RED + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_control_set", getTileBlockDisplayName(), intsToString(controls))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getFirst") && player.isSneaking()) {
					from.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_first_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setFirst";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setFirst") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, from, "qubitIDList");
					highlightQubits(player, from);
					player.sendMessage(new TextComponentString(TextFormatting.GOLD + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_first_swap_list", getTileBlockDisplayName(), intsToString(from))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("getSecond") && player.isSneaking()) {
					to.clear();
					player.sendMessage(new TextComponentString(TextFormatting.ITALIC + Lang.localize("info.nuclearcraft.multitool.quantum_computer.start_second_swap_list", getTileBlockDisplayName())));
					nbt.setString("qComputerQubitMode", "list");
					nbt.setString("qComputerGateMode", "");
					toolMode = "setSecond";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
				else if (toolMode.equals("setSecond") && !player.isSneaking()) {
					NBTHelper.readIntCollection(nbt, to, "qubitIDList");
					highlightQubits(player, to);
					player.sendMessage(new TextComponentString(TextFormatting.LIGHT_PURPLE + Lang.localize("info.nuclearcraft.multitool.quantum_computer.finish_second_swap_list", getTileBlockDisplayName(), intsToString(to))));
					nbt.setString("qComputerQubitMode", "");
					nbt.setString("qComputerGateMode", "");
					toolMode = "getControl";
					
					clearMultitoolGateInfo(nbt);
					return true;
				}
			}
			return super.onUseMultitool(multitool, player, worldIn, facing, hitX, hitY, hitZ);
		}
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) {
			super.writeAll(nbt);
			NBTHelper.writeIntCollection(nbt, controls, "cQubits");
			NBTHelper.writeIntCollection(nbt, from, "iQubits");
			NBTHelper.writeIntCollection(nbt, to, "jQubits");
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) {
			super.readAll(nbt);
			NBTHelper.readIntCollection(nbt, controls, "cQubits");
			NBTHelper.readIntCollection(nbt, from, "iQubits");
			NBTHelper.readIntCollection(nbt, to, "jQubits");
		}
	}
	
	public TileQuantumComputerGate(String gateID) {
		super();
		this.gateID = gateID;
	}
	
	@Override
	public void onMachineAssembled(QuantumComputer multiblock) {
		doStandardNullControllerResponse(multiblock);
	}
	
	@Override
	public void onMachineBroken() {}
	
	@Override
	public int[] weakSidesToCheck(World worldIn, BlockPos posIn) {
		return new int[] {2, 3, 4, 5};
	}
	
	@Override
	public void update() {
		if (!pulsed && getIsRedstonePowered()) {
			if (isMultiblockAssembled()) {
				getMultiblock().queue.add(newGate(getMultiblock()));
			}
			pulsed = true;
		}
		else if (pulsed && !getIsRedstonePowered()) {
			pulsed = false;
		}
	}
	
	@Override
	public boolean shouldRefresh(World worldIn, BlockPos posIn, IBlockState oldState, IBlockState newState) {
		return oldState != newState;
	}
	
	protected abstract QuantumGateWrapper newGate(QuantumComputer qc);
	
	public abstract void sendGateInfo(EntityPlayerMP player);
	
	protected void highlightQubits(EntityPlayerMP player, IntCollection n) {
		QuantumComputer qc = getMultiblock();
		if (qc != null) {
			for (TileQuantumComputerQubit qubit : qc.getQubits()) {
				if (n.contains(qubit.id)) {
					BlockHighlightTracker.sendPacket(player, qubit.getPos(), 5000);
				}
			}
		}
	}
	
	public static void clearMultitoolGateInfo(NBTTagCompound nbt) {
		nbt.setDouble("qGateAngle", 0D);
		NBTHelper.writeIntCollection(nbt, new IntRBTreeSet(), "qubitIDSet");
		NBTHelper.writeIntCollection(nbt, new IntArrayList(), "qubitIDList");
	}
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		nbt.setBoolean("pulsed", pulsed);
		nbt.setString("toolMode", toolMode);
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) {
		super.readAll(nbt);
		pulsed = nbt.getBoolean("pulsed");
		toolMode = nbt.getString("toolMode");
	}
}
