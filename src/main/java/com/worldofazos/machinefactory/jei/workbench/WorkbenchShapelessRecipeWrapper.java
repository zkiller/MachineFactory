package com.worldofazos.machinefactory.jei.workbench;

import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkbenchShapelessRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by zkill on 2017-06-18.
 */
public class WorkbenchShapelessRecipeWrapper extends BlankRecipeWrapper implements IRecipeWrapper {

    private IJeiHelpers jeiHelpers;

    private final WorkbenchShapelessRecipe recipe;

    public WorkbenchShapelessRecipeWrapper(IJeiHelpers jeiHelpers, WorkbenchShapelessRecipe recipe)
    {
        this.jeiHelpers = jeiHelpers;
        this.recipe = recipe;
        for(Object input : this.recipe.recipeItems)
        {
            if(input instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) input;
                if(itemStack.stackSize != 1)
                    itemStack.stackSize = 1;
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        ItemStack recipeOutput = recipe.getRecipeOutput();

        List<List<ItemStack>> inputs = stackHelper.expandRecipeItemStackInputs(recipe.recipeItems);
        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipeOutput);
    }
}