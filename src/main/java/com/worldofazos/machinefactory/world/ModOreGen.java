package com.worldofazos.machinefactory.world;

import com.worldofazos.machinefactory.blocks.ModBlocks;
import com.worldofazos.machinefactory.config.ModConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModOreGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if(ModConfig.oreGenAll) {

            int dimension = world.provider.getDimension();

            switch (dimension) {
                case 0: // Overworld
                    generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                    break;
                case 1: // The End
                    generateTheEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                    break;
                case -1: // Nether
                    generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                default:
                    break;

            }

        }

    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if(ModConfig.oreGenOverworld) {
            if (ModConfig.oreGenPlatine) {
                generateOre(ModBlocks.orePlatine.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 4 + random.nextInt(4), 6);
            }
        }

    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if(ModConfig.oreGenNether) {
            if (ModConfig.oreGenPlatine) {
                generateOre(ModBlocks.orePlatine.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 60, 64, 4 + random.nextInt(4), 6);
            }
        }

    }

    private void generateTheEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if(ModConfig.oreGenTheEnd) {
            if (ModConfig.oreGenPlatine) {
                generateOre(ModBlocks.orePlatine.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 60, 64, 4 + random.nextInt(4), 6);
            }
        }

    }

    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
        int deltaY = maxY - minY;

        int dimension = world.provider.getDimension();

        for (int i = 0; i < chances; i++) {
            BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
            ModWorldGenMinable generator = new ModWorldGenMinable(ore, size, dimension);
            generator.generate(world, random, pos);
        }
    }


}
