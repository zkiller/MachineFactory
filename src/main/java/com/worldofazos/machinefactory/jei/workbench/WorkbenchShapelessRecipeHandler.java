package com.worldofazos.machinefactory.jei.workbench;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkbenchShapelessRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;

public class WorkbenchShapelessRecipeHandler implements IRecipeHandler<WorkbenchShapelessRecipe> {

    private IJeiHelpers jeiHelpers;

    public WorkbenchShapelessRecipeHandler(IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<WorkbenchShapelessRecipe> getRecipeClass() {
        return WorkbenchShapelessRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return Infos.WORKBENCH_ID;
    }

    @Override
    public String getRecipeCategoryUid(WorkbenchShapelessRecipe recipe) {
        return Infos.WORKBENCH_ID;
    }


    @Override
    public IRecipeWrapper getRecipeWrapper(WorkbenchShapelessRecipe recipe) {
        return new WorkbenchShapelessRecipeWrapper(jeiHelpers, recipe);
    }

    @Override
    public boolean isRecipeValid(WorkbenchShapelessRecipe recipe) {
        if(recipe.getRecipeOutput() == null)
            return false;

        int inputCount = 0;
        for (Object input : recipe.recipeItems) {
            if(input != null) {
                if(input instanceof List && ((List) input).isEmpty())
                {
                    return false;
                }
                inputCount++;
            }
        }

        if(inputCount > 81)
            return false;

        return inputCount > 0;
    }
}