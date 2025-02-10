package nc.recipe.vanilla;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.*;
import nc.*;
import nc.enumm.IMetaEnum;
import nc.enumm.MetaEnums.*;
import nc.init.*;
import nc.item.ItemMultitool;
import nc.multiblock.quantum.QuantumGateEnums;
import nc.radiation.RadArmor;
import nc.recipe.vanilla.ingredient.BucketIngredient;
import nc.recipe.vanilla.recipe.*;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.*;
import vazkii.patchouli.common.item.ItemModBook;

import java.util.ArrayList;

import static nc.config.NCConfig.*;

public class CraftingRecipeHandler {
	
	public static void registerCraftingRecipes() {
		if (ModCheck.patchouliLoaded()) {
			addShapelessOreRecipe(ItemModBook.forBook("nuclearcraft:guide"), Items.BOOK, "ingotUranium");
			addShapelessOreRecipe(Items.BOOK, ItemModBook.forBook("nuclearcraft:guide"));
		}
		
		ingotBlockRecipes(IngotType.class, NCBlocks.ingot_block, NCItems.ingot);
		ingotBlockRecipes(IngotType2.class, NCBlocks.ingot_block2, NCItems.ingot2);
		
		materialBlockRecipes(0, "Molybdenum");
		materialBlockRecipes(1, "CopperOxide");
		materialBlockRecipes(2, "Cobalt");
		materialBlockRecipes(3, "Nickel");
		materialBlockRecipes(4, "Platinum");
		
		blockCompress(NCBlocks.fertile_isotope, 0, "blockUranium238", "ingotUranium238");
		blockCompress(NCBlocks.fertile_isotope, 1, "blockNeptunium237", "ingotNeptunium237");
		blockCompress(NCBlocks.fertile_isotope, 2, "blockPlutonium242", "ingotPlutonium242");
		blockCompress(NCBlocks.fertile_isotope, 3, "blockAmericium243", "ingotAmericium243");
		blockCompress(NCBlocks.fertile_isotope, 4, "blockCurium246", "ingotCurium246");
		blockCompress(NCBlocks.fertile_isotope, 5, "blockBerkelium247", "ingotBerkelium247");
		blockCompress(NCBlocks.fertile_isotope, 6, "blockCalifornium252", "ingotCalifornium252");
		
		blockOpen(NCItems.uranium, 10, "ingotUranium238", "blockUranium238");
		blockOpen(NCItems.neptunium, 5, "ingotNeptunium237", "blockNeptunium237");
		blockOpen(NCItems.plutonium, 15, "ingotPlutonium242", "blockPlutonium242");
		blockOpen(NCItems.americium, 10, "ingotAmericium243", "blockAmericium243");
		blockOpen(NCItems.curium, 10, "ingotCurium246", "blockCurium246");
		blockOpen(NCItems.berkelium, 0, "ingotBerkelium247", "blockBerkelium247");
		blockOpen(NCItems.californium, 15, "ingotCalifornium252", "blockCalifornium252");
		
		if (register_processor[0]) {
			addShapedOreRecipe(NCBlocks.nuclear_furnace, "PTP", "TFT", "PTP", 'T', "ingotTough", 'P', "plateBasic", 'F', Blocks.FURNACE);
		}
		if (register_processor[1]) {
			addShapedOreRecipe(NCBlocks.manufactory, "LRL", "FPF", "LSL", 'P', Blocks.PISTON, 'L', "ingotLead", 'S', "solenoidCopper", 'R', "dustRedstone", 'F', Items.FLINT);
		}
		if (register_processor[2]) {
			addShapedOreRecipe(NCBlocks.separator, "PMP", "RCR", "PMP", 'C', "chassis", 'P', "plateBasic", 'M', "motor", 'R', "dustRedstone");
		}
		if (register_processor[3]) {
			addShapedOreRecipe(NCBlocks.decay_hastener, "PGP", "ECE", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'S', "solenoidCopper", 'G', "dustGlowstone", 'E', Items.ENDER_PEARL);
		}
		if (register_processor[4]) {
			addShapedOreRecipe(NCBlocks.fuel_reprocessor, "PBP", "TCT", "PAP", 'C', "chassis", 'P', "plateBasic", 'A', "actuator", 'T', "ingotTough", 'B', "ingotBoron");
		}
		if (register_processor[5]) {
			addShapedOreRecipe(NCBlocks.alloy_furnace, "PRP", "BFB", "PSP", 'F', Blocks.FURNACE, 'P', "plateBasic", 'S', "solenoidCopper", 'R', "dustRedstone", 'B', Items.BRICK);
		}
		if (register_processor[6]) {
			addShapedOreRecipe(NCBlocks.infuser, "PBP", "GCG", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'G', "ingotGold", 'S', "servo", 'B', Items.BUCKET);
		}
		if (register_processor[7]) {
			addShapedOreRecipe(NCBlocks.melter, "PNP", "NCN", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'N', "ingotBrickNether", 'S', "servo");
		}
		if (register_processor[8]) {
			addShapedOreRecipe(NCBlocks.supercooler, "PDP", "HCH", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'D', "ingotMagnesiumDiboride", 'H', "ingotHardCarbon", 'S', "servo");
		}
		if (register_processor[9]) {
			addShapedOreRecipe(NCBlocks.electrolyzer, "PGP", "SCS", "PMP", 'C', "chassis", 'P', "plateAdvanced", 'S', "solenoidCopper", 'G', "ingotGraphite", 'M', "motor");
		}
		if (register_processor[10]) {
			addShapedOreRecipe(NCBlocks.assembler, "PHP", "ACA", "PMP", 'C', "chassis", 'P', "plateBasic", 'H', "ingotHardCarbon", 'A', "actuator", 'M', "motor");
		}
		if (register_processor[11]) {
			addShapedOreRecipe(NCBlocks.ingot_former, "PHP", "FCF", "PTP", 'C', "chassis", 'P', "plateBasic", 'F', "ingotFerroboron", 'T', "ingotTough", 'H', Blocks.HOPPER);
		}
		if (register_processor[12]) {
			addShapedOreRecipe(NCBlocks.pressurizer, "PTP", "ACA", "PTP", 'C', "chassis", 'P', "plateAdvanced", 'T', "ingotTough", 'A', "actuator");
		}
		if (register_processor[13]) {
			addShapedOreRecipe(NCBlocks.chemical_reactor, "PMP", "GCG", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'G', "dustGlowstone", 'M', "motor", 'S', "servo");
		}
		if (register_processor[14]) {
			addShapedOreRecipe(NCBlocks.salt_mixer, "PSP", "BCB", "PMP", 'C', "chassis", 'P', "plateBasic", 'B', Items.BUCKET, 'M', "motor", 'S', "ingotSteel");
		}
		if (register_processor[15]) {
			addShapedOreRecipe(NCBlocks.crystallizer, "PSP", "SCS", "PUP", 'C', "chassis", 'P', "plateAdvanced", 'S', "solenoidCopper", 'U', Items.CAULDRON);
		}
		if (register_processor[16]) {
			addShapedOreRecipe(NCBlocks.enricher, "PHP", "LCL", "PMP", 'C', "chassis", 'P', "plateAdvanced", 'L', "gemLapis", 'M', "motor", 'H', Blocks.HOPPER);
		}
		if (register_processor[17]) {
			addShapedOreRecipe(NCBlocks.extractor, "PMP", "BCB", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'M', "ingotMagnesium", 'S', "servo", 'B', Items.BUCKET);
		}
		if (register_processor[18]) {
			addShapedOreRecipe(NCBlocks.centrifuge, "PFP", "MCM", "PSP", 'C', "chassis", 'P', "plateAdvanced", 'M', "motor", 'F', "ingotFerroboron", 'S', "servo");
		}
		if (register_processor[19]) {
			addShapedOreRecipe(NCBlocks.rock_crusher, "PMP", "ACA", "PTP", 'C', "chassis", 'P', "plateAdvanced", 'A', "actuator", 'T', "ingotTough", 'M', "motor");
		}
		if (register_processor[20]) {
			addShapedOreRecipe(NCBlocks.electric_furnace, "LIL", "BFB", "LSL", 'F', Blocks.FURNACE, 'L', "ingotLead", 'S', "solenoidCopper", 'I', "ingotIron", 'B', Items.BRICK);
		}
		
		addShapedOreRecipe(NCBlocks.machine_interface, " A ", "MCM", " S ", 'C', "chassis", 'A', "actuator", 'M', "motor", 'S', "servo");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_frame, 8), " B ", "BCB", " B ", 'B', "ingotBronze", 'C', "chassis");
		addShapelessOreRecipe(NCBlocks.machine_frame, NCBlocks.machine_glass);
		addShapelessOreRecipe(NCBlocks.machine_glass, NCBlocks.machine_frame, "blockGlass");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_power_port, 4), "BPB", "RCR", "BPB", 'B', "ingotBronze", 'P', "ingotCopper", 'R', "dustRedstone", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_process_port, 4), "BHB", "SCS", "BHB", 'B', "ingotBronze", 'H', Blocks.HOPPER, 'S', "servo", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_reservoir_port, 4), "BSB", "TCT", "BSB", 'B', "ingotBronze", 'S', "servo", 'T', "ingotSteel", 'C', "chassis");
		addShapedOreRecipe(NCBlocks.machine_redstone_port, "BRB", "TCT", "BRB", 'B', "ingotBronze", 'R', "dustRedstone", 'T', Blocks.REDSTONE_TORCH, 'C', "chassis");
		if (ModCheck.openComputersLoaded()) {
			addShapedOreRecipe(NCBlocks.machine_computer_port, "BMB", "LCL", "BPB", 'B', "ingotBronze", 'M', RegistryHelper.itemStackFromRegistry("opencomputers:material:7"), 'L', RegistryHelper.blockStackFromRegistry("opencomputers:cable:0"), 'P', RegistryHelper.itemStackFromRegistry("opencomputers:material:4"), 'C', "chassis");
		}
		
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 0), "SQS", "QSQ", "SQS", 'S', "sinteredSteel", 'Q', "dustQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 0), "SQS", "QSQ", "SQS", 'S', "sinteredSteel", 'Q', "dustNetherQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 1), "PQP", "QPQ", "PQP", 'P', "ingotPolyethersulfone", 'Q', "dustQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 1), "PQP", "QPQ", "PQP", 'P', "ingotPolyethersulfone", 'Q', "dustNetherQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 2), "ZQZ", "QZQ", "ZQZ", 'Z', "ingotZirfon", 'Q', "dustQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_diaphragm, 4, 2), "ZQZ", "QZQ", "ZQZ", 'Z', "ingotZirfon", 'Q', "dustNetherQuartz");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_sieve_assembly, 4, 0), "SSS", "OSO", "SSS", 'S', "ingotSteel", 'O', "dustObsidian");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_sieve_assembly, 4, 1), "PPP", "OPO", "PPP", 'P', "ingotPolytetrafluoroethene", 'O', "dustObsidian");
		addShapedOreRecipe(new ItemStack(NCBlocks.machine_sieve_assembly, 4, 2), "HHH", "OHO", "HHH", 'H', "ingotHastelloy", 'O', "dustObsidian");
		
		addShapedOreRecipe(NCBlocks.electrolyzer_controller, "BSB", "TCT", "BSB", 'B', "ingotBronze", 'S', "sinteredSteel", 'T', "ingotTough", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.electrolyzer_cathode_terminal, 2), "BSB", "RCR", "BSB", 'B', "ingotBronze", 'S', "solenoidCopper", 'R', "dustRedstone", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.electrolyzer_anode_terminal, 2), "BSB", "LCL", "BSB", 'B', "ingotBronze", 'S', "solenoidCopper", 'L', "gemLapis", 'C', "chassis");
		
		addShapedOreRecipe(NCBlocks.distiller_controller, "BFB", "HCH", "BFB", 'B', "ingotBronze", 'F', "ingotFerroboron", 'H', "ingotHardCarbon", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.distiller_sieve_tray, 4), "BSB", "RCR", "BSB", 'B', "ingotBronze", 'S', "ingotSteel", 'R', "ingotBoron", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.distiller_reflux_unit, 4), "BFB", "TCT", "BSB", 'B', "ingotBronze", 'F', "ingotFerroboron", 'T', "ingotTough", 'S', "sinteredSteel", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.distiller_reboiling_unit, 4), "BSB", "PCP", "BLB", 'B', "ingotBronze", 'S', "sinteredSteel", 'P', "ingotCopper", 'L', "solenoidCopper", 'C', "chassis");
		addShapedOreRecipe(new ItemStack(NCBlocks.distiller_liquid_distributor, 4), "BFB", "TCT", "BMB", 'B', "ingotBronze", 'F', "ingotFerroboron", 'T', "ingotTin", 'M', "motor", 'C', "chassis");
		
		addShapedOreRecipe(NCBlocks.rtg_uranium, "PGP", "GUG", "PGP", 'G', "ingotGraphite", 'P', "plateBasic", 'U', "blockUranium238");
		addShapedOreRecipe(NCBlocks.rtg_plutonium, "PGP", "GUG", "PGP", 'G', "ingotGraphite", 'P', "plateAdvanced", 'U', "ingotPlutonium238All");
		addShapedOreRecipe(NCBlocks.rtg_americium, "PGP", "GAG", "PGP", 'G', "ingotGraphite", 'P', "plateAdvanced", 'A', "ingotAmericium241All");
		addShapedOreRecipe(NCBlocks.rtg_californium, "PGP", "GCG", "PGP", 'G', "ingotGraphite", 'P', "plateAdvanced", 'C', "ingotCalifornium250All");
		
		addShapedOreRecipe(NCBlocks.solar_panel_basic, "GQG", "PLP", "CPC", 'G', "dustGraphite", 'Q', "dustQuartz", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'L', "gemLapis", 'C', "solenoidCopper");
		addShapedOreRecipe(NCBlocks.solar_panel_basic, "GQG", "PLP", "CPC", 'G', "dustGraphite", 'Q', "dustNetherQuartz", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'L', "gemLapis", 'C', "solenoidCopper");
		addShapedOreRecipe(NCBlocks.solar_panel_advanced, "PGP", "SSS", "PCP", 'S', NCBlocks.solar_panel_basic, 'G', "dustGraphite", 'P', "plateAdvanced", 'C', "solenoidCopper");
		addShapedOreRecipe(NCBlocks.solar_panel_du, "PGP", "SSS", "PMP", 'S', NCBlocks.solar_panel_advanced, 'G', "dustGraphite", 'P', "plateDU", 'M', "solenoidMagnesiumDiboride");
		addShapedOreRecipe(NCBlocks.solar_panel_elite, "PBP", "SSS", "PMP", 'S', NCBlocks.solar_panel_du, 'B', "gemBoronArsenide", 'P', "plateElite", 'M', "solenoidMagnesiumDiboride");
		
		addShapedOreRecipe(NCBlocks.decay_generator, "LCL", "CRC", "LCL", 'C', "cobblestone", 'L', "ingotLead", 'R', "dustRedstone");
		
		if (register_battery[0]) {
			addShapedOreRecipe(NCBlocks.voltaic_pile_basic, "PSP", "SMS", "PSP", 'P', "plateBasic", 'S', "solenoidCopper", 'M', "blockMagnesium");
			addShapedEnergyRecipe(NCBlocks.voltaic_pile_advanced, "PMP", "VVV", "PCP", 'V', NCBlocks.voltaic_pile_basic, 'P', "plateAdvanced", 'M', "ingotMagnesium", 'C', "ingotCopper");
			addShapedEnergyRecipe(NCBlocks.voltaic_pile_du, "PMP", "VVV", "PCP", 'V', NCBlocks.voltaic_pile_advanced, 'P', "plateDU", 'M', "ingotMagnesium", 'C', "ingotCopper");
			addShapedEnergyRecipe(NCBlocks.voltaic_pile_elite, "PMP", "VVV", "PCP", 'V', NCBlocks.voltaic_pile_du, 'P', "plateElite", 'M', "ingotMagnesium", 'C', "ingotCopper");
		}
		
		addShapedOreRecipe(NCItems.lithium_ion_cell, "CCC", "FLF", "DDD", 'C', "ingotHardCarbon", 'F', "ingotFerroboron", 'L', "ingotLithium", 'D', "ingotLithiumManganeseDioxide");
		if (register_battery[1]) {
			addShapedEnergyRecipe(NCBlocks.lithium_ion_battery_basic, "PCP", "CSC", "PCP", 'C', NCItems.lithium_ion_cell, 'P', "plateElite", 'S', "solenoidMagnesiumDiboride");
			addShapedEnergyRecipe(NCBlocks.lithium_ion_battery_advanced, "PDP", "LLL", "PSP", 'L', NCBlocks.lithium_ion_battery_basic, 'P', "plateAdvanced", 'D', "ingotLithiumManganeseDioxide", 'S', "solenoidMagnesiumDiboride");
			addShapedEnergyRecipe(NCBlocks.lithium_ion_battery_du, "PDP", "LLL", "PSP", 'L', NCBlocks.lithium_ion_battery_advanced, 'P', "plateDU", 'D', "ingotLithiumManganeseDioxide", 'S', "solenoidMagnesiumDiboride");
			addShapedEnergyRecipe(NCBlocks.lithium_ion_battery_elite, "PDP", "LLL", "PSP", 'L', NCBlocks.lithium_ion_battery_du, 'P', "plateElite", 'D', "ingotLithiumManganeseDioxide", 'S', "solenoidMagnesiumDiboride");
		}
		
		addShapedOreRecipe(NCBlocks.bin, "PZP", "Z Z", "PZP", 'P', "plateBasic", 'Z', "ingotSiliconCarbide");
		
		if (register_passive[0]) {
			addShapedFluidRecipe(NCBlocks.cobblestone_generator, "PIP", "L W", "PIP", 'I', "ingotTin", 'P', "plateBasic", 'L', new BucketIngredient("lava"), 'W', new BucketIngredient("water"));
			addShapedFluidRecipe(NCBlocks.cobblestone_generator, "PIP", "W L", "PIP", 'I', "ingotTin", 'P', "plateBasic", 'L', new BucketIngredient("lava"), 'W', new BucketIngredient("water"));
			addShapedOreRecipe(NCBlocks.cobblestone_generator_compact, "CCC", "CIC", "CCC", 'C', NCBlocks.cobblestone_generator, 'I', "ingotBronze");
			addShapedOreRecipe(NCBlocks.cobblestone_generator_dense, "CCC", "CIC", "CCC", 'C', NCBlocks.cobblestone_generator_compact, 'I', "ingotGold");
		}
		
		if (register_passive[1]) {
			addShapedFluidRecipe(NCBlocks.water_source, "PIP", "W W", "PIP", 'I', "ingotTin", 'P', "plateBasic", 'W', new BucketIngredient("water"));
			addShapedOreRecipe(NCBlocks.water_source_compact, "CCC", "CIC", "CCC", 'C', NCBlocks.water_source, 'I', "ingotBronze");
			addShapedOreRecipe(NCBlocks.water_source_dense, "CCC", "CIC", "CCC", 'C', NCBlocks.water_source_compact, 'I', "ingotGold");
		}
		
		if (register_passive[2]) {
			addShapedOreRecipe(NCBlocks.nitrogen_collector, "PIP", "B B", "PIP", 'I', "ingotBeryllium", 'P', "plateAdvanced", 'B', Items.BUCKET);
			addShapedOreRecipe(NCBlocks.nitrogen_collector_compact, "CCC", "CIC", "CCC", 'C', NCBlocks.nitrogen_collector, 'I', "ingotBronze");
			addShapedOreRecipe(NCBlocks.nitrogen_collector_dense, "CCC", "CIC", "CCC", 'C', NCBlocks.nitrogen_collector_compact, 'I', "ingotGold");
		}
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_casing, 8), " P ", "PFP", " P ", 'P', "plateBasic", 'F', "steelFrame");
		addShapelessOreRecipe(NCBlocks.fission_casing, NCBlocks.fission_glass);
		addShapelessOreRecipe(NCBlocks.fission_casing, NCBlocks.fission_conductor);
		addShapelessOreRecipe(NCBlocks.fission_glass, NCBlocks.fission_casing, "blockGlass");
		addShapelessOreRecipe(NCBlocks.fission_conductor, NCBlocks.fission_casing);
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_monitor, 4), "PGP", "TFT", "PGP", 'P', "plateBasic", 'G', "dustGlowstone", 'T', "ingotTough", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_power_port, 4), "PCP", "RFR", "PCP", 'P', "plateBasic", 'C', "ingotCopper", 'R', "dustRedstone", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_vent, 4), "PTP", "SFS", "PTP", 'P', "plateBasic", 'T', "ingotTough", 'S', "servo", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_irradiator, 4), "PZP", "AFA", "PZP", 'P', "plateBasic", 'Z', "ingotZirconium", 'A', "ingotZircaloy", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_source, 2, 0), "PRP", "BFB", "PRP", 'P', "plateBasic", 'R', "dustRadium", 'B', "dustBeryllium", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_source, 2, 1), "PLP", "BFB", "PLP", 'P', "plateBasic", 'L', "dustPolonium", 'B', "dustBeryllium", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_source, 2, 2), "PCP", "CFC", "PCP", 'P', "plateBasic", 'C', "ingotCalifornium252All", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_shield, 4, 0), "PBP", "SFS", "PBP", 'P', "plateBasic", 'B', "ingotBoron", 'S', "ingotSilver", 'F', "steelFrame");
		if (ModCheck.openComputersLoaded()) {
			addShapedOreRecipe(NCBlocks.fission_computer_port, "PMP", "CFC", "PBP", 'P', "plateBasic", 'M', RegistryHelper.itemStackFromRegistry("opencomputers:material:7"), 'C', RegistryHelper.blockStackFromRegistry("opencomputers:cable:0"), 'B', RegistryHelper.itemStackFromRegistry("opencomputers:material:4"), 'F', "steelFrame");
		}
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_reflector, 2, 0), "BGB", "GFG", "BGB", 'B', "ingotBeryllium", 'G', "ingotGraphite", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_reflector, 2, 1), "LSL", "SFS", "LSL", 'L', "ingotLead", 'S', "ingotSteel", 'F', "steelFrame");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_irradiator_port, 4), "PHP", "ZFZ", "PHP", 'P', "plateBasic", 'H', Blocks.HOPPER, 'Z', "ingotZirconium", 'F', "steelFrame");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_cell_port, 4), "PHP", "ZFZ", "PHP", 'P', "plateAdvanced", 'H', Blocks.HOPPER, 'Z', "ingotZircaloy", 'F', "steelFrame");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_vessel_port, 4), "PSP", "ZFZ", "PSP", 'P', "plateElite", 'S', "servo", 'Z', "ingotZircaloy", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 4, 0), "PSP", "TFT", "PSP", 'P', "plateElite", 'S', "servo", 'T', "ingotThermoconducting", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 1), " I ", "IPI", " I ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'I', "ingotIron");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 2), "RRR", "RPR", "RRR", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'R', "dustRedstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 3), "QQQ", "QPQ", "QQQ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'Q', "gemQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 4), "DOD", "OPO", "DOD", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'O', "obsidian", 'D', "dustObsidian");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 5), "INI", "NPN", "INI", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'N', Blocks.NETHER_BRICK, 'I', "ingotBrickNether");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 6), "GGG", "GPG", "GGG", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'G', "dustGlowstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 7), "LLL", "LPL", "LLL", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'L', "gemLapis");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 8), " G ", "GPG", " G ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'G', "ingotGold");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 9), " R ", "RPR", " R ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'R', "gemPrismarine");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 10), "BBB", "BPB", "BBB", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'B', "slimeball");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 11), "DED", "EPE", "DED", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'E', "endstone", 'D', "dustEndstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 12), "CBC", "BPB", "CBC", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'B', Blocks.PURPUR_BLOCK, 'C', Items.CHORUS_FRUIT_POPPED);
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 13), " D ", "DPD", " D ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'D', "gemDiamond");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 14), " E ", "EPE", " E ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'E', "gemEmerald");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port, 1, 15), " C ", "CPC", " C ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'C', "ingotCopper");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 0), " T ", "TPT", " T ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'T', "ingotTin");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 1), " L ", "LPL", " L ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'L', "ingotLead");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 2), " B ", "BPB", " B ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'B', "ingotBoron");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 3), " L ", "LPL", " L ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'L', "ingotLithium");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 4), " M ", "MPM", " M ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'M', "ingotMagnesium");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 5), " M ", "MPM", " M ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'M', "ingotManganese");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 6), " A ", "APA", " A ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'A', "ingotAluminum");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 7), " I ", "IPI", " I ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'I', "ingotSilver");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 8), "FFF", "FPF", "FFF", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'F', "gemFluorite");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 9), "VVV", "VPV", "VVV", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'V', "gemVilliaumite");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 10), "CCC", "CPC", "CCC", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'C', "gemCarobbiite");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 11), "AAA", "APA", "AAA", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'A', "dustArsenic");
		addShapelessFluidRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 12), new ItemStack(NCBlocks.fission_heater_port, 1, 0), new BucketIngredient("liquid_nitrogen"));
		addShapelessFluidRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 13), new ItemStack(NCBlocks.fission_heater_port, 1, 0), new BucketIngredient("liquid_helium"));
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 14), " E ", "EPE", " E ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'E', "ingotEnderium");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_heater_port2, 1, 15), " C ", "CPC", " C ", 'P', new ItemStack(NCBlocks.fission_heater_port, 1, 0), 'C', "dustCryotheum");
		
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_source_manager, 4), "PEP", "RFR", "PEP", 'P', "plateBasic", 'E', "dustEnergetic", 'R', Items.REPEATER, 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.fission_shield_manager, 4), "PTP", "RFR", "PTP", 'P', "plateBasic", 'T', "ingotTough", 'R', Items.REPEATER, 'F', "steelFrame");
		
		addShapedOreRecipe(NCBlocks.solid_fission_controller, "PTP", "HFH", "PTP", 'P', "plateAdvanced", 'T', "ingotTough", 'H', "ingotHardCarbon", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_cell, 4), "PTP", "ZFZ", "PTP", 'P', "plateAdvanced", 'T', "ingotTough", 'Z', "ingotZircaloy", 'F', "steelFrame");
		addShapelessFluidRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 0), "emptyHeatSink", new BucketIngredient("water"));
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 1), " I ", "ISI", " I ", 'S', "emptyHeatSink", 'I', "ingotIron");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 2), "RRR", "RSR", "RRR", 'S', "emptyHeatSink", 'R', "dustRedstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 3), "QQQ", "QSQ", "QQQ", 'S', "emptyHeatSink", 'Q', "gemQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 4), "DOD", "OSO", "DOD", 'S', "emptyHeatSink", 'O', "obsidian", 'D', "dustObsidian");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 5), "INI", "NSN", "INI", 'S', "emptyHeatSink", 'N', Blocks.NETHER_BRICK, 'I', "ingotBrickNether");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 6), "GGG", "GSG", "GGG", 'S', "emptyHeatSink", 'G', "dustGlowstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 7), "LLL", "LSL", "LLL", 'S', "emptyHeatSink", 'L', "gemLapis");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 8), " G ", "GSG", " G ", 'S', "emptyHeatSink", 'G', "ingotGold");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 9), " P ", "PSP", " P ", 'S', "emptyHeatSink", 'P', "gemPrismarine");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 10), "BBB", "BSB", "BBB", 'S', "emptyHeatSink", 'B', "slimeball");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 11), "DED", "ESE", "DED", 'S', "emptyHeatSink", 'E', "endstone", 'D', "dustEndstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 12), "CPC", "PSP", "CPC", 'S', "emptyHeatSink", 'P', Blocks.PURPUR_BLOCK, 'C', Items.CHORUS_FRUIT_POPPED);
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 13), " D ", "DSD", " D ", 'S', "emptyHeatSink", 'D', "gemDiamond");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 14), " E ", "ESE", " E ", 'S', "emptyHeatSink", 'E', "gemEmerald");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink, 1, 15), " C ", "CSC", " C ", 'S', "emptyHeatSink", 'C', "ingotCopper");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 0), " T ", "TST", " T ", 'S', "emptyHeatSink", 'T', "ingotTin");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 1), " L ", "LSL", " L ", 'S', "emptyHeatSink", 'L', "ingotLead");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 2), " B ", "BSB", " B ", 'S', "emptyHeatSink", 'B', "ingotBoron");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 3), " L ", "LSL", " L ", 'S', "emptyHeatSink", 'L', "ingotLithium");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 4), " M ", "MSM", " M ", 'S', "emptyHeatSink", 'M', "ingotMagnesium");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 5), " M ", "MSM", " M ", 'S', "emptyHeatSink", 'M', "ingotManganese");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 6), " A ", "ASA", " A ", 'S', "emptyHeatSink", 'A', "ingotAluminum");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 7), " I ", "ISI", " I ", 'S', "emptyHeatSink", 'I', "ingotSilver");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 8), "FFF", "FSF", "FFF", 'S', "emptyHeatSink", 'F', "gemFluorite");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 9), "VVV", "VSV", "VVV", 'S', "emptyHeatSink", 'V', "gemVilliaumite");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 10), "CCC", "CSC", "CCC", 'S', "emptyHeatSink", 'C', "gemCarobbiite");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 11), "AAA", "ASA", "AAA", 'S', "emptyHeatSink", 'A', "dustArsenic");
		addShapelessFluidRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 12), "emptyHeatSink", new BucketIngredient("liquid_nitrogen"));
		addShapelessFluidRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 13), "emptyHeatSink", new BucketIngredient("liquid_helium"));
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 14), " E ", "ESE", " E ", 'S', "emptyHeatSink", 'E', "ingotEnderium");
		addShapedOreRecipe(new ItemStack(NCBlocks.solid_fission_sink2, 1, 15), " C ", "CSC", " C ", 'S', "emptyHeatSink", 'C', "dustCryotheum");
		
		addShapedOreRecipe(NCBlocks.salt_fission_controller, "PMP", "EFE", "PMP", 'P', "plateElite", 'E', "ingotExtreme", 'M', "ingotZirconiumMolybdenum", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_vessel, 4), "PMP", "ZFZ", "PMP", 'P', "plateElite", 'M', "ingotZirconiumMolybdenum", 'Z', "ingotZircaloy", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 8, 0), "PEP", "TFT", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'T', "ingotThermoconducting", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 1), " I ", "IHI", " I ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'I', "ingotIron");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 2), "RRR", "RHR", "RRR", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'R', "dustRedstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 3), "QQQ", "QHQ", "QQQ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'Q', "gemQuartz");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 4), "DOD", "OHO", "DOD", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'O', "obsidian", 'D', "dustObsidian");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 5), "INI", "NHN", "INI", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'N', Blocks.NETHER_BRICK, 'I', "ingotBrickNether");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 6), "GGG", "GHG", "GGG", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'G', "dustGlowstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 7), "LLL", "LHL", "LLL", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'L', "gemLapis");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 8), " G ", "GHG", " G ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'G', "ingotGold");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 9), " P ", "PHP", " P ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'P', "gemPrismarine");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 10), "BBB", "BHB", "BBB", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'B', "slimeball");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 11), "DED", "EHE", "DED", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'E', "endstone", 'D', "dustEndstone");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 12), "CPC", "PHP", "CPC", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'P', Blocks.PURPUR_BLOCK, 'C', Items.CHORUS_FRUIT_POPPED);
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 13), " D ", "DHD", " D ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'D', "gemDiamond");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 14), " E ", "EHE", " E ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'E', "gemEmerald");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater, 1, 15), " C ", "CHC", " C ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'C', "ingotCopper");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 0), " T ", "THT", " T ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'T', "ingotTin");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 1), " L ", "LHL", " L ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'L', "ingotLead");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 2), " B ", "BHB", " B ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'B', "ingotBoron");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 3), " L ", "LHL", " L ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'L', "ingotLithium");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 4), " M ", "MHM", " M ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'M', "ingotMagnesium");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 5), " M ", "MHM", " M ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'M', "ingotManganese");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 6), " A ", "AHA", " A ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'A', "ingotAluminum");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 7), " I ", "IHI", " I ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'I', "ingotSilver");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 8), "FFF", "FHF", "FFF", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'F', "gemFluorite");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 9), "VVV", "VHV", "VVV", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'V', "gemVilliaumite");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 10), "CCC", "CHC", "CCC", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'C', "gemCarobbiite");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 11), "AAA", "AHA", "AAA", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'A', "dustArsenic");
		addShapelessFluidRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 12), new ItemStack(NCBlocks.salt_fission_heater, 1, 0), new BucketIngredient("liquid_nitrogen"));
		addShapelessFluidRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 13), new ItemStack(NCBlocks.salt_fission_heater, 1, 0), new BucketIngredient("liquid_helium"));
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 14), " E ", "EHE", " E ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'E', "ingotEnderium");
		addShapedOreRecipe(new ItemStack(NCBlocks.salt_fission_heater2, 1, 15), " C ", "CHC", " C ", 'H', new ItemStack(NCBlocks.salt_fission_heater, 1, 0), 'C', "dustCryotheum");
		
		addShapedOreRecipe(NCBlocks.heat_exchanger_controller, "SES", "TFT", "SES", 'S', "ingotSteel", 'E', "ingotExtreme", 'T', "ingotThermoconducting", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.heat_exchanger_casing, 8), " S ", "SFS", " S ", 'S', "ingotSteel", 'F', "steelFrame");
		addShapelessOreRecipe(NCBlocks.heat_exchanger_casing, NCBlocks.heat_exchanger_glass);
		addShapelessOreRecipe(NCBlocks.heat_exchanger_glass, NCBlocks.heat_exchanger_casing, "blockGlass");
		addShapedOreRecipe(new ItemStack(NCBlocks.heat_exchanger_vent, 4), "SIS", "VFV", "SIS", 'S', "ingotSteel", 'I', "ingotFerroboron", 'V', "servo", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.heat_exchanger_tube_copper, 4), "SCS", "CFC", "SVS", 'S', "ingotSteel", 'C', "ingotCopper", 'F', "steelFrame", 'V', "servo");
		addShapedOreRecipe(new ItemStack(NCBlocks.heat_exchanger_tube_hard_carbon, 4), "SHS", "HFH", "SVS", 'S', "ingotSteel", 'H', "ingotHardCarbon", 'F', "steelFrame", 'V', "servo");
		addShapedOreRecipe(new ItemStack(NCBlocks.heat_exchanger_tube_thermoconducting, 4), "STS", "TFT", "SVS", 'S', "ingotSteel", 'T', "ingotThermoconducting", 'F', "steelFrame", 'V', "servo");
		addShapelessOreRecipe(NCBlocks.heat_exchanger_tube_copper, NCBlocks.condenser_tube_copper);
		addShapelessOreRecipe(NCBlocks.heat_exchanger_tube_hard_carbon, NCBlocks.condenser_tube_hard_carbon);
		addShapelessOreRecipe(NCBlocks.heat_exchanger_tube_thermoconducting, NCBlocks.condenser_tube_thermoconducting);
		addShapedOreRecipe(NCBlocks.heat_exchanger_redstone_port, "SRS", "TFT", "SRS", 'S', "ingotSteel", 'R', "dustRedstone", 'T', Blocks.REDSTONE_TORCH, 'F', "steelFrame");
		if (ModCheck.openComputersLoaded()) {
			addShapedOreRecipe(NCBlocks.heat_exchanger_computer_port, "SMS", "CFC", "SPS", 'S', "ingotSteel", 'M', RegistryHelper.itemStackFromRegistry("opencomputers:material:7"), 'C', RegistryHelper.blockStackFromRegistry("opencomputers:cable:0"), 'P', RegistryHelper.itemStackFromRegistry("opencomputers:material:4"), 'F', "steelFrame");
		}
		
		addShapedOreRecipe(NCBlocks.condenser_controller, "STS", "CFC", "STS", 'S', "ingotSteel", 'T', "ingotTough", 'C', "ingotThermoconducting", 'F', "steelFrame");
		addShapelessOreRecipe(NCBlocks.condenser_tube_copper, NCBlocks.heat_exchanger_tube_copper);
		addShapelessOreRecipe(NCBlocks.condenser_tube_hard_carbon, NCBlocks.heat_exchanger_tube_hard_carbon);
		addShapelessOreRecipe(NCBlocks.condenser_tube_thermoconducting, NCBlocks.heat_exchanger_tube_thermoconducting);
		
		addShapedOreRecipe(NCBlocks.turbine_controller, "STS", "TFT", "STS", 'S', "ingotHSLASteel", 'T', "ingotTough", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_casing, 8), " S ", "SFS", " S ", 'S', "ingotHSLASteel", 'F', "steelFrame");
		addShapelessOreRecipe(NCBlocks.turbine_casing, NCBlocks.turbine_glass);
		addShapelessOreRecipe(NCBlocks.turbine_glass, NCBlocks.turbine_casing, "blockGlass");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_shaft, 4), "SSS", "TTT", "SSS", 'S', "ingotHSLASteel", 'T', "ingotTough");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_blade_steel, 4), "SHS", "SHS", "SHS", 'S', "ingotSteel", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_blade_extreme, 4), "EHE", "EHE", "EHE", 'E', "ingotExtreme", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_blade_sic_sic_cmc, 4), "SHS", "SHS", "SHS", 'S', "ingotSiCSiCCMC", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_stator, 4), "SS", "SS", "SS", 'S', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_rotor_bearing, 4), "SGS", "GFG", "SGS", 'G', "nuggetGold", 'S', "ingotHSLASteel", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 0), "MMM", "HTH", "MMM", 'M', "ingotMagnesium", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 1), "BBB", "HTH", "BBB", 'B', "ingotBeryllium", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 2), "AAA", "HTH", "AAA", 'A', "ingotAluminum", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 3), "GGG", "HTH", "GGG", 'G', "ingotGold", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 4), "CCC", "HTH", "CCC", 'C', "ingotCopper", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_dynamo_coil, 2, 5), "SSS", "HTH", "SSS", 'S', "ingotSilver", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_coil_connector, 4), "HHH", "HTH", "HHH", 'T', "ingotTough", 'H', "ingotHSLASteel");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_inlet, 4), "STS", "VFV", "STS", 'S', "ingotHSLASteel", 'T', "ingotTough", 'V', "servo", 'F', "steelFrame");
		addShapedOreRecipe(new ItemStack(NCBlocks.turbine_outlet, 4), "SSS", "VFV", "SSS", 'S', "ingotHSLASteel", 'V', "servo", 'F', "steelFrame");
		addShapedOreRecipe(NCBlocks.turbine_redstone_port, "SRS", "TFT", "SRS", 'S', "ingotHSLASteel", 'R', "dustRedstone", 'T', Blocks.REDSTONE_TORCH, 'F', "steelFrame");
		if (ModCheck.openComputersLoaded()) {
			addShapedOreRecipe(NCBlocks.turbine_computer_port, "SMS", "CFC", "SPS", 'S', "ingotHSLASteel", 'M', RegistryHelper.itemStackFromRegistry("opencomputers:material:7"), 'C', RegistryHelper.blockStackFromRegistry("opencomputers:cable:0"), 'P', RegistryHelper.itemStackFromRegistry("opencomputers:material:4"), 'F', "steelFrame");
		}
		
		addShapedOreRecipe(new ItemStack(NCItems.part, 2, 0), "LG", "GL", 'L', "ingotLead", 'G', "dustGraphite");
		addShapedOreRecipe(new ItemStack(NCItems.part, 2, 0), "GL", "LG", 'L', "ingotLead", 'G', "dustGraphite");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 1), " R ", "TPT", " R ", 'R', "dustRedstone", 'T', "ingotTough", 'P', "plateBasic");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 2), "SUS", "UPU", "SUS", 'S', "dustSulfur", 'U', "ingotUranium238", 'P', "plateAdvanced");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 3), "RBR", "BPB", "RBR", 'R', "dustCrystalBinder", 'B', "ingotBoron", 'P', "plateDU");
		addShapedOreRecipe(new ItemStack(NCItems.part, 2, 4), "CC", "II", "CC", 'C', "ingotCopper", 'I', "ingotIron");
		addShapedOreRecipe(new ItemStack(NCItems.part, 2, 5), "MM", "TT", "MM", 'M', "ingotMagnesiumDiboride", 'T', "ingotTough");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 7), "F F", "RSR", "SCS", 'F', "ingotFerroboron", 'S', "ingotSteel", 'C', "ingotCopper", 'R', "dustRedstone");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 8), "SSG", "CCI", "SSG", 'G', "nuggetGold", 'S', "ingotSteel", 'I', "ingotIron", 'C', "solenoidCopper");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 9), "  S", "FP ", "CF ", 'F', "ingotFerroboron", 'S', "ingotSteel", 'P', Blocks.PISTON, 'C', "ingotCopper");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 10), "LSL", "STS", "LSL", 'L', "ingotLead", 'T', "ingotTough", 'S', "ingotSteel");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 11), "PTP", "I I", "PTP", 'P', "plateBasic", 'I', "ingotIron", 'T', "ingotTin");
		addShapedOreRecipe(new ItemStack(NCItems.part, 1, 12), "STS", "TBT", "STS", 'S', "ingotSteel", 'B', "ingotBronze", 'T', "ingotTough");
		addShapedOreRecipe(new ItemStack(NCItems.part, 8, 14), "PSP", "T T", "PSP", 'P', "plateAdvanced", 'S', "ingotSteel", 'T', "ingotTough");
		
		addShapedOreRecipe(new ItemStack(NCItems.upgrade, 1, 0), "LRL", "RPR", "LRL", 'L', "gemLapis", 'R', "dustRedstone", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
		addShapedOreRecipe(new ItemStack(NCItems.upgrade, 1, 1), "OQO", "QPQ", "OQO", 'O', "dustObsidian", 'Q', "dustQuartz", 'P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		addShapedOreRecipe(new ItemStack(NCItems.upgrade, 1, 1), "OQO", "QPQ", "OQO", 'O', "dustObsidian", 'Q', "dustNetherQuartz", 'P', Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		
		tools("ingotBoron", NCTools.sword_boron, NCTools.pickaxe_boron, NCTools.shovel_boron, NCTools.axe_boron, NCTools.hoe_boron, NCTools.spaxelhoe_boron);
		tools("ingotTough", NCTools.sword_tough, NCTools.pickaxe_tough, NCTools.shovel_tough, NCTools.axe_tough, NCTools.hoe_tough, NCTools.spaxelhoe_tough);
		tools("ingotHardCarbon", NCTools.sword_hard_carbon, NCTools.pickaxe_hard_carbon, NCTools.shovel_hard_carbon, NCTools.axe_hard_carbon, NCTools.hoe_hard_carbon, NCTools.spaxelhoe_hard_carbon);
		tools("gemBoronNitride", NCTools.sword_boron_nitride, NCTools.pickaxe_boron_nitride, NCTools.shovel_boron_nitride, NCTools.axe_boron_nitride, NCTools.hoe_boron_nitride, NCTools.spaxelhoe_boron_nitride);
		
		armor("ingotBoron", NCArmor.helm_boron, NCArmor.chest_boron, NCArmor.legs_boron, NCArmor.boots_boron);
		armor("ingotTough", NCArmor.helm_tough, NCArmor.chest_tough, NCArmor.legs_tough, NCArmor.boots_tough);
		armor("ingotHardCarbon", NCArmor.helm_hard_carbon, NCArmor.chest_hard_carbon, NCArmor.legs_hard_carbon, NCArmor.boots_hard_carbon);
		armor("gemBoronNitride", NCArmor.helm_boron_nitride, NCArmor.chest_boron_nitride, NCArmor.legs_boron_nitride, NCArmor.boots_boron_nitride);
		
		fissionFuelRecipes("Uranium", NCItems.pellet_uranium, NCItems.fuel_uranium, 238, 233, 235);
		fissionFuelRecipes("Neptunium", NCItems.pellet_neptunium, NCItems.fuel_neptunium, 237, 236);
		fissionFuelRecipes("Plutonium", NCItems.pellet_plutonium, NCItems.fuel_plutonium, 242, 239, 241);
		fissionFuelLowEnrichedRecipeAll(NCItems.pellet_mixed, 0, "Uranium238", "Plutonium239", new int[] {0, 1}, "", "Carbide");
		fissionFuelLowEnrichedRecipeAll(NCItems.fuel_mixed, 0, "Uranium238", "Plutonium239", new int[] {1, 2, 3}, "Oxide", "Nitride", "ZA");
		fissionFuelLowEnrichedRecipeAll(NCItems.pellet_mixed, 2, "Uranium238", "Plutonium241", new int[] {0, 1}, "", "Carbide");
		fissionFuelLowEnrichedRecipeAll(NCItems.fuel_mixed, 4, "Uranium238", "Plutonium241", new int[] {1, 2, 3}, "Oxide", "Nitride", "ZA");
		fissionFuelRecipes("Americium", NCItems.pellet_americium, NCItems.fuel_americium, 243, 242);
		fissionFuelRecipes("Curium", NCItems.pellet_curium, NCItems.fuel_curium, 246, 243, 245, 247);
		fissionFuelRecipes("Berkelium", NCItems.pellet_berkelium, NCItems.fuel_berkelium, 247, 248);
		fissionFuelRecipes("Californium", NCItems.pellet_californium, NCItems.fuel_californium, 252, 249, 251);
		
		addShapelessOreRecipe(new ItemStack(NCItems.compound, 2, 1), "dustRhodochrosite", "dustCalciumSulfate", "dustObsidian", "dustMagnesium");
		addShapelessOreRecipe(new ItemStack(NCItems.compound, 2, 2), "dustRedstone", "dustGlowstone");
		addShapelessOreRecipe(new ItemStack(NCItems.compound, 2, 9), "dustObsidian", "dustObsidian", "dustObsidian", "dustObsidian", "dustEndstone");
		addShapelessOreRecipe(new ItemStack(NCItems.compound, 2, 10), "dustGraphite", "dustManganese");
		
		addShapedOreRecipe(NCItems.portable_ender_chest, " S ", "WCW", "LWL", 'C', "chestEnder", 'W', new ItemStack(Blocks.WOOL, 1, 10), 'S', "string", 'L', "ingotTough");
		addShapedOreRecipe(NCItems.portable_ender_chest, " S ", "WCW", "LWL", 'C', "chestEnder", 'W', new ItemStack(Blocks.WOOL, 1, 15), 'S', "string", 'L', "ingotTough");
		
		addShapelessOreRecipe(new ItemStack(NCItems.dominos, 4), Items.BREAD, Items.BREAD, Items.BREAD, Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM);
		addShapelessOreRecipe(Blocks.BROWN_MUSHROOM, NCBlocks.glowing_mushroom);
		addShapelessOreRecipe(NCBlocks.glowing_mushroom, Blocks.BROWN_MUSHROOM, "dustGlowstone");
		
		addShapedOreRecipe(new ItemStack(Items.COOKIE, 8), "FCF", 'F', "dustWheat", 'C', "dustCocoa");
		addShapelessOreRecipe(NCItems.smore, NCItems.graham_cracker, "ingotChocolate", "ingotMarshmallow", NCItems.graham_cracker);
		addShapelessOreRecipe(NCItems.moresmore, NCItems.smore, "ingotChocolate", "ingotMarshmallow", NCItems.smore);
		addShapelessOreRecipe(NCItems.foursmore, NCItems.moresmore, "ingotChocolate", "ingotMarshmallow", NCItems.moresmore);
		
		addShapedOreRecipe(NCItems.geiger_counter, "SFF", "CRR", "BFF", 'S', "ingotSteel", 'F', "ingotFerroboron", 'C', "ingotCopper", 'R', "dustRedstone", 'B', "bioplastic");
		addShapedOreRecipe(NCItems.radiation_badge, " C ", "SRS", " L ", 'C', "ingotCopper", 'S', "string", 'R', "dustRedstone", 'L', "ingotLead");
		
		addShapedOreRecipe(NCItems.rad_x, "EPE", "PRP", "PBP", 'E', "dustEnergetic", 'P', "bioplastic", 'R', NCItems.radaway, 'B', Items.BLAZE_POWDER);
		
		addShapedOreRecipe(NCBlocks.radiation_scrubber, "PCP", "CEC", "PCP", 'P', "plateElite", 'E', "ingotExtreme", 'C', "dustBorax");
		
		addShapedOreRecipe(NCBlocks.geiger_block, " P ", "PGP", " P ", 'P', "plateBasic", 'G', NCItems.geiger_counter);
		
		addShapedOreRecipe(new ItemStack(NCItems.rad_shielding, 1, 0), "III", "CCC", "LLL", 'I', "ingotIron", 'C', "coal", 'L', "ingotLead");
		addShapedOreRecipe(new ItemStack(NCItems.rad_shielding, 1, 1), "BBB", "RFR", "PPP", 'B', "bioplastic", 'F', "ingotFerroboron", 'P', "plateBasic", 'R', new ItemStack(NCItems.rad_shielding, 1, 0));
		addShapedOreRecipe(new ItemStack(NCItems.rad_shielding, 1, 2), "BBB", "RHR", "PPP", 'B', "ingotBeryllium", 'H', "ingotHardCarbon", 'P', "plateDU", 'R', new ItemStack(NCItems.rad_shielding, 1, 1));
		
		addShapedOreRecipe(ItemMultitool.newMultitool(NCItems.multitool), " F ", "HSF", "SB ", 'F', "ingotFerroboron", 'H', "ingotHardCarbon", 'S', "ingotSteel", 'B', "ingotBronze");
		
		addShapelessOreRecipe(NCItems.record_wanderer, "record", "ingotTough");
		addShapelessOreRecipe(NCItems.record_end_of_the_world, "record", "ingotUranium235");
		addShapelessOreRecipe(NCItems.record_money_for_nothing, "record", "ingotSilver");
		addShapelessOreRecipe(NCItems.record_hyperspace, "record", "dustDimensional");
		
		addShapedOreRecipe(NCArmor.helm_hazmat, "YWY", "SLS", "BIB", 'Y', "dyeYellow", 'W', "wool", 'L', Items.LEATHER_HELMET, 'B', "bioplastic", 'I', "ingotSteel", 'S', new ItemStack(NCItems.rad_shielding, 1, 2));
		addShapedOreRecipe(NCArmor.chest_hazmat, "WSW", "YLY", "SWS", 'Y', "dyeYellow", 'W', "wool", 'L', Items.LEATHER_CHESTPLATE, 'S', new ItemStack(NCItems.rad_shielding, 1, 2));
		addShapedOreRecipe(NCArmor.legs_hazmat, "YBY", "SLS", "W W", 'Y', "dyeYellow", 'W', "wool", 'L', Items.LEATHER_LEGGINGS, 'B', "bioplastic", 'S', new ItemStack(NCItems.rad_shielding, 1, 2));
		addShapedOreRecipe(NCArmor.boots_hazmat, "SDS", "BLB", 'D', "dyeBlack", 'L', Items.LEATHER_BOOTS, 'B', "bioplastic", 'S', new ItemStack(NCItems.rad_shielding, 1, 2));
		
		if (register_quantum) {
			addShapedOreRecipe(NCBlocks.quantum_computer_controller, "EPE", "PFP", "EPE", 'E', "ingotExtreme", 'P', Items.ENDER_PEARL, 'F', "steelFrame");
			addShapedOreRecipe(NCBlocks.quantum_computer_qubit, "ESE", "PRP", "ESE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL, 'R', "blockRedstone");
			
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 0), "SES", "EPE", "SES", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 1), "SES", "EPE", "ESE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 2), "SSS", "EPE", "SSS", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 3), "SES", "SPS", "SES", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 4), "ESS", "EPE", "SSE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 6), "SSS", "EPE", "ESE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 8), "SSS", "SPS", "SEE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 5), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 4));
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 4), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 5));
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 7), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 6));
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 6), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 7));
			
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 9), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 0), Blocks.REDSTONE_TORCH);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 10), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 1), Blocks.REDSTONE_TORCH);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 11), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, 2), Blocks.REDSTONE_TORCH);
			
			for (int i = 0; i < QuantumGateEnums.SingleType.values().length; ++i) {
				addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, i), new ItemStack(NCBlocks.quantum_computer_gate_single, 1, i), "dustEnergetic");
			}
			
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 4), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 5));
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 6), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 7));
			
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 9), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 0), Blocks.REDSTONE_TORCH);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 10), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 1), Blocks.REDSTONE_TORCH);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 11), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, 2), Blocks.REDSTONE_TORCH);
			
			for (int i : new int[] {0, 1, 2, 3, 4, 6, 8, 9, 10, 11}) {
				addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_single, 1, i), new ItemStack(NCBlocks.quantum_computer_gate_control, 1, i));
			}
			
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_swap, 1, 0), "EES", "EPE", "SEE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_swap, 1, 1), new ItemStack(NCBlocks.quantum_computer_gate_swap, 1, 0), "dustEnergetic");
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_gate_swap, 1, 0), new ItemStack(NCBlocks.quantum_computer_gate_swap, 1, 1));
			
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_connector, 8), "ESE", "S S", "ESE", 'E', "ingotExtreme", 'S', "ingotSteel");
			
			addShapedOreRecipe(new ItemStack(NCBlocks.quantum_computer_code_generator, 1, 0), "ESE", "PBP", "ESE", 'E', "ingotExtreme", 'S', "ingotSteel", 'P', Items.ENDER_PEARL, 'B', Items.WRITABLE_BOOK);
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_code_generator, 1, 0), new ItemStack(NCBlocks.quantum_computer_code_generator, 1, 1));
			addShapelessOreRecipe(new ItemStack(NCBlocks.quantum_computer_code_generator, 1, 1), new ItemStack(NCBlocks.quantum_computer_code_generator, 1, 0));
		}
	}
	
	public static void registerRadShieldingCraftingRecipes() {
		if (radiation_shielding_default_recipes) {
			for (Item item : ForgeRegistries.ITEMS.getValuesCollection()) {
				if (ArmorHelper.isArmor(item, radiation_horse_armor_public)) {
					NonNullList<ItemStack> stacks = new NonNullList<>(new ArrayList<>(), ItemStack.EMPTY);
					item.getSubItems(CreativeTabs.SEARCH, stacks);
					for (ItemStack stack : stacks) {
						int packed = RecipeItemHelper.pack(stack);
						if (!RadArmor.ARMOR_STACK_SHIELDING_BLACKLIST.contains(packed)) {
							RadArmor.addArmorShieldingRecipes(stack);
						}
					}
				}
			}
		}
		
		for (int packed : RadArmor.ARMOR_STACK_SHIELDING_LIST) {
			RadArmor.addArmorShieldingRecipes(RecipeItemHelper.unpack(packed));
		}
	}
	
	public static <T extends Enum<T> & IStringSerializable & IMetaEnum> void ingotBlockRecipes(Class<T> enumm, Block block, Item ingot) {
		T[] values = enumm.getEnumConstants();
		for (int i = 0, len = values.length; i < len; ++i) {
			String type = StringHelper.capitalize(values[i].getName());
			if (!ore_dict_raw_material_recipes) {
				blockCompress(block, i, "block" + type, new ItemStack(ingot, 1, i));
			}
			else {
				for (ItemStack ingotStack : OreDictionary.getOres("ingot" + type, false)) {
					blockCompress(block, i, "block" + type, ingotStack);
				}
			}
			
			if (!ore_dict_raw_material_recipes) {
				blockOpen(ingot, i, "ingot" + type, new ItemStack(block, 1, i));
			}
			else {
				for (ItemStack blockStack : OreDictionary.getOres("block" + type, false)) {
					blockOpen(ingot, i, "ingot" + type, blockStack);
				}
			}
		}
	}
	
	public static void materialBlockRecipes(int meta, String suffix) {
		String ingot = "ingot" + suffix, block = "block" + suffix;
		if (OreDictHelper.oreExists(ingot)) {
			blockCompress(NCBlocks.material_block, meta, block, ingot);
			addShapelessOreRecipe(OreDictHelper.getPrioritisedCraftingStack(ItemStack.EMPTY, ingot, 9), block);
		}
	}
	
	public static void fissionFuelRecipes(String element, Item pellet, Item fuel, int fertileNo, int... fissileNo) {
		for (int i = 0; i < fissileNo.length; ++i) {
			fissionFuelLowEnrichedRecipeAll(pellet, 4 * i, element + fertileNo, element + fissileNo[i], new int[] {0, 1}, "", "Carbide");
			fissionFuelLowEnrichedRecipeAll(fuel, 8 * i, element + fertileNo, element + fissileNo[i], new int[] {1, 2, 3}, "Oxide", "Nitride", "ZA");
			fissionFuelHighlyEnrichedRecipeAll(pellet, 4 * i + 2, element + fertileNo, element + fissileNo[i], new int[] {0, 1}, "", "Carbide");
			fissionFuelHighlyEnrichedRecipeAll(fuel, 8 * i + 4, element + fertileNo, element + fissileNo[i], new int[] {1, 2, 3}, "Oxide", "Nitride", "ZA");
		}
	}
	
	public static void fissionFuelLowEnrichedRecipeAll(Item fuel, int startMeta, String fertile, String fissile, int[] offsets, String... types) {
		for (int i = 0; i < offsets.length; ++i) {
			fissionFuelLowEnrichedRecipe(fuel, startMeta + offsets[i], fertile + types[i], fissile + types[i]);
		}
	}
	
	public static void fissionFuelHighlyEnrichedRecipeAll(Item fuel, int startMeta, String fertile, String fissile, int[] offsets, String... types) {
		for (int i = 0; i < offsets.length; ++i) {
			fissionFuelHighlyEnrichedRecipe(fuel, startMeta + offsets[i], fertile + types[i], fissile + types[i]);
		}
	}
	
	public static void fissionFuelLowEnrichedRecipe(Item fuel, int meta, String fertile, String fissile) {
		fertile = "ingot" + fertile;
		fissile = "ingot" + fissile;
		addShapelessOreRecipe(new ItemStack(fuel, 9, meta), fissile, fertile, fertile, fertile, fertile, fertile, fertile, fertile, fertile);
	}
	
	public static void fissionFuelHighlyEnrichedRecipe(Item fuel, int meta, String fertile, String fissile) {
		fertile = "ingot" + fertile;
		fissile = "ingot" + fissile;
		addShapelessOreRecipe(new ItemStack(fuel, 9, meta), fissile, fissile, fissile, fertile, fertile, fertile, fertile, fertile, fertile);
	}
	
	public static void blockCompress(Block blockOut, int metaOut, String itemOutOreName, Object itemIn) {
		addShapedOreRecipe(OreDictHelper.getPrioritisedCraftingStack(new ItemStack(blockOut, 1, metaOut), itemOutOreName), "III", "III", "III", 'I', itemIn);
	}
	
	public static void blockOpen(Item itemOut, int metaOut, String itemOutOreName, Object itemIn) {
		addShapelessOreRecipe(OreDictHelper.getPrioritisedCraftingStack(new ItemStack(itemOut, 9, metaOut), itemOutOreName), itemIn);
	}
	
	public static void tools(Object material, Item sword, Item pick, Item shovel, Item axe, Item hoe, Item spaxelhoe) {
		addShapedOreRecipe(sword, "M", "M", "S", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(pick, "MMM", " S ", " S ", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(shovel, "M", "S", "S", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(axe, "MM", "MS", " S", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(axe, "MM", "SM", "S ", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(hoe, "MM", " S", " S", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(hoe, "MM", "S ", "S ", 'M', material, 'S', "stickWood");
		addShapedOreRecipe(spaxelhoe, "ASP", "HIW", " I ", 'A', axe, 'S', shovel, 'P', pick, 'H', hoe, 'W', sword, 'I', "ingotIron");
	}
	
	public static void armor(Object material, Item helm, Item chest, Item legs, Item boots) {
		addShapedOreRecipe(helm, "MMM", "M M", 'M', material);
		addShapedOreRecipe(chest, "M M", "MMM", "MMM", 'M', material);
		addShapedOreRecipe(legs, "MMM", "M M", "M M", 'M', material);
		addShapedOreRecipe(boots, "M M", "M M", 'M', material);
	}
	
	private static final Object2IntMap<String> RECIPE_COUNT_MAP = new Object2IntOpenHashMap<>();
	
	public static void addShapedOreRecipe(Object out, Object... inputs) {
		registerRecipe(ShapedOreRecipe::new, out, inputs);
	}
	
	public static void addShapedEnergyRecipe(Object out, Object... inputs) {
		registerRecipe(ShapedEnergyRecipe::new, out, inputs);
	}
	
	public static void addShapedFluidRecipe(Object out, Object... inputs) {
		registerRecipe(ShapedFluidRecipe::new, out, inputs);
	}
	
	public static void addShapelessOreRecipe(Object out, Object... inputs) {
		registerRecipe(ShapelessOreRecipe::new, out, inputs);
	}
	
	public static void addShapelessFluidRecipe(Object out, Object... inputs) {
		registerRecipe(ShapelessFluidRecipe::new, out, inputs);
	}
	
	public static void addShapelessArmorUpgradeRecipe(Object out, Object... inputs) {
		registerRecipe(ShapelessArmorRadShieldingRecipe::new, out, inputs);
	}
	
	public static <T extends IRecipe> void registerRecipe(RecipeSupplier<T> supplier, Object out, Object... inputs) {
		if (out == null || Lists.newArrayList(inputs).contains(null)) {
			return;
		}
		ItemStack outStack = StackHelper.fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null) {
			String outName = StackHelper.stackPath(outStack);
			if (RECIPE_COUNT_MAP.containsKey(outName)) {
				int count = RECIPE_COUNT_MAP.getInt(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;
			}
			else {
				RECIPE_COUNT_MAP.put(outName, 1);
			}
			ResourceLocation location = new ResourceLocation(Global.MOD_ID, outName);
			IRecipe recipe = supplier.get(location, outStack, inputs);
			recipe.setRegistryName(location);
			ForgeRegistries.RECIPES.register(recipe);
		}
	}
}
