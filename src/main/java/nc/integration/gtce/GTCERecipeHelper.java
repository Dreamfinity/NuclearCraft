package nc.integration.gtce;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.*;
import gregtech.api.recipes.ingredients.*;
import gregtech.common.items.MetaItems;
import nc.config.NCConfig;
import nc.recipe.*;
import nc.recipe.ingredient.*;
import nc.util.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GTCERecipeHelper {
	
	// Thanks so much to Firew0lf for the original method!
	@Optional.Method(modid = "gregtech")
	public static void addGTCERecipe(String recipeName, ProcessorRecipe recipe) {
		RecipeMap<?> recipeMap = null;
		RecipeBuilder<?> builder = null;
		
		switch (recipeName) {
		case "manufactory":
			recipeMap = RecipeMaps.MACERATOR_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 12, 8);
			break;
		case "isotope_separator":
			recipeMap = RecipeMaps.THERMAL_CENTRIFUGE_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 48, 160);
			break;
		case "decay_hastener":
			return;
		case "fuel_reprocessor":
			recipeMap = RecipeMaps.CENTRIFUGE_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 24, 60);
			break;
		case "alloy_furnace":
			recipeMap = RecipeMaps.ALLOY_SMELTER_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 16, 10);
			break;
		case "infuser":
			recipeMap = RecipeMaps.CHEMICAL_BATH_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 16, 12);
			break;
		case "melter":
			recipeMap = RecipeMaps.EXTRACTOR_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 32, 16);
			break;
		case "supercooler":
			recipeMap = RecipeMaps.VACUUM_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 240, 20);
			break;
		case "electrolyser":
			recipeMap = RecipeMaps.ELECTROLYZER_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 30, 16);
			break;
		case "irradiator":
			return;
		case "ingot_former":
			recipeMap = RecipeMaps.FLUID_SOLIDFICATION_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 8, 1);
			break;
		case "pressurizer":
			if (isPlateRecipe(recipe)) {
				recipeMap = RecipeMaps.BENDER_RECIPES;
				builder = addStats(recipeMap.recipeBuilder(), recipe, 24, 10).notConsumable(new IntCircuitIngredient(0));
			}
			else {
				recipeMap = RecipeMaps.COMPRESSOR_RECIPES;
				builder = addStats(recipeMap.recipeBuilder(), recipe, 2, 20);
			}
			break;
		case "chemical_reactor":
			recipeMap = RecipeMaps.CHEMICAL_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 30, 30);
			break;
		case "salt_mixer":
			recipeMap = RecipeMaps.MIXER_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 8, 12);
			break;
		case "crystallizer":
			recipeMap = RecipeMaps.CHEMICAL_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 30, 10).notConsumable(new IntCircuitIngredient(0));
			break;
		case "dissolver":
			recipeMap = RecipeMaps.CHEMICAL_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 20, 20).notConsumable(new IntCircuitIngredient(1));
			break;
		case "extractor":
			recipeMap = RecipeMaps.EXTRACTOR_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 16, 12);
			break;
		case "centrifuge":
			recipeMap = RecipeMaps.CENTRIFUGE_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 16, 80).notConsumable(new IntCircuitIngredient(0));
			break;
		case "rock_crusher":
			recipeMap = RecipeMaps.MACERATOR_RECIPES;
			builder = addStats(recipeMap.recipeBuilder(), recipe, 20, 12);
			break;
		}
		
		if (builder == null) {
			return;
		}
		
		List<List<ItemStack>> itemInputLists = new ArrayList<>();
		List<List<FluidStack>> fluidInputLists = new ArrayList<>();
		
		for (IItemIngredient item : recipe.itemIngredients()) itemInputLists.add(item.getInputStackList());
		for (IFluidIngredient fluid : recipe.fluidIngredients()) fluidInputLists.add(fluid.getInputStackList());
		
		int arrSize = recipe.itemIngredients().size() + recipe.fluidIngredients().size();
		int[] inputNumbers = new int[arrSize];
		Arrays.fill(inputNumbers, 0);
		
		int[] maxNumbers  = new int[arrSize];
		for (int i = 0; i < itemInputLists.size(); i++) {
			int maxNumber = itemInputLists.get(i).size() - 1;
			if (maxNumber < 0) return;
			maxNumbers[i] = maxNumber;
		}
		for (int i = 0; i < fluidInputLists.size(); i++) {
			int maxNumber = fluidInputLists.get(i).size() - 1;
			if (maxNumber < 0) return;
			maxNumbers[i + itemInputLists.size()] = maxNumber;
		}
		
		List<Pair<List<ItemStack>, List<FluidStack>>> materialListTuples = new ArrayList<>();
		
		RecipeTupleGenerator.INSTANCE.generateMaterialListTuples(materialListTuples, maxNumbers, inputNumbers, itemInputLists, fluidInputLists, true);
		
		for (Pair<List<ItemStack>, List<FluidStack>> materials : materialListTuples) {
			if (isRecipeInvalid(recipeMap, materials.getLeft(), materials.getRight())) {
				return;
			}
		}
		
		List<RecipeBuilder<?>> builders = new ArrayList<>(); // Holds all the recipe variants
		builders.add(builder);
		
		for (IItemIngredient input : recipe.itemIngredients()) {
			if (input instanceof OreIngredient) {
				for (RecipeBuilder<?> builderVariant : builders) {
					builderVariant.input(((OreIngredient)input).oreName, ((OreIngredient)input).stackSize);
				}
			}
			else {
				List<String> ingredientOreList = new ArrayList<>(); // Hold the different oreDict names
				List<RecipeBuilder<?>> newBuilders = new ArrayList<>();
				for (ItemStack inputVariant : input.getInputStackList()) {
					if(inputVariant.isEmpty()) continue;
					Set<String> variantOreList = OreDictHelper.getOreNames(inputVariant);
					
					if (!variantOreList.isEmpty()) { // This variant has oreDict entries
						if (new HashSet<>(ingredientOreList).containsAll(variantOreList)) {
							continue;
						}
						ingredientOreList.addAll(variantOreList);
						
						for (RecipeBuilder<?> recipeBuilder : builders) {
							newBuilders.add(recipeBuilder.copy().input(variantOreList.iterator().next(), inputVariant.getCount()));
						}
					}
					else {
						for (RecipeBuilder<?> recipeBuilder : builders) {
							newBuilders.add(recipeBuilder.copy().inputs(inputVariant));
						}
					}
				}
				builders = newBuilders;
			}
		}
		
		if (recipeMap == RecipeMaps.FLUID_SOLIDFICATION_RECIPES) {
			MetaItem<?>.MetaValueItem mold = getIngotFormerMold(recipe);
			for (RecipeBuilder<?> builderVariant : builders) {
				builderVariant.notConsumable(mold);
			}
		}
		
		for (IFluidIngredient input : recipe.fluidIngredients()) {
			if (input.getInputStackList().isEmpty()) continue;
			for (RecipeBuilder<?> builderVariant : builders) {
				builderVariant.fluidInputs(input.getInputStackList().get(0));
			}
		}
		
		for (IItemIngredient output : recipe.itemProducts()) {
			if (output instanceof ChanceItemIngredient) return;
			List<ItemStack> outputStackList = output.getOutputStackList();
			if (outputStackList.isEmpty()) continue;
			for (RecipeBuilder<?> builderVariant : builders) {
				builderVariant = builderVariant.outputs(outputStackList.get(0));
			}
		}
		
		for (IFluidIngredient output : recipe.fluidProducts()) {
			if (output instanceof ChanceFluidIngredient) return;
			List<FluidStack> outputStackList = output.getOutputStackList();
			if (outputStackList.isEmpty()) continue;
			for (RecipeBuilder<?> builderVariant : builders) {
				builderVariant.fluidOutputs(outputStackList.get(0));
			}
		}
		
		boolean built = false;
		for (RecipeBuilder<?> builderVariant : builders) {
			if (!builderVariant.getInputs().isEmpty() || !builderVariant.getFluidInputs().isEmpty()) {
				builderVariant.buildAndRegister();
				built = true;
			}
		}
		
		if (built && NCConfig.gtce_recipe_logging) {
			NCUtil.getLogger().info("Injected GTCE " + recipeMap.unlocalizedName + " recipe: " + RecipeHelper.getRecipeString(recipe));
		}
	}
	
	@Optional.Method(modid = "gregtech")
	private static RecipeBuilder<?> addStats(RecipeBuilder<?> builder, ProcessorRecipe recipe, int processPower, int processTime) {
		return builder.EUt(Math.max((int) recipe.getBaseProcessPower(processPower), 1)).duration((int) recipe.getBaseProcessTime(20D * processTime));
	}
	
	// GTCE recipe matching - modified from GTCE source
	
	@Optional.Method(modid = "gregtech")
	private static boolean isRecipeInvalid(RecipeMap<?> recipeMap, List<ItemStack> itemInputs, List<FluidStack> fluidInputs) {
		int itemInputCount = itemInputs.size(), fluidInputCount = fluidInputs.size();
		
		if (itemInputCount > recipeMap.getMaxInputs() || fluidInputCount > recipeMap.getMaxFluidInputs() || itemInputCount + fluidInputCount < 1) {
			return true;
		}
		
		return findRecipeInputConflict(recipeMap, itemInputs, fluidInputs);
	}
	
	@Optional.Method(modid = "gregtech")
	private static boolean findRecipeInputConflict(RecipeMap<?> recipeMap, List<ItemStack> itemInputs, List<FluidStack> fluidInputs) {
		for (Recipe recipe : recipeMap.getRecipeList()) {
			if (isRecipeInputConflict(recipe, itemInputs, fluidInputs)) {
				return true;
			}
		}
		return false;
	}
	
	@Optional.Method(modid = "gregtech")
	private static boolean isRecipeInputConflict(Recipe recipe, List<ItemStack> itemInputs, List<FluidStack> fluidInputs) {
		itemLoop:
		for (ItemStack input : itemInputs) {
			for (GTRecipeInput gtInput : recipe.getInputs()) {
				if (gtInput.acceptsStack(input)) {
					continue itemLoop;
				}
			}
			return false;
		}
		
		fluidLoop:
		for (FluidStack input : fluidInputs) {
			for (GTRecipeInput gtInput : recipe.getFluidInputs()) {
				if (gtInput.acceptsFluid(input)) {
					continue fluidLoop;
				}
			}
			return false;
		}
		
		return true;
	}
	
	private static boolean isPlateRecipe(ProcessorRecipe recipe) {
		ItemStack output = recipe.itemProducts().get(0).getStack();
		return output != null && OreDictHelper.hasOrePrefix(output, "plate", "plateDense");
	}
	
	@Optional.Method(modid = "gregtech")
	private static MetaItem<?>.MetaValueItem getIngotFormerMold(ProcessorRecipe recipe) {
		ItemStack output = recipe.itemProducts().get(0).getStack();
		if (output != null) {
			if (OreDictHelper.hasOrePrefix(output, "ingot")) {
				return MetaItems.SHAPE_MOLD_INGOT;
			}
			else if (OreDictHelper.hasOrePrefix(output, "block")) {
				return MetaItems.SHAPE_MOLD_BLOCK;
			}
		}
		return MetaItems.SHAPE_MOLD_BALL;
	}
}
