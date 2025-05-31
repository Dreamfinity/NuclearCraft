package nc.multiblock.quantum;

import nc.enumm.*;
import nc.tile.quantum.TileQuantumComputerGate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;

public class QuantumGateEnums {
	
	public enum BasicType implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileQuantumComputerGate> {
		
		X("x", 0, TileQuantumComputerGate.X.class),
		Y("y", 1, TileQuantumComputerGate.Y.class),
		Z("z", 2, TileQuantumComputerGate.Z.class),
		H("h", 3, TileQuantumComputerGate.H.class),
		S("s", 4, TileQuantumComputerGate.S.class),
		Sdg("sdg", 5, TileQuantumComputerGate.Sdg.class),
		T("t", 6, TileQuantumComputerGate.T.class),
		Tdg("tdg", 7, TileQuantumComputerGate.Tdg.class),
		P("p", 8, TileQuantumComputerGate.P.class),
		RX("rx", 9, TileQuantumComputerGate.RX.class),
		RY("ry", 10, TileQuantumComputerGate.RY.class),
		RZ("rz", 11, TileQuantumComputerGate.RZ.class);
		
		private final String name;
		private final int id;
		private final Class<? extends TileQuantumComputerGate> tileClass;
		
		BasicType(String name, int id, Class<? extends TileQuantumComputerGate> tileClass) {
			this.name = name;
			this.id = id;
			this.tileClass = tileClass;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
		
		@Override
		public int getHarvestLevel() {
			return 0;
		}
		
		@Override
		public String getHarvestTool() {
			return "pickaxe";
		}
		
		@Override
		public float getHardness() {
			return 2F;
		}
		
		@Override
		public float getResistance() {
			return 15F;
		}
		
		@Override
		public int getLightValue() {
			return 0;
		}
		
		@Override
		public Class<? extends TileQuantumComputerGate> getTileClass() {
			return tileClass;
		}
		
		public TileEntity getTile() {
			return switch (this) {
				case X -> new TileQuantumComputerGate.X();
				case Y -> new TileQuantumComputerGate.Y();
				case Z -> new TileQuantumComputerGate.Z();
				case H -> new TileQuantumComputerGate.H();
				case S -> new TileQuantumComputerGate.S();
				case Sdg -> new TileQuantumComputerGate.Sdg();
				case T -> new TileQuantumComputerGate.T();
				case Tdg -> new TileQuantumComputerGate.Tdg();
				case P -> new TileQuantumComputerGate.P();
				case RX -> new TileQuantumComputerGate.RX();
				case RY -> new TileQuantumComputerGate.RY();
				case RZ -> new TileQuantumComputerGate.RZ();
			};
		}
	}
	
	public enum ControlType implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileQuantumComputerGate> {
		
		CX("cx", 0, TileQuantumComputerGate.CX.class),
		CY("cy", 1, TileQuantumComputerGate.CY.class),
		CZ("cz", 2, TileQuantumComputerGate.CZ.class),
		CH("ch", 3, TileQuantumComputerGate.CH.class),
		CS("cs", 4, TileQuantumComputerGate.CS.class),
		CSdg("csdg", 5, TileQuantumComputerGate.CSdg.class),
		CT("ct", 6, TileQuantumComputerGate.CT.class),
		CTdg("ctdg", 7, TileQuantumComputerGate.CTdg.class),
		CP("cp", 8, TileQuantumComputerGate.CP.class),
		CRX("crx", 9, TileQuantumComputerGate.CRX.class),
		CRY("cry", 10, TileQuantumComputerGate.CRY.class),
		CRZ("crz", 11, TileQuantumComputerGate.CRZ.class);
		
		private final String name;
		private final int id;
		private final Class<? extends TileQuantumComputerGate> tileClass;
		
		ControlType(String name, int id, Class<? extends TileQuantumComputerGate> tileClass) {
			this.name = name;
			this.id = id;
			this.tileClass = tileClass;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
		
		@Override
		public int getHarvestLevel() {
			return 0;
		}
		
		@Override
		public String getHarvestTool() {
			return "pickaxe";
		}
		
		@Override
		public float getHardness() {
			return 2F;
		}
		
		@Override
		public float getResistance() {
			return 15F;
		}
		
		@Override
		public int getLightValue() {
			return 0;
		}
		
		@Override
		public Class<? extends TileQuantumComputerGate> getTileClass() {
			return tileClass;
		}
		
		public TileEntity getTile() {
			return switch (this) {
				case CX -> new TileQuantumComputerGate.CX();
				case CY -> new TileQuantumComputerGate.CY();
				case CZ -> new TileQuantumComputerGate.CZ();
				case CH -> new TileQuantumComputerGate.CH();
				case CS -> new TileQuantumComputerGate.CS();
				case CSdg -> new TileQuantumComputerGate.CSdg();
				case CT -> new TileQuantumComputerGate.CT();
				case CTdg -> new TileQuantumComputerGate.CTdg();
				case CP -> new TileQuantumComputerGate.CP();
				case CRX -> new TileQuantumComputerGate.CRX();
				case CRY -> new TileQuantumComputerGate.CRY();
				case CRZ -> new TileQuantumComputerGate.CRZ();
			};
		}
	}
	
	public enum SwapType implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileQuantumComputerGate> {
		
		SWAP("swap", 0, TileQuantumComputerGate.Swap.class),
		CSWAP("cswap", 1, TileQuantumComputerGate.ControlSwap.class);
		
		private final String name;
		private final int id;
		private final Class<? extends TileQuantumComputerGate> tileClass;
		
		SwapType(String name, int id, Class<? extends TileQuantumComputerGate> tileClass) {
			this.name = name;
			this.id = id;
			this.tileClass = tileClass;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
		
		@Override
		public int getHarvestLevel() {
			return 0;
		}
		
		@Override
		public String getHarvestTool() {
			return "pickaxe";
		}
		
		@Override
		public float getHardness() {
			return 2F;
		}
		
		@Override
		public float getResistance() {
			return 15F;
		}
		
		@Override
		public int getLightValue() {
			return 0;
		}
		
		@Override
		public Class<? extends TileQuantumComputerGate> getTileClass() {
			return tileClass;
		}
		
		public TileEntity getTile() {
			return switch (this) {
				case SWAP -> new TileQuantumComputerGate.Swap();
				case CSWAP -> new TileQuantumComputerGate.ControlSwap();
			};
		}
	}
}
