package nc.enumm;

import nc.tile.fission.*;
import nc.tile.fission.port.TileFissionHeaterPort;
import net.minecraft.util.IStringSerializable;

import static nc.config.NCConfig.*;

public class MetaEnums {
	
	public enum OreType implements IStringSerializable, IBlockMetaEnum {
		
		COPPER("copper", 0, ore_harvest_levels[0], "pickaxe", 3, 15, 0),
		TIN("tin", 1, ore_harvest_levels[1], "pickaxe", 3, 15, 0),
		LEAD("lead", 2, ore_harvest_levels[2], "pickaxe", 3, 15, 0),
		THORIUM("thorium", 3, ore_harvest_levels[3], "pickaxe", 3, 15, 0),
		URANIUM("uranium", 4, ore_harvest_levels[4], "pickaxe", 3, 15, 0),
		BORON("boron", 5, ore_harvest_levels[5], "pickaxe", 3, 15, 0),
		LITHIUM("lithium", 6, ore_harvest_levels[6], "pickaxe", 3, 15, 0),
		MAGNESIUM("magnesium", 7, ore_harvest_levels[7], "pickaxe", 3, 15, 0);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		OreType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
	}
	
	public enum IngotType implements IStringSerializable, IBlockMetaEnum {
		
		COPPER("copper", 0, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		TIN("tin", 1, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		LEAD("lead", 2, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		THORIUM("thorium", 3, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		URANIUM("uranium", 4, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		BORON("boron", 5, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		LITHIUM("lithium", 6, 0, "pickaxe", 4, 30, 0, 5, 5, false),
		MAGNESIUM("magnesium", 7, 0, "pickaxe", 4, 30, 0, 5, 5, false),
		GRAPHITE("graphite", 8, 0, "pickaxe", 4, 30, 0, 5, 5, true),
		BERYLLIUM("beryllium", 9, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		ZIRCONIUM("zirconium", 10, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		MANGANESE("manganese", 11, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		ALUMINUM("aluminum", 12, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		SILVER("silver", 13, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		MANGANESE_OXIDE("manganese_oxide", 14, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		MANGANESE_DIOXIDE("manganese_dioxide", 15, 0, "pickaxe", 4, 30, 0, 0, 0, false);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final int fireSpreadSpeed;
		private final int flammability;
		private final boolean isFireSource;
		
		IngotType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, int fireSpreadSpeed, int flammability, boolean isFireSource) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
			this.fireSpreadSpeed = fireSpreadSpeed;
			this.flammability = flammability;
			this.isFireSource = isFireSource;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		public int getFireSpreadSpeed() {
			return fireSpreadSpeed;
		}
		
		public int getFlammability() {
			return flammability;
		}
		
		public boolean isFireSource() {
			return isFireSource;
		}
	}
	
	public enum IngotType2 implements IStringSerializable, IBlockMetaEnum {
		
		ZIRCONIA("zirconia", 0, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		PALLADIUM("palladium", 1, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		TIN_OXIDE("tin_oxide", 2, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		NICKEL_OXIDE("nickel_oxide", 3, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		COBALT_OXIDE("cobalt_oxide", 4, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		RUTHENIUM_OXIDE("ruthenium_oxide", 5, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		IRIDIUM_OXIDE("iridium_oxide", 6, 0, "pickaxe", 4, 30, 0, 0, 0, false);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final int fireSpreadSpeed;
		private final int flammability;
		private final boolean isFireSource;
		
		IngotType2(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, int fireSpreadSpeed, int flammability, boolean isFireSource) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
			this.fireSpreadSpeed = fireSpreadSpeed;
			this.flammability = flammability;
			this.isFireSource = isFireSource;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		public int getFireSpreadSpeed() {
			return fireSpreadSpeed;
		}
		
		public int getFlammability() {
			return flammability;
		}
		
		public boolean isFireSource() {
			return isFireSource;
		}
	}
	
	public enum BlockMaterial implements IStringSerializable, IBlockMetaEnum {
		
		MOLYBDENUM("molybdenum", 0, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		COPPER_OXIDE("copper_oxide", 1, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		COBALT("cobalt", 2, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		NICKEL("nickel", 3, 0, "pickaxe", 4, 30, 0, 0, 0, false),
		PLATINUM("platinum", 4, 0, "pickaxe", 4, 30, 0, 0, 0, false);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final int fireSpreadSpeed;
		private final int flammability;
		private final boolean isFireSource;
		
		BlockMaterial(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, int fireSpreadSpeed, int flammability, boolean isFireSource) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
			this.fireSpreadSpeed = fireSpreadSpeed;
			this.flammability = flammability;
			this.isFireSource = isFireSource;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		public int getFireSpreadSpeed() {
			return fireSpreadSpeed;
		}
		
		public int getFlammability() {
			return flammability;
		}
		
		public boolean isFireSource() {
			return isFireSource;
		}
	}
	
	public enum FertileIsotopeType implements IStringSerializable, IBlockMetaEnum {
		
		URANIUM("uranium", 0, 0, "pickaxe", 3, 15, 0),
		NEPTUNIUM("neptunium", 1, 0, "pickaxe", 3, 15, 0),
		PLUTONIUM("plutonium", 2, 0, "pickaxe", 3, 15, 0),
		AMERICIUM("americium", 3, 0, "pickaxe", 3, 15, 0),
		CURIUM("curium", 4, 0, "pickaxe", 3, 15, 0),
		BERKELIUM("berkelium", 5, 0, "pickaxe", 3, 15, 0),
		CALIFORNIUM("californium", 6, 0, "pickaxe", 3, 15, 0);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		FertileIsotopeType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
	}
	
	public enum MachineDiaphragmType implements IStringSerializable, IBlockMetaEnum {
		
		SINTERED_STEEL("sintered_steel", 0, 0, "pickaxe", 3, 15, 0),
		POLYETHERSULFONE("polyethersulfone", 1, 0, "pickaxe", 3, 15, 0),
		ZIRFON("zirfon", 2, 0, "pickaxe", 3, 15, 0);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		MachineDiaphragmType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
	}
	
	public enum MachineSieveTrayType implements IStringSerializable, IBlockMetaEnum {
		
		STEEL("steel", 0, 0, "pickaxe", 3, 15, 0),
		POLYTETRAFLUOROETHENE("polytetrafluoroethene", 1, 0, "pickaxe", 3, 15, 0),
		HASTELLOY("hastelloy", 2, 0, "pickaxe", 3, 15, 0);
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		MachineSieveTrayType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
			this.name = name;
			this.id = id;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
	}
	
	public enum NeutronReflectorType implements IStringSerializable, IBlockMetaEnum {
		
		BERYLLIUM_CARBON("beryllium_carbon", 0, fission_reflector_efficiency[0], fission_reflector_reflectivity[0], 0, "pickaxe", 2, 15, 0),
		LEAD_STEEL("lead_steel", 1, fission_reflector_efficiency[1], fission_reflector_reflectivity[1], 0, "pickaxe", 2, 15, 0);
		
		private final String name;
		private final int id;
		private final double efficiency;
		private final double reflectivity;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		NeutronReflectorType(String name, int id, double efficiency, double reflectivity, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue) {
			this.name = name;
			this.id = id;
			this.efficiency = efficiency;
			this.reflectivity = reflectivity;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
		
		public double getEfficiency() {
			return efficiency;
		}
		
		public double getReflectivity() {
			return reflectivity;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
	}
	
	public enum NeutronSourceType implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileFissionSource.Meta> {
		
		RADIUM_BERYLLIUM("radium_beryllium", 0, fission_source_efficiency[0], 0, "pickaxe", 2, 15, 0, TileFissionSource.RadiumBeryllium.class),
		POLONIUM_BERYLLIUM("polonium_beryllium", 1, fission_source_efficiency[1], 0, "pickaxe", 2, 15, 0, TileFissionSource.PoloniumBeryllium.class),
		CALIFORNIUM("californium", 2, fission_source_efficiency[2], 0, "pickaxe", 2, 15, 0, TileFissionSource.Californium.class);
		
		private final String name;
		private final int id;
		private final double efficiency;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileFissionSource.Meta> tileClass;
		
		NeutronSourceType(String name, int id, double efficiency, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileFissionSource.Meta> tileClass) {
			this.name = name;
			this.id = id;
			this.efficiency = efficiency;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
		
		public double getEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileFissionSource.Meta> getTileClass() {
			return tileClass;
		}
	}
	
	public enum NeutronShieldType implements IStringSerializable, IBlockMetaEnum, ITileEnum<TileFissionShield.Meta> {
		
		BORON_SILVER("boron_silver", 0, fission_shield_heat_per_flux[0], fission_shield_efficiency[0], 0, "pickaxe", 2, 15, 0, TileFissionShield.BoronSilver.class);
		
		private final String name;
		private final int id;
		private final double heatPerFlux;
		private final double efficiency;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileFissionShield.Meta> tileClass;
		
		NeutronShieldType(String name, int id, double heatPerFlux, double efficiency, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileFissionShield.Meta> tileClass) {
			this.name = name;
			this.id = id;
			this.heatPerFlux = heatPerFlux;
			this.efficiency = efficiency;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
		
		public double getHeatPerFlux() {
			return heatPerFlux;
		}
		
		public double getEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileFissionShield.Meta> getTileClass() {
			return tileClass;
		}
	}
	
	public enum HeatSinkType implements IStringSerializable, IBlockMetaEnum, ICoolingComponentEnum<TileSolidFissionSink.Meta> {
		
		WATER("water", 0, fission_sink_cooling_rate[0], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Water.class),
		IRON("iron", 1, fission_sink_cooling_rate[1], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Iron.class),
		REDSTONE("redstone", 2, fission_sink_cooling_rate[2], 0, "pickaxe", 2, 15, 7, TileSolidFissionSink.Redstone.class),
		QUARTZ("quartz", 3, fission_sink_cooling_rate[3], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Quartz.class),
		OBSIDIAN("obsidian", 4, fission_sink_cooling_rate[4], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Obsidian.class),
		NETHER_BRICK("nether_brick", 5, fission_sink_cooling_rate[5], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.NetherBrick.class),
		GLOWSTONE("glowstone", 6, fission_sink_cooling_rate[6], 0, "pickaxe", 2, 15, 15, TileSolidFissionSink.Glowstone.class),
		LAPIS("lapis", 7, fission_sink_cooling_rate[7], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Lapis.class),
		GOLD("gold", 8, fission_sink_cooling_rate[8], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Gold.class),
		PRISMARINE("prismarine", 9, fission_sink_cooling_rate[9], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Prismarine.class),
		SLIME("slime", 10, fission_sink_cooling_rate[10], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Slime.class),
		END_STONE("end_stone", 11, fission_sink_cooling_rate[11], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.EndStone.class),
		PURPUR("purpur", 12, fission_sink_cooling_rate[12], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Purpur.class),
		DIAMOND("diamond", 13, fission_sink_cooling_rate[13], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Diamond.class),
		EMERALD("emerald", 14, fission_sink_cooling_rate[14], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Emerald.class),
		COPPER("copper", 15, fission_sink_cooling_rate[15], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Copper.class);
		
		private final String name;
		private final int id;
		private final int coolingRate;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileSolidFissionSink.Meta> tileClass;
		
		HeatSinkType(String name, int id, int coolingRate, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileSolidFissionSink.Meta> tileClass) {
			this.name = name;
			this.id = id;
			this.coolingRate = coolingRate;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
		public int getCooling() {
			return coolingRate;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileSolidFissionSink.Meta> getTileClass() {
			return tileClass;
		}
	}
	
	public enum HeatSinkType2 implements IStringSerializable, IBlockMetaEnum, ICoolingComponentEnum<TileSolidFissionSink.Meta> {
		
		TIN("tin", 0, fission_sink_cooling_rate[16], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Tin.class),
		LEAD("lead", 1, fission_sink_cooling_rate[17], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Lead.class),
		BORON("boron", 2, fission_sink_cooling_rate[18], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Boron.class),
		LITHIUM("lithium", 3, fission_sink_cooling_rate[19], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Lithium.class),
		MAGNESIUM("magnesium", 4, fission_sink_cooling_rate[20], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Magnesium.class),
		MANGANESE("manganese", 5, fission_sink_cooling_rate[21], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Manganese.class),
		ALUMINUM("aluminum", 6, fission_sink_cooling_rate[22], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Aluminum.class),
		SILVER("silver", 7, fission_sink_cooling_rate[23], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Silver.class),
		FLUORITE("fluorite", 8, fission_sink_cooling_rate[24], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Fluorite.class),
		VILLIAUMITE("villiaumite", 9, fission_sink_cooling_rate[25], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Villiaumite.class),
		CAROBBIITE("carobbiite", 10, fission_sink_cooling_rate[26], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Carobbiite.class),
		ARSENIC("arsenic", 11, fission_sink_cooling_rate[27], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Arsenic.class),
		LIQUID_NITROGEN("liquid_nitrogen", 12, fission_sink_cooling_rate[28], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.LiquidNitrogen.class),
		LIQUID_HELIUM("liquid_helium", 13, fission_sink_cooling_rate[29], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.LiquidHelium.class),
		ENDERIUM("enderium", 14, fission_sink_cooling_rate[30], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Enderium.class),
		CRYOTHEUM("cryotheum", 15, fission_sink_cooling_rate[31], 0, "pickaxe", 2, 15, 0, TileSolidFissionSink.Cryotheum.class);
		
		private final String name;
		private final int id;
		private final int coolingRate;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileSolidFissionSink.Meta> tileClass;
		
		HeatSinkType2(String name, int id, int coolingRate, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileSolidFissionSink.Meta> tileClass) {
			this.name = name;
			this.id = id;
			this.coolingRate = coolingRate;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
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
		public int getCooling() {
			return coolingRate;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileSolidFissionSink.Meta> getTileClass() {
			return tileClass;
		}
	}
	
	public enum CoolantHeaterType implements IStringSerializable, IBlockMetaEnum, ICoolingComponentEnum<TileSaltFissionHeater.Meta> {
		
		STANDARD("standard", 0, fission_heater_cooling_rate[0], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Standard.class, TileFissionHeaterPort.Standard.class),
		IRON("iron", 1, fission_heater_cooling_rate[1], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Iron.class, TileFissionHeaterPort.Iron.class),
		REDSTONE("redstone", 2, fission_heater_cooling_rate[2], 0, "pickaxe", 2, 15, 7, TileSaltFissionHeater.Redstone.class, TileFissionHeaterPort.Redstone.class),
		QUARTZ("quartz", 3, fission_heater_cooling_rate[3], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Quartz.class, TileFissionHeaterPort.Quartz.class),
		OBSIDIAN("obsidian", 4, fission_heater_cooling_rate[4], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Obsidian.class, TileFissionHeaterPort.Obsidian.class),
		NETHER_BRICK("nether_brick", 5, fission_heater_cooling_rate[5], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.NetherBrick.class, TileFissionHeaterPort.NetherBrick.class),
		GLOWSTONE("glowstone", 6, fission_heater_cooling_rate[6], 0, "pickaxe", 2, 15, 15, TileSaltFissionHeater.Glowstone.class, TileFissionHeaterPort.Glowstone.class),
		LAPIS("lapis", 7, fission_heater_cooling_rate[7], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Lapis.class, TileFissionHeaterPort.Lapis.class),
		GOLD("gold", 8, fission_heater_cooling_rate[8], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Gold.class, TileFissionHeaterPort.Gold.class),
		PRISMARINE("prismarine", 9, fission_heater_cooling_rate[9], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Prismarine.class, TileFissionHeaterPort.Prismarine.class),
		SLIME("slime", 10, fission_heater_cooling_rate[10], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Slime.class, TileFissionHeaterPort.Slime.class),
		END_STONE("end_stone", 11, fission_heater_cooling_rate[11], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.EndStone.class, TileFissionHeaterPort.EndStone.class),
		PURPUR("purpur", 12, fission_heater_cooling_rate[12], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Purpur.class, TileFissionHeaterPort.Purpur.class),
		DIAMOND("diamond", 13, fission_heater_cooling_rate[13], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Diamond.class, TileFissionHeaterPort.Diamond.class),
		EMERALD("emerald", 14, fission_heater_cooling_rate[14], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Emerald.class, TileFissionHeaterPort.Emerald.class),
		COPPER("copper", 15, fission_heater_cooling_rate[15], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Copper.class, TileFissionHeaterPort.Copper.class);
		
		private final String name;
		private final int id;
		private final int coolingRate;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileSaltFissionHeater.Meta> tileClass;
		private final Class<? extends TileFissionHeaterPort.Meta> portClass;
		
		CoolantHeaterType(String name, int id, int coolingRate, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileSaltFissionHeater.Meta> tileClass, Class<? extends TileFissionHeaterPort.Meta> portClass) {
			this.name = name;
			this.id = id;
			this.coolingRate = coolingRate;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
			this.tileClass = tileClass;
			this.portClass = portClass;
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
		public int getCooling() {
			return coolingRate;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileSaltFissionHeater.Meta> getTileClass() {
			return tileClass;
		}
		
		public Class<? extends TileFissionHeaterPort.Meta> getPortClass() {
			return portClass;
		}
	}
	
	public enum CoolantHeaterType2 implements IStringSerializable, IBlockMetaEnum, ICoolingComponentEnum<TileSaltFissionHeater.Meta> {
		
		TIN("tin", 0, fission_heater_cooling_rate[16], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Tin.class, TileFissionHeaterPort.Tin.class),
		LEAD("lead", 1, fission_heater_cooling_rate[17], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Lead.class, TileFissionHeaterPort.Lead.class),
		BORON("boron", 2, fission_heater_cooling_rate[18], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Boron.class, TileFissionHeaterPort.Boron.class),
		LITHIUM("lithium", 3, fission_heater_cooling_rate[19], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Lithium.class, TileFissionHeaterPort.Lithium.class),
		MAGNESIUM("magnesium", 4, fission_heater_cooling_rate[20], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Magnesium.class, TileFissionHeaterPort.Magnesium.class),
		MANGANESE("manganese", 5, fission_heater_cooling_rate[21], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Manganese.class, TileFissionHeaterPort.Manganese.class),
		ALUMINUM("aluminum", 6, fission_heater_cooling_rate[22], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Aluminum.class, TileFissionHeaterPort.Aluminum.class),
		SILVER("silver", 7, fission_heater_cooling_rate[23], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Silver.class, TileFissionHeaterPort.Silver.class),
		FLUORITE("fluorite", 8, fission_heater_cooling_rate[24], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Fluorite.class, TileFissionHeaterPort.Fluorite.class),
		VILLIAUMITE("villiaumite", 9, fission_heater_cooling_rate[25], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Villiaumite.class, TileFissionHeaterPort.Villiaumite.class),
		CAROBBIITE("carobbiite", 10, fission_heater_cooling_rate[26], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Carobbiite.class, TileFissionHeaterPort.Carobbiite.class),
		ARSENIC("arsenic", 11, fission_heater_cooling_rate[27], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Arsenic.class, TileFissionHeaterPort.Arsenic.class),
		LIQUID_NITROGEN("liquid_nitrogen", 12, fission_heater_cooling_rate[28], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.LiquidNitrogen.class, TileFissionHeaterPort.LiquidNitrogen.class),
		LIQUID_HELIUM("liquid_helium", 13, fission_heater_cooling_rate[29], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.LiquidHelium.class, TileFissionHeaterPort.LiquidHelium.class),
		ENDERIUM("enderium", 14, fission_heater_cooling_rate[30], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Enderium.class, TileFissionHeaterPort.Enderium.class),
		CRYOTHEUM("cryotheum", 15, fission_heater_cooling_rate[31], 0, "pickaxe", 2, 15, 0, TileSaltFissionHeater.Cryotheum.class, TileFissionHeaterPort.Cryotheum.class);
		
		private final String name;
		private final int id;
		private final int coolingRate;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		private final Class<? extends TileSaltFissionHeater.Meta> tileClass;
		private final Class<? extends TileFissionHeaterPort.Meta> portClass;
		
		CoolantHeaterType2(String name, int id, int coolingRate, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue, Class<? extends TileSaltFissionHeater.Meta> tileClass, Class<? extends TileFissionHeaterPort.Meta> portClass) {
			this.name = name;
			this.id = id;
			this.coolingRate = coolingRate;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
			this.tileClass = tileClass;
			this.portClass = portClass;
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
		public int getCooling() {
			return coolingRate;
		}
		
		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}
		
		@Override
		public String getHarvestTool() {
			return harvestTool;
		}
		
		@Override
		public float getHardness() {
			return hardness;
		}
		
		@Override
		public float getResistance() {
			return resistance;
		}
		
		@Override
		public int getLightValue() {
			return lightValue;
		}
		
		@Override
		public Class<? extends TileSaltFissionHeater.Meta> getTileClass() {
			return tileClass;
		}
		
		public Class<? extends TileFissionHeaterPort.Meta> getPortClass() {
			return portClass;
		}
	}
	
	public enum GemType implements IStringSerializable, IMetaEnum {
		
		RHODOCHROSITE("rhodochrosite", 0),
		BORON_NITRIDE("boron_nitride", 1),
		FLUORITE("fluorite", 2),
		VILLIAUMITE("villiaumite", 3),
		CAROBBIITE("carobbiite", 4),
		BORON_ARSENIDE("boron_arsenide", 5),
		SILICON("silicon", 6);
		
		private final String name;
		private final int id;
		
		GemType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum GemDustType implements IStringSerializable, IMetaEnum {
		
		DIAMOND("diamond", 0),
		RHODOCHROSITE("rhodochrosite", 1),
		QUARTZ("quartz", 2),
		OBSIDIAN("obsidian", 3),
		BORON_NITRIDE("boron_nitride", 4),
		FLUORITE("fluorite", 5),
		SULFUR("sulfur", 6),
		COAL("coal", 7),
		VILLIAUMITE("villiaumite", 8),
		CAROBBIITE("carobbiite", 9),
		ARSENIC("arsenic", 10),
		END_STONE("end_stone", 11);
		
		private final String name;
		private final int id;
		
		GemDustType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum AlloyType implements IStringSerializable, IMetaEnum {
		
		BRONZE("bronze", 0),
		TOUGH("tough", 1),
		HARD_CARBON("hard_carbon", 2),
		MAGNESIUM_DIBORIDE("magnesium_diboride", 3),
		LITHIUM_MANGANESE_DIOXIDE("lithium_manganese_dioxide", 4),
		STEEL("steel", 5),
		FERROBORON("ferroboron", 6),
		SHIBUICHI("shibuichi", 7),
		TIN_SILVER("tin_silver", 8),
		LEAD_PLATINUM("lead_platinum", 9),
		EXTREME("extreme", 10),
		THERMOCONDUCTING("thermoconducting", 11),
		ZIRCALOY("zircaloy", 12),
		SILICON_CARBIDE("silicon_carbide", 13),
		SIC_SIC_CMC("sic_sic_cmc", 14),
		HSLA_STEEL("hsla_steel", 15),
		ZIRCONIUM_MOLYBDENUM("zirconium_molybdenum", 16),
		HASTELLOY("hastelloy", 17);
		
		private final String name;
		private final int id;
		
		AlloyType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CompoundType implements IStringSerializable, IMetaEnum {
		
		CALCIUM_SULFATE("calcium_sulfate", 0),
		CRYSTAL_BINDER("crystal_binder", 1),
		ENERGETIC_BLEND("energetic_blend", 2),
		SODIUM_FLUORIDE("sodium_fluoride", 3),
		POTASSIUM_FLUORIDE("potassium_fluoride", 4),
		SODIUM_HYDROXIDE("sodium_hydroxide", 5),
		POTASSIUM_HYDROXIDE("potassium_hydroxide", 6),
		BORAX("borax", 7),
		IRRADIATED_BORAX("irradiated_borax", 8),
		DIMENSIONAL_BLEND("dimensional_blend", 9),
		C_MN_BLEND("c_mn_blend", 10),
		ALUGENTUM("alugentum", 11),
		AMMONIUM_SULFATE("ammonium_sulfate", 12),
		AMMONIUM_BISULFATE("ammonium_bisulfate", 13),
		AMMONIUM_PERSULFATE("ammonium_persulfate", 14),
		HYDROQUINONE("hydroquinone", 15),
		SODIUM_HYDROQUINONE("sodium_hydroquinone", 16),
		POTASSIUM_HYDROQUINONE("potassium_hydroquinone", 17);
		
		private final String name;
		private final int id;
		
		CompoundType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum PartType implements IStringSerializable, IMetaEnum {
		
		PLATE_BASIC("plate_basic", 0),
		PLATE_ADVANCED("plate_advanced", 1),
		PLATE_DU("plate_du", 2),
		PLATE_ELITE("plate_elite", 3),
		WIRE_COPPER("wire_copper", 4),
		WIRE_MAGNESIUM_DIBORIDE("wire_magnesium_diboride", 5),
		BIOPLASTIC("bioplastic", 6),
		SERVO("servo", 7),
		MOTOR("motor", 8),
		ACTUATOR("actuator", 9),
		CHASSIS("chassis", 10),
		EMPTY_FRAME("empty_frame", 11),
		STEEL_FRAME("steel_frame", 12),
		SIC_FIBER("sic_fiber", 13),
		EMPTY_HEAT_SINK("empty_sink", 14),
		PYROLYTIC_CARBON("pyrolytic_carbon", 15),
		SINTERED_STEEL("sintered_steel", 16),
		SINTERED_ZIRCONIA("sintered_zirconia", 17),
		POLYETHERSULFONE("polyethersulfone", 18),
		ZIRFON("zirfon", 19),
		POLYTETRAFLUOROETHENE("polytetrafluoroethene", 20);
		
		private final String name;
		private final int id;
		
		PartType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum UpgradeType implements IStringSerializable, IMetaEnum {
		
		SPEED("speed", 0),
		ENERGY("energy", 1);
		
		private final String name;
		private final int id;
		private final int maxStackSize;
		
		UpgradeType(String name, int id) {
			this.name = name;
			this.id = id;
			this.maxStackSize = upgrade_stack_sizes[id];
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
		
		public int getMaxStackSize() {
			return maxStackSize;
		}
	}
	
	public enum FissionDustType implements IStringSerializable, IMetaEnum {
		
		BISMUTH("bismuth", 0),
		RADIUM("radium", 1),
		POLONIUM("polonium", 2),
		TBP("tbp", 3),
		PROTACTINIUM_233("protactinium_233", 4),
		STRONTIUM_90("strontium_90", 5),
		MOLYBDENUM("molybdenum", 6),
		RUTHENIUM_106("ruthenium_106", 7),
		CAESIUM_137("caesium_137", 8),
		PROMETHIUM_147("promethium_147", 9),
		EUROPIUM_155("europium_155", 10);
		
		private final String name;
		private final int id;
		
		FissionDustType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum UraniumType implements IStringSerializable, IMetaEnum {
		
		_233("233", 0),
		_233_C("233_c", 1),
		_233_OX("233_ox", 2),
		_233_NI("233_ni", 3),
		_233_ZA("233_za", 4),
		_235("235", 5),
		_235_C("235_c", 6),
		_235_OX("235_ox", 7),
		_235_NI("235_ni", 8),
		_235_ZA("235_za", 9),
		_238("238", 10),
		_238_C("238_c", 11),
		_238_OX("238_ox", 12),
		_238_NI("238_ni", 13),
		_238_ZA("238_za", 14);
		
		private final String name;
		private final int id;
		
		UraniumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum NeptuniumType implements IStringSerializable, IMetaEnum {
		
		_236("236", 0),
		_236_C("236_c", 1),
		_236_OX("236_ox", 2),
		_236_NI("236_ni", 3),
		_236_ZA("236_za", 4),
		_237("237", 5),
		_237_C("237_c", 6),
		_237_OX("237_ox", 7),
		_237_NI("237_ni", 8),
		_237_ZA("237_za", 9);
		
		private final String name;
		private final int id;
		
		NeptuniumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum PlutoniumType implements IStringSerializable, IMetaEnum {
		
		_238("238", 0),
		_238_C("238_c", 1),
		_238_OX("238_ox", 2),
		_238_NI("238_ni", 3),
		_238_ZA("238_za", 4),
		_239("239", 5),
		_239_C("239_c", 6),
		_239_OX("239_ox", 7),
		_239_NI("239_ni", 8),
		_239_ZA("239_za", 9),
		_241("241", 10),
		_241_C("241_c", 11),
		_241_OX("241_ox", 12),
		_241_NI("241_ni", 13),
		_241_ZA("241_za", 14),
		_242("242", 15),
		_242_C("242_c", 16),
		_242_OX("242_ox", 17),
		_242_NI("242_ni", 18),
		_242_ZA("242_za", 19);
		
		private final String name;
		private final int id;
		
		PlutoniumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum AmericiumType implements IStringSerializable, IMetaEnum {
		
		_241("241", 0),
		_241_C("241_c", 1),
		_241_OX("241_ox", 2),
		_241_NI("241_ni", 3),
		_241_ZA("241_za", 4),
		_242("242", 5),
		_242_C("242_c", 6),
		_242_OX("242_ox", 7),
		_242_NI("242_ni", 8),
		_242_ZA("242_za", 9),
		_243("243", 10),
		_243_C("243_c", 11),
		_243_OX("243_ox", 12),
		_243_NI("243_ni", 13),
		_243_ZA("243_za", 14);
		
		private final String name;
		private final int id;
		
		AmericiumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CuriumType implements IStringSerializable, IMetaEnum {
		
		_243("243", 0),
		_243_C("243_c", 1),
		_243_OX("243_ox", 2),
		_243_NI("243_ni", 3),
		_243_ZA("243_za", 4),
		_245("245", 5),
		_245_C("245_c", 6),
		_245_OX("245_ox", 7),
		_245_NI("245_ni", 8),
		_245_ZA("245_za", 9),
		_246("246", 10),
		_246_C("246_c", 11),
		_246_OX("246_ox", 12),
		_246_NI("246_ni", 13),
		_246_ZA("246_za", 14),
		_247("247", 15),
		_247_C("247_c", 16),
		_247_OX("247_ox", 17),
		_247_NI("247_ni", 18),
		_247_ZA("247_za", 19);
		
		private final String name;
		private final int id;
		
		CuriumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum BerkeliumType implements IStringSerializable, IMetaEnum {
		
		_247("247", 0),
		_247_C("247_c", 1),
		_247_OX("247_ox", 2),
		_247_NI("247_ni", 3),
		_247_ZA("247_za", 4),
		_248("248", 5),
		_248_C("248_c", 6),
		_248_OX("248_ox", 7),
		_248_NI("248_ni", 8),
		_248_ZA("248_za", 9);
		
		private final String name;
		private final int id;
		
		BerkeliumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CaliforniumType implements IStringSerializable, IMetaEnum {
		
		_249("249", 0),
		_249_C("249_c", 1),
		_249_OX("249_ox", 2),
		_249_NI("249_ni", 3),
		_249_ZA("249_za", 4),
		_250("250", 5),
		_250_C("250_c", 6),
		_250_OX("250_ox", 7),
		_250_NI("250_ni", 8),
		_250_ZA("250_za", 9),
		_251("251", 10),
		_251_C("251_c", 11),
		_251_OX("251_ox", 12),
		_251_NI("251_ni", 13),
		_251_ZA("251_za", 14),
		_252("252", 15),
		_252_C("252_c", 16),
		_252_OX("252_ox", 17),
		_252_NI("252_ni", 18),
		_252_ZA("252_za", 19);
		
		private final String name;
		private final int id;
		
		CaliforniumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum ThoriumPelletType implements IStringSerializable, IMetaEnum {
		
		TBU("tbu", 0),
		TBU_C("tbu_c", 1);
		
		private final String name;
		private final int id;
		
		ThoriumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum UraniumPelletType implements IStringSerializable, IMetaEnum {
		
		LEU_233("leu_233", 0),
		LEU_233_C("leu_233_c", 1),
		HEU_233("heu_233", 2),
		HEU_233_C("heu_233_c", 3),
		LEU_235("leu_235", 4),
		LEU_235_C("leu_235_c", 5),
		HEU_235("heu_235", 6),
		HEU_235_C("heu_235_c", 7);
		
		private final String name;
		private final int id;
		
		UraniumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum NeptuniumPelletType implements IStringSerializable, IMetaEnum {
		
		LEN_236("len_236", 0),
		LEN_236_C("len_236_c", 1),
		HEN_236("hen_236", 2),
		HEN_236_C("hen_236_c", 3);
		
		private final String name;
		private final int id;
		
		NeptuniumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum PlutoniumPelletType implements IStringSerializable, IMetaEnum {
		
		LEP_239("lep_239", 0),
		LEP_239_C("lep_239_c", 1),
		HEP_239("hep_239", 2),
		HEP_239_C("hep_239_c", 3),
		LEP_241("lep_241", 4),
		LEP_241_C("lep_241_c", 5),
		HEP_241("hep_241", 6),
		HEP_241_C("hep_241_c", 7);
		
		private final String name;
		private final int id;
		
		PlutoniumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum MixedPelletType implements IStringSerializable, IMetaEnum {
		
		MIX_239("mix_239", 0),
		MIX_239_C("mix_239_c", 1),
		MIX_241("mix_241", 2),
		MIX_241_C("mix_241_c", 3);
		
		private final String name;
		private final int id;
		
		MixedPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum AmericiumPelletType implements IStringSerializable, IMetaEnum {
		
		LEA_242("lea_242", 0),
		LEA_242_C("lea_242_c", 1),
		HEA_242("hea_242", 2),
		HEA_242_C("hea_242_c", 3);
		
		private final String name;
		private final int id;
		
		AmericiumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CuriumPelletType implements IStringSerializable, IMetaEnum {
		
		LECm_243("lecm_243", 0),
		LECm_243_C("lecm_243_c", 1),
		HECm_243("hecm_243", 2),
		HECm_243_C("hecm_243_c", 3),
		LECm_245("lecm_245", 4),
		LECm_245_C("lecm_245_c", 5),
		HECm_245("hecm_245", 6),
		HECm_245_C("hecm_245_c", 7),
		LECm_247("lecm_247", 8),
		LECm_247_C("lecm_247_c", 9),
		HECm_247("hecm_247", 10),
		HECm_247_C("hecm_247_c", 11);
		
		private final String name;
		private final int id;
		
		CuriumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum BerkeliumPelletType implements IStringSerializable, IMetaEnum {
		
		LEB_248("leb_248", 0),
		LEB_248_C("leb_248_c", 1),
		HEB_248("heb_248", 2),
		HEB_248_C("heb_248_c", 3);
		
		private final String name;
		private final int id;
		
		BerkeliumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CaliforniumPelletType implements IStringSerializable, IMetaEnum {
		
		LECf_249("lecf_249", 0),
		LECf_249_C("lecf_249_c", 1),
		HECf_249("hecf_249", 2),
		HECf_249_C("hecf_249_c", 3),
		LECf_251("lecf_251", 4),
		LECf_251_C("lecf_251_c", 5),
		HECf_251("hecf_251", 6),
		HECf_251_C("hecf_251_c", 7);
		
		private final String name;
		private final int id;
		
		CaliforniumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum ThoriumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		TBU_TR("tbu_tr", 0),
		TBU_OX("tbu_ox", 1),
		TBU_NI("tbu_ni", 2),
		TBU_ZA("tbu_za", 3);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		ThoriumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_thorium_fuel_time[id + id / 4];
			heatGen = fission_thorium_heat_generation[id + id / 4];
			efficiency = fission_thorium_efficiency[id + id / 4];
			criticality = fission_thorium_criticality[id + id / 4];
			selfPriming = fission_thorium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum UraniumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LEU_233_TR("leu_233_tr", 0),
		LEU_233_OX("leu_233_ox", 1),
		LEU_233_NI("leu_233_ni", 2),
		LEU_233_ZA("leu_233_za", 3),
		HEU_233_TR("heu_233_tr", 4),
		HEU_233_OX("heu_233_ox", 5),
		HEU_233_NI("heu_233_ni", 6),
		HEU_233_ZA("heu_233_za", 7),
		LEU_235_TR("leu_235_tr", 8),
		LEU_235_OX("leu_235_ox", 9),
		LEU_235_NI("leu_235_ni", 10),
		LEU_235_ZA("leu_235_za", 11),
		HEU_235_TR("heu_235_tr", 12),
		HEU_235_OX("heu_235_ox", 13),
		HEU_235_NI("heu_235_ni", 14),
		HEU_235_ZA("heu_235_za", 15);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		UraniumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_uranium_fuel_time[id + id / 4];
			heatGen = fission_uranium_heat_generation[id + id / 4];
			efficiency = fission_uranium_efficiency[id + id / 4];
			criticality = fission_uranium_criticality[id + id / 4];
			selfPriming = fission_uranium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum NeptuniumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LEN_236_TR("len_236_tr", 0),
		LEN_236_OX("len_236_ox", 1),
		LEN_236_NI("len_236_ni", 2),
		LEN_236_ZA("len_236_za", 3),
		HEN_236_TR("hen_236_tr", 4),
		HEN_236_OX("hen_236_ox", 5),
		HEN_236_NI("hen_236_ni", 6),
		HEN_236_ZA("hen_236_za", 7);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		NeptuniumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_neptunium_fuel_time[id + id / 4];
			heatGen = fission_neptunium_heat_generation[id + id / 4];
			efficiency = fission_neptunium_efficiency[id + id / 4];
			criticality = fission_neptunium_criticality[id + id / 4];
			selfPriming = fission_neptunium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum PlutoniumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LEP_239_TR("lep_239_tr", 0),
		LEP_239_OX("lep_239_ox", 1),
		LEP_239_NI("lep_239_ni", 2),
		LEP_239_ZA("lep_239_za", 3),
		HEP_239_TR("hep_239_tr", 4),
		HEP_239_OX("hep_239_ox", 5),
		HEP_239_NI("hep_239_ni", 6),
		HEP_239_ZA("hep_239_za", 7),
		LEP_241_TR("lep_241_tr", 8),
		LEP_241_OX("lep_241_ox", 9),
		LEP_241_NI("lep_241_ni", 10),
		LEP_241_ZA("lep_241_za", 11),
		HEP_241_TR("hep_241_tr", 12),
		HEP_241_OX("hep_241_ox", 13),
		HEP_241_NI("hep_241_ni", 14),
		HEP_241_ZA("hep_241_za", 15);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		PlutoniumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_plutonium_fuel_time[id + id / 4];
			heatGen = fission_plutonium_heat_generation[id + id / 4];
			efficiency = fission_plutonium_efficiency[id + id / 4];
			criticality = fission_plutonium_criticality[id + id / 4];
			selfPriming = fission_plutonium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum MixedFuelType implements IStringSerializable, IFissionFuelEnum {
		
		MIX_239_TR("mix_239_tr", 0),
		MIX_239_OX("mix_239_ox", 1),
		MIX_239_NI("mix_239_ni", 2),
		MIX_239_ZA("mix_239_za", 3),
		MIX_241_TR("mix_241_tr", 4),
		MIX_241_OX("mix_241_ox", 5),
		MIX_241_NI("mix_241_ni", 6),
		MIX_241_ZA("mix_241_za", 7);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		MixedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_mixed_fuel_time[id + id / 4];
			heatGen = fission_mixed_heat_generation[id + id / 4];
			efficiency = fission_mixed_efficiency[id + id / 4];
			criticality = fission_mixed_criticality[id + id / 4];
			selfPriming = fission_mixed_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum AmericiumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LEA_242_TR("lea_242_tr", 0),
		LEA_242_OX("lea_242_ox", 1),
		LEA_242_NI("lea_242_ni", 2),
		LEA_242_ZA("lea_242_za", 3),
		HEA_242_TR("hea_242_tr", 4),
		HEA_242_OX("hea_242_ox", 5),
		HEA_242_NI("hea_242_ni", 6),
		HEA_242_ZA("hea_242_za", 7);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		AmericiumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_americium_fuel_time[id + id / 4];
			heatGen = fission_americium_heat_generation[id + id / 4];
			efficiency = fission_americium_efficiency[id + id / 4];
			criticality = fission_americium_criticality[id + id / 4];
			selfPriming = fission_americium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum CuriumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LECm_243_TR("lecm_243_tr", 0),
		LECm_243_OX("lecm_243_ox", 1),
		LECm_243_NI("lecm_243_ni", 2),
		LECm_243_ZA("lecm_243_za", 3),
		HECm_243_TR("hecm_243_tr", 4),
		HECm_243_OX("hecm_243_ox", 5),
		HECm_243_NI("hecm_243_ni", 6),
		HECm_243_ZA("hecm_243_za", 7),
		LECm_245_TR("lecm_245_tr", 8),
		LECm_245_OX("lecm_245_ox", 9),
		LECm_245_NI("lecm_245_ni", 10),
		LECm_245_ZA("lecm_245_za", 11),
		HECm_245_TR("hecm_245_tr", 12),
		HECm_245_OX("hecm_245_ox", 13),
		HECm_245_NI("hecm_245_ni", 14),
		HECm_245_ZA("hecm_245_za", 15),
		LECm_247_TR("lecm_247_tr", 16),
		LECm_247_OX("lecm_247_ox", 17),
		LECm_247_NI("lecm_247_ni", 18),
		LECm_247_ZA("lecm_247_za", 19),
		HECm_247_TR("hecm_247_tr", 20),
		HECm_247_OX("hecm_247_ox", 21),
		HECm_247_NI("hecm_247_ni", 22),
		HECm_247_ZA("hecm_247_za", 23);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		CuriumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_curium_fuel_time[id + id / 4];
			heatGen = fission_curium_heat_generation[id + id / 4];
			efficiency = fission_curium_efficiency[id + id / 4];
			criticality = fission_curium_criticality[id + id / 4];
			selfPriming = fission_curium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum BerkeliumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LEB_248_TR("leb_248_tr", 0),
		LEB_248_OX("leb_248_ox", 1),
		LEB_248_NI("leb_248_ni", 2),
		LEB_248_ZA("leb_248_za", 3),
		HEB_248_TR("heb_248_tr", 4),
		HEB_248_OX("heb_248_ox", 5),
		HEB_248_NI("heb_248_ni", 6),
		HEB_248_ZA("heb_248_za", 7);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		BerkeliumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_berkelium_fuel_time[id + id / 4];
			heatGen = fission_berkelium_heat_generation[id + id / 4];
			efficiency = fission_berkelium_efficiency[id + id / 4];
			criticality = fission_berkelium_criticality[id + id / 4];
			selfPriming = fission_berkelium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum CaliforniumFuelType implements IStringSerializable, IFissionFuelEnum {
		
		LECf_249_TR("lecf_249_tr", 0),
		LECf_249_OX("lecf_249_ox", 1),
		LECf_249_NI("lecf_249_ni", 2),
		LECf_249_ZA("lecf_249_za", 3),
		HECf_249_TR("hecf_249_tr", 4),
		HECf_249_OX("hecf_249_ox", 5),
		HECf_249_NI("hecf_249_ni", 6),
		HECf_249_ZA("hecf_249_za", 7),
		LECf_251_TR("lecf_251_tr", 8),
		LECf_251_OX("lecf_251_ox", 9),
		LECf_251_NI("lecf_251_ni", 10),
		LECf_251_ZA("lecf_251_za", 11),
		HECf_251_TR("hecf_251_tr", 12),
		HECf_251_OX("hecf_251_ox", 13),
		HECf_251_NI("hecf_251_ni", 14),
		HECf_251_ZA("hecf_251_za", 15);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		CaliforniumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = fission_californium_fuel_time[id + id / 4];
			heatGen = fission_californium_heat_generation[id + id / 4];
			efficiency = fission_californium_efficiency[id + id / 4];
			criticality = fission_californium_criticality[id + id / 4];
			selfPriming = fission_californium_self_priming[id + id / 4];
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
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public enum ThoriumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		TBU_TR("tbu_tr", 0),
		TBU_OX("tbu_ox", 1),
		TBU_NI("tbu_ni", 2),
		TBU_ZA("tbu_za", 3);
		
		private final String name;
		private final int id;
		
		ThoriumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum UraniumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LEU_233_TR("leu_233_tr", 0),
		LEU_233_OX("leu_233_ox", 1),
		LEU_233_NI("leu_233_ni", 2),
		LEU_233_ZA("leu_233_za", 3),
		HEU_233_TR("heu_233_tr", 4),
		HEU_233_OX("heu_233_ox", 5),
		HEU_233_NI("heu_233_ni", 6),
		HEU_233_ZA("heu_233_za", 7),
		LEU_235_TR("leu_235_tr", 8),
		LEU_235_OX("leu_235_ox", 9),
		LEU_235_NI("leu_235_ni", 10),
		LEU_235_ZA("leu_235_za", 11),
		HEU_235_TR("heu_235_tr", 12),
		HEU_235_OX("heu_235_ox", 13),
		HEU_235_NI("heu_235_ni", 14),
		HEU_235_ZA("heu_235_za", 15);
		
		private final String name;
		private final int id;
		
		UraniumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum NeptuniumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LEN_236_TR("len_236_tr", 0),
		LEN_236_OX("len_236_ox", 1),
		LEN_236_NI("len_236_ni", 2),
		LEN_236_ZA("len_236_za", 3),
		HEN_236_TR("hen_236_tr", 4),
		HEN_236_OX("hen_236_ox", 5),
		HEN_236_NI("hen_236_ni", 6),
		HEN_236_ZA("hen_236_za", 7);
		
		private final String name;
		private final int id;
		
		NeptuniumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum PlutoniumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LEP_239_TR("lep_239_tr", 0),
		LEP_239_OX("lep_239_ox", 1),
		LEP_239_NI("lep_239_ni", 2),
		LEP_239_ZA("lep_239_za", 3),
		HEP_239_TR("hep_239_tr", 4),
		HEP_239_OX("hep_239_ox", 5),
		HEP_239_NI("hep_239_ni", 6),
		HEP_239_ZA("hep_239_za", 7),
		LEP_241_TR("lep_241_tr", 8),
		LEP_241_OX("lep_241_ox", 9),
		LEP_241_NI("lep_241_ni", 10),
		LEP_241_ZA("lep_241_za", 11),
		HEP_241_TR("hep_241_tr", 12),
		HEP_241_OX("hep_241_ox", 13),
		HEP_241_NI("hep_241_ni", 14),
		HEP_241_ZA("hep_241_za", 15);
		
		private final String name;
		private final int id;
		
		PlutoniumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum MixedDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		MIX_239_TR("mix_239_tr", 0),
		MIX_239_OX("mix_239_ox", 1),
		MIX_239_NI("mix_239_ni", 2),
		MIX_239_ZA("mix_239_za", 3),
		MIX_241_TR("mix_241_tr", 4),
		MIX_241_OX("mix_241_ox", 5),
		MIX_241_NI("mix_241_ni", 6),
		MIX_241_ZA("mix_241_za", 7);
		
		private final String name;
		private final int id;
		
		MixedDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum AmericiumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LEA_242_TR("lea_242_tr", 0),
		LEA_242_OX("lea_242_ox", 1),
		LEA_242_NI("lea_242_ni", 2),
		LEA_242_ZA("lea_242_za", 3),
		HEA_242_TR("hea_242_tr", 4),
		HEA_242_OX("hea_242_ox", 5),
		HEA_242_NI("hea_242_ni", 6),
		HEA_242_ZA("hea_242_za", 7);
		
		private final String name;
		private final int id;
		
		AmericiumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CuriumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LECm_243_TR("lecm_243_tr", 0),
		LECm_243_OX("lecm_243_ox", 1),
		LECm_243_NI("lecm_243_ni", 2),
		LECm_243_ZA("lecm_243_za", 3),
		HECm_243_TR("hecm_243_tr", 4),
		HECm_243_OX("hecm_243_ox", 5),
		HECm_243_NI("hecm_243_ni", 6),
		HECm_243_ZA("hecm_243_za", 7),
		LECm_245_TR("lecm_245_tr", 8),
		LECm_245_OX("lecm_245_ox", 9),
		LECm_245_NI("lecm_245_ni", 10),
		LECm_245_ZA("lecm_245_za", 11),
		HECm_245_TR("hecm_245_tr", 12),
		HECm_245_OX("hecm_245_ox", 13),
		HECm_245_NI("hecm_245_ni", 14),
		HECm_245_ZA("hecm_245_za", 15),
		LECm_247_TR("lecm_247_tr", 16),
		LECm_247_OX("lecm_247_ox", 17),
		LECm_247_NI("lecm_247_ni", 18),
		LECm_247_ZA("lecm_247_za", 19),
		HECm_247_TR("hecm_247_tr", 20),
		HECm_247_OX("hecm_247_ox", 21),
		HECm_247_NI("hecm_247_ni", 22),
		HECm_247_ZA("hecm_247_za", 23);
		
		private final String name;
		private final int id;
		
		CuriumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum BerkeliumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LEB_248_TR("leb_248_tr", 0),
		LEB_248_OX("leb_248_ox", 1),
		LEB_248_NI("leb_248_ni", 2),
		LEB_248_ZA("leb_248_za", 3),
		HEB_248_TR("heb_248_tr", 4),
		HEB_248_OX("heb_248_ox", 5),
		HEB_248_NI("heb_248_ni", 6),
		HEB_248_ZA("heb_248_za", 7);
		
		private final String name;
		private final int id;
		
		BerkeliumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum CaliforniumDepletedFuelType implements IStringSerializable, IMetaEnum {
		
		LECf_249_TR("lecf_249_tr", 0),
		LECf_249_OX("lecf_249_ox", 1),
		LECf_249_NI("lecf_249_ni", 2),
		LECf_249_ZA("lecf_249_za", 3),
		HECf_249_TR("hecf_249_tr", 4),
		HECf_249_OX("hecf_249_ox", 5),
		HECf_249_NI("hecf_249_ni", 6),
		HECf_249_ZA("hecf_249_za", 7),
		LECf_251_TR("lecf_251_tr", 8),
		LECf_251_OX("lecf_251_ox", 9),
		LECf_251_NI("lecf_251_ni", 10),
		LECf_251_ZA("lecf_251_za", 11),
		HECf_251_TR("hecf_251_tr", 12),
		HECf_251_OX("hecf_251_ox", 13),
		HECf_251_NI("hecf_251_ni", 14),
		HECf_251_ZA("hecf_251_za", 15);
		
		private final String name;
		private final int id;
		
		CaliforniumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum IC2DepletedFuelType implements IStringSerializable, IMetaEnum {
		
		U("u", 0),
		MOX("mox", 1);
		
		private final String name;
		private final int id;
		
		IC2DepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum BoronType implements IStringSerializable, IMetaEnum {
		
		_10("10", 0),
		_11("11", 1);
		
		private final String name;
		private final int id;
		
		BoronType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum LithiumType implements IStringSerializable, IMetaEnum {
		
		_6("6", 0),
		_7("7", 1);
		
		private final String name;
		private final int id;
		
		LithiumType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
	
	public enum RadShieldingType implements IStringSerializable, IMetaEnum {
		
		LIGHT("light", 0),
		MEDIUM("medium", 1),
		HEAVY("heavy", 2);
		
		private final String name;
		private final int id;
		
		RadShieldingType(String name, int id) {
			this.name = name;
			this.id = id;
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
	}
}
