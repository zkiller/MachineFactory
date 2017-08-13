package com.worldofazos.machinefactory.jei;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.blocks.Crusher.CrusherGUI;
import com.worldofazos.machinefactory.blocks.ModBlocks;
import com.worldofazos.machinefactory.blocks.Smelter.SmelterGUI;
import com.worldofazos.machinefactory.blocks.WorkBench.GuiWorkdBench;
import com.worldofazos.machinefactory.blocks.WorkBench.WorkBenchContainer;
import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkBenchManager;
import com.worldofazos.machinefactory.jei.crusher.CrusherRecipeCategory;
import com.worldofazos.machinefactory.jei.crusher.CrusherRecipeHandler;
import com.worldofazos.machinefactory.jei.crusher.CrusherRecipeMaker;
import com.worldofazos.machinefactory.jei.smelter.SmelterRecipeCategory;
import com.worldofazos.machinefactory.jei.smelter.SmelterRecipeHandler;
import com.worldofazos.machinefactory.jei.smelter.SmelterRecipeMaker;
import com.worldofazos.machinefactory.jei.workbench.WorkbenchRecipeCategory;
import com.worldofazos.machinefactory.jei.workbench.WorkbenchShapedRecipeHandler;
import com.worldofazos.machinefactory.jei.workbench.WorkbenchShapelessRecipeHandler;
import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ModJEIPlugin extends BlankModPlugin {

    public static IRecipeRegistry recipeRegistry;

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        // Hardcore Workbench

        registry.addRecipeCategories(
                new WorkbenchRecipeCategory(guiHelper)
        );

        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.hardcoreWorkdbench), Infos.WORKBENCH_ID);

        registry.addRecipeHandlers(
                new WorkbenchShapedRecipeHandler(jeiHelpers),
                new WorkbenchShapelessRecipeHandler(jeiHelpers)
        );

        registry.addRecipeClickArea(GuiWorkdBench.class, 214,65,5, 5, Infos.WORKBENCH_ID);

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(WorkBenchContainer.class, Infos.WORKBENCH_ID, 1, 81, 82, 36);

        registry.addRecipes(WorkBenchManager.getInstance().getRecipeList());

        // Crusher
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new CrusherRecipeHandler());
        registry.addRecipes(CrusherRecipeMaker.getCrusherRecipes(jeiHelpers));
        registry.addRecipeClickArea(CrusherGUI.class, 77, 32, 22, 15, Infos.CRUSHER_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.Crusher), Infos.CRUSHER_ID);

        // Smelter
        registry.addRecipeCategories(new SmelterRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new SmelterRecipeHandler());
        registry.addRecipes(SmelterRecipeMaker.getSmelterRecipes(jeiHelpers));
        registry.addRecipeClickArea(SmelterGUI.class, 87, 32, 22, 15, Infos.SMELTER_ID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.Smelter), Infos.SMELTER_ID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

}
