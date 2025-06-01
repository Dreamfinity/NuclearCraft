package nc.worldgen.ore;

import it.unimi.dsi.fastutil.ints.*;
import nc.block.BlockMeta;
import nc.enumm.MetaEnums;
import nc.init.NCBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static nc.config.NCConfig.*;

public class OreGenerator implements IWorldGenerator {
	
	protected static IntSet ore_dim_set;
	
	protected final WorldGenOre[] ores;
	
	public static class WorldGenOre extends WorldGenMinable {
		
		public final double countParameter;
		
		public WorldGenOre(int meta) {
			super(((BlockMeta<?>) NCBlocks.ore).getStateFromMeta(meta), (int) getCountParameter(ore_size[meta]), new UniversalOrePredicate());
			countParameter = getCountParameter(ore_size[meta]);
		}
		
		private static final double A = 0.853D, B = 0.148D, C = 3.551D;
		
		public static double getCountParameter(int count) {
			return count <= 0 ? 0D : (count <= 6 ? Math.pow(count + 4, 0.75D) : (Math.pow(count, 1D / C) - A) / B);
		}
		
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			float f = rand.nextFloat() * (float) Math.PI;
			double d0 = (float) (position.getX() + 8) + MathHelper.sin(f) * (float) countParameter / 8.0F;
			double d1 = (float) (position.getX() + 8) - MathHelper.sin(f) * (float) countParameter / 8.0F;
			double d2 = (float) (position.getZ() + 8) + MathHelper.cos(f) * (float) countParameter / 8.0F;
			double d3 = (float) (position.getZ() + 8) - MathHelper.cos(f) * (float) countParameter / 8.0F;
			double d4 = position.getY() + rand.nextInt(3) - 2;
			double d5 = position.getY() + rand.nextInt(3) - 2;
			
			for (int i = 0, count = (int) countParameter; i < count; ++i) {
				float f1 = (float) i / (float) countParameter;
				double d6 = d0 + (d1 - d0) * (double) f1;
				double d7 = d4 + (d5 - d4) * (double) f1;
				double d8 = d2 + (d3 - d2) * (double) f1;
				double d9 = rand.nextDouble() * countParameter / 16.0D;
				double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
				double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
				int j = MathHelper.floor(d6 - d10 / 2.0D);
				int k = MathHelper.floor(d7 - d11 / 2.0D);
				int l = MathHelper.floor(d8 - d10 / 2.0D);
				int i1 = MathHelper.floor(d6 + d10 / 2.0D);
				int j1 = MathHelper.floor(d7 + d11 / 2.0D);
				int k1 = MathHelper.floor(d8 + d10 / 2.0D);
				
				for (int l1 = j; l1 <= i1; ++l1) {
					double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);
					
					if (d12 * d12 < 1.0D) {
						for (int i2 = k; i2 <= j1; ++i2) {
							double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);
							
							if (d12 * d12 + d13 * d13 < 1.0D) {
								for (int j2 = l; j2 <= k1; ++j2) {
									double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);
									
									if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
										BlockPos blockpos = new BlockPos(l1, i2, j2);
										
										IBlockState state = worldIn.getBlockState(blockpos);
										if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, predicate)) {
											worldIn.setBlockState(blockpos, oreBlock, 2);
										}
									}
								}
							}
						}
					}
				}
			}
			
			return true;
		}
	}
	
	public OreGenerator() {
		if (ore_dim_set == null) {
			ore_dim_set = new IntOpenHashSet(ore_dims);
		}
		
		ores = new WorldGenOre[8];
		for (int i = 0; i < MetaEnums.OreType.values().length; ++i) {
			ores[i] = new WorldGenOre(i);
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (ore_dim_set.contains(world.provider.getDimension()) != ore_dims_list_type) {
			generateOres(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	protected void generateOres(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < MetaEnums.OreType.values().length; ++i) {
			if (ore_gen[i]) {
				generateOre(ores[i], world, random, chunkX, chunkZ, ore_rate[i], ore_min_height[i], ore_max_height[i]);
			}
		}
	}
	
	public static void generateOre(WorldGenOre generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight >= world.getHeight() || minHeight > maxHeight) {
			throw new IllegalArgumentException("Illegal height arguments!");
		}
		
		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; ++i) {
			int x = chunk_X * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunk_Z * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}
}
