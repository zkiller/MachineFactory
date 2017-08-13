package com.worldofazos.machinefactory.recipes;

import com.worldofazos.machinefactory.blocks.ModBlocks;
import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkBenchManager;
import com.worldofazos.machinefactory.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        // Crafting
        GameRegistry.addShapedRecipe(new ItemStack(Items.RABBIT_STEW), " R ", "CPM", " B ", 'R', Items.COOKED_RABBIT, 'C', Items.WHEAT, 'P', Items.BAKED_POTATO, 'M', Blocks.BROWN_MUSHROOM, 'B', Items.BOWL);

        // Smelting

        FurnaceRecipes.instance().addSmelting(ModItems.dustIron, new ItemStack(Items.IRON_INGOT, 1), 1.0F);
        FurnaceRecipes.instance().addSmelting(ModItems.dustGold, new ItemStack(Items.GOLD_INGOT, 1), 1.0F);
        FurnaceRecipes.instance().addSmelting(ModItems.dustDiamond, new ItemStack(Items.DIAMOND, 1), 1.0F);
        FurnaceRecipes.instance().addSmelting(ModItems.dustCoal, new ItemStack(Items.COAL, 1), 1.0F);

        FurnaceRecipes.instance().addSmeltingRecipeForBlock(ModBlocks.orePlatine, new ItemStack(ModItems.ingotPlatine, 1), 1.0F);
        FurnaceRecipes.instance().addSmelting(ModItems.dustPlatine, new ItemStack(ModItems.ingotPlatine, 1), 1.0F);

        FurnaceRecipes.instance().addSmeltingRecipeForBlock(ModBlocks.oreThorium, new ItemStack(ModItems.ingotThorium, 1), 1.0F);
        FurnaceRecipes.instance().addSmelting(ModItems.dustThorium, new ItemStack(ModItems.ingotThorium, 1), 1.0F);

    }

    public static void postInit() {

        // Test
        WorkBenchManager.getInstance().addShapedOreRecipe(new ItemStack(Items.NETHER_STAR, 64),
                "EDBEDBEDB",
                "BDEBDEBDE",
                "EDBEDBEDB",
                "BDEBDEBDE",
                "EDBEDBEDB",
                "BDEBDEBDE",
                "EDBEDBEDB",
                "BDEBDEBDE",
                "EDBEDBEDB",
                'E', Items.EMERALD,
                'D', Items.DIAMOND,
                'B', Blocks.DIAMOND_BLOCK

        );
/*
        if(Loader.isModLoaded("thermalexpansion")) {
            WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeEnergyCell,
                    "III      ",
                    'I', Items.NETHER_STAR
            );
        }
*/
/*
        // Creative Fluid Source
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeSource,
                "SSSSSSSSS",
                "SGGGGGGGS",
                "SGGCCCGGS",
                "SGCDEDCGS",
                "SGCEDEGGS",
                "SGCDEDCGS",
                "SGGCCCGGS",
                "SGGGGGGGS",
                "SSSSSSSSS",

                'S', ModItems.steelBlock,
                'G', ModItems.enhancedGaldadorianBlock,
                'C', ModItems.creativeFluidStorageBlock,
                'D', ModItems.darkSteelBlock,
                'E', ModItems.electricalSteelBlock
        );

        // Creative Energy Cell
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeEnergyCell,
                "LGGGGGGGL",
                "GTVVCVVTG",
                "GVCCTCCVG",
                "GVCAAACVG",
                "GCTAPATCG",
                "GVCAAACVG",
                "GVCCTCCVG",
                "GTVVCVVTG",
                "LGGGGGGGL",

                'G', ModItems.enhancedGaldadorianBlock,
                'L', ModItems.lumiumBlock,
                'T', ModItems.terrasteelBlock,
                'V', ModItems.vibrantCapacitorBank,
                'A', ModItems.awakenedDraconiumTurbine,
                'P', ModItems.controllerSolarT4,
                'C', ModItems.draconicFluxCapacitor

        );

        // Creative Modifier Tinker
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeModifier,
                "GGGGGGGGG",
                "GGGGGGGGG",
                "GGIIGIIGG",
                "GGIIGIIGG",
                "GGGGGGGGG",
                "GGGGIGGGG",
                "GGIGGGIGG",
                "GGIIIIIGG",
                "GGGGGGGGG",

                'G', Blocks.GOLD_BLOCK,
                'I', ModItems.witherGold
        );

        // Creative Storage Item Refined
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeStorageBlock,
                "QQQQQQQQQ",
                "QSCTTTCSQ",
                "QCPPPPPCQ",
                "QTPOOOPTQ",
                "QTPOHOPTQ",
                "QTPOOOPTQ",
                "QCPPPPPCQ",
                "QSCTTTCSQ",
                "QQQQQQQQQ",

                'Q', ModItems.quantumStorage,
                'S', ModItems.darkSteelBlock,
                'C', ModItems.largeStorageCrate,
                'T', ModItems.basaltStructureT4,
                'P', ModItems.storagePart64k,
                'O', ModItems.opiniumCorePerfected,
                'H', ModItems.blackHoleTalisman
        );

        // Creative Storage Fluid Refined
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeFluidStorageBlock,
                "QQQQQQQQQ",
                "QSCTTTCSQ",
                "QCPPPPPCQ",
                "QTPOOOPTQ",
                "QTPOHOPTQ",
                "QTPOOOPTQ",
                "QCPPPPPCQ",
                "QSCTTTCSQ",
                "QQQQQQQQQ",

                'Q', ModItems.quantumTank,
                'S', ModItems.darkSteelBlock,
                'C', ModItems.pressurizedFluidTank,
                'T', ModItems.basaltStructureT4,
                'P', ModItems.fluidStoragePart512k,
                'O', ModItems.opiniumCorePerfected,
                'H', ModItems.blackHoleTalisman
        );

        // Creative Mana Pool
        WorkBenchManager.getInstance().addShapedOreRecipe(ModItems.creativeManaPool,
                "         ",
                "         ",
                "    B    ",
                "         ",
                "SS     SS",
                "SG  T  GS",
                "SGIIHIIGS",
                "SGGGLGGGS",
                "SSSSSSSSS",

                'S', ModItems.shimmerrock,
                'G', ModItems.gaiaSpiritIngot,
                'B', ModItems.manaBottle,
                'T', ModItems.transcendentBloodOrb,
                'H', ModItems.shiftingStar,
                'L', ModItems.laputaShardXX,
                'I', Items.NETHER_STAR
        );

*/

    }

}
