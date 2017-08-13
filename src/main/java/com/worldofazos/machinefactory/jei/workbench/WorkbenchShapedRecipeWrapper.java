package com.worldofazos.machinefactory.jei.workbench;

import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkbenchShapedOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class WorkbenchShapedRecipeWrapper extends BlankRecipeWrapper {

    private final WorkbenchShapedOreRecipe recipe;
    private final IJeiHelpers jeiHelpers;

    public WorkbenchShapedRecipeWrapper(IJeiHelpers jeiHelpers, WorkbenchShapedOreRecipe recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        for(Object obj: this.recipe.getInput())
        {
            if(obj instanceof ItemStack) {
                ItemStack itemStack = (ItemStack)obj;
                if (itemStack != null && itemStack.stackSize != 1) {
                    itemStack.stackSize = 1;
                }
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ItemStack recipeOutput = recipe.getRecipeOutput();

        List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(Arrays.asList(recipe.getInput()));

        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipeOutput);

    }

}