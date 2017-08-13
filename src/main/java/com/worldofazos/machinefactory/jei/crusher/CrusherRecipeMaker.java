package com.worldofazos.machinefactory.jei.crusher;

import com.worldofazos.machinefactory.recipes.CrusherRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrusherRecipeMaker {

    public static List<CrusherRecipeWrapper> getCrusherRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        CrusherRecipes crusherRecipes = CrusherRecipes.instance();
        Map<ItemStack, ItemStack> smeltingMap = crusherRecipes.getRecipesList();

        List<CrusherRecipeWrapper> recipes = new ArrayList<CrusherRecipeWrapper>();

        for (Map.Entry<ItemStack, ItemStack> entry : smeltingMap.entrySet()) {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();

            List<ItemStack> inputs = stackHelper.getSubtypes(input);
            CrusherRecipeWrapper recipe = new CrusherRecipeWrapper(inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }

}