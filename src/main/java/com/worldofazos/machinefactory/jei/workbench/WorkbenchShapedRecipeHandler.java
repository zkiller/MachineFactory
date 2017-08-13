package com.worldofazos.machinefactory.jei.workbench;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkbenchShapedOreRecipe;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.List;

public class WorkbenchShapedRecipeHandler implements IRecipeHandler<WorkbenchShapedOreRecipe> {

    private IJeiHelpers jeiHelpers;

    public WorkbenchShapedRecipeHandler(IJeiHelpers jeiHelpers)
    {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public Class<WorkbenchShapedOreRecipe> getRecipeClass() {
        return WorkbenchShapedOreRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return Infos.WORKBENCH_ID;
    }

    @Override
    public String getRecipeCategoryUid(WorkbenchShapedOreRecipe recipe) {
        return Infos.WORKBENCH_ID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(WorkbenchShapedOreRecipe recipe) {
        return new WorkbenchShapedRecipeWrapper(jeiHelpers, recipe);
    }

    @Override
    public boolean isRecipeValid(WorkbenchShapedOreRecipe recipe) {
        if(recipe.getRecipeOutput() == null)
            return false;
        int inputCount = 0;
        for (Object input : recipe.getInput()) {
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