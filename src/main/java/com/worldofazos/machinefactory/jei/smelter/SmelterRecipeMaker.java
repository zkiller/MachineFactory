package com.worldofazos.machinefactory.jei.smelter;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmelterRecipeMaker {

    public static List<SmelterRecipeWrapper> getSmelterRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        FurnaceRecipes smelterRecipes = FurnaceRecipes.instance();
        Map<ItemStack, ItemStack> smeltingMap = smelterRecipes.getSmeltingList();

        List<SmelterRecipeWrapper> recipes = new ArrayList<SmelterRecipeWrapper>();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();

            List<ItemStack> inputs = stackHelper.getSubtypes(input);
            SmelterRecipeWrapper recipe = new SmelterRecipeWrapper(inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }

}