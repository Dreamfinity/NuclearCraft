package nc.recipe.other;

import nc.init.NCBlocks;
import nc.recipe.BasicRecipeHandler;
import nc.util.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.List;

import static nc.config.NCConfig.*;

public class CollectorRecipes extends BasicRecipeHandler {
	
	public CollectorRecipes() {
		super("collector", 1, 0, 1, 1);
	}
	
	@Override
	public void addRecipes() {
		if (register_passive[0]) {
			addRecipe(NCBlocks.cobblestone_generator, new ItemStack(Blocks.COBBLESTONE), emptyFluidStack(), NCMath.sigFigs(processor_passive_rate[0], 5) + " C/t");
			addRecipe(NCBlocks.cobblestone_generator_compact, new ItemStack(Blocks.COBBLESTONE), emptyFluidStack(), NCMath.sigFigs(processor_passive_rate[0] * 8, 5) + " C/t");
			addRecipe(NCBlocks.cobblestone_generator_dense, new ItemStack(Blocks.COBBLESTONE), emptyFluidStack(), NCMath.sigFigs(processor_passive_rate[0] * 64, 5) + " C/t");
		}
		
		if (register_passive[1]) {
			addRecipe(NCBlocks.water_source, emptyItemStack(), fluidStack("water", 1000), UnitHelper.prefix(processor_passive_rate[1], 5, "B/t", -1));
			addRecipe(NCBlocks.water_source_compact, emptyItemStack(), fluidStack("water", 1000), UnitHelper.prefix(processor_passive_rate[1] * 8, 5, "B/t", -1));
			addRecipe(NCBlocks.water_source_dense, emptyItemStack(), fluidStack("water", 1000), UnitHelper.prefix(processor_passive_rate[1] * 64, 5, "B/t", -1));
		}
		
		if (register_passive[2]) {
			addRecipe(NCBlocks.nitrogen_collector, emptyItemStack(), fluidStack("nitrogen", 1000), UnitHelper.prefix(processor_passive_rate[2], 5, "B/t", -1));
			addRecipe(NCBlocks.nitrogen_collector_compact, emptyItemStack(), fluidStack("nitrogen", 1000), UnitHelper.prefix(processor_passive_rate[2] * 8, 5, "B/t", -1));
			addRecipe(NCBlocks.nitrogen_collector_dense, emptyItemStack(), fluidStack("nitrogen", 1000), UnitHelper.prefix(processor_passive_rate[2] * 64, 5, "B/t", -1));
		}
	}
	
	@Override
	public List<Object> fixedExtras(List<Object> extras) {
		ExtrasFixer fixer = new ExtrasFixer(extras);
		fixer.add(String.class, null);
		return fixer.fixed;
	}
}
