package com.worldofazos.machinefactory.jei.smelter;

import com.worldofazos.machinefactory.Infos;
import jline.internal.Log;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class SmelterRecipeHandler implements IRecipeHandler<SmelterRecipeWrapper> {

    @Nonnull
    @Override
    public Class<SmelterRecipeWrapper> getRecipeClass() {
        return SmelterRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {

        return null;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull SmelterRecipeWrapper recipe) {

        return Infos.SMELTER_ID;
    }


    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(SmelterRecipeWrapper recipe) {
        return recipe;
    }

    @Nonnull
    @Override
    public boolean isRecipeValid(SmelterRecipeWrapper recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = "";
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }
        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = "";
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }
        return true;
    }
}