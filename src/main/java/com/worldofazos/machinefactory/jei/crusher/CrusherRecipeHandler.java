package com.worldofazos.machinefactory.jei.crusher;

import com.worldofazos.machinefactory.Infos;
import jline.internal.Log;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipeWrapper> {

    @Nonnull
    @Override
    public Class<CrusherRecipeWrapper> getRecipeClass() {
        return CrusherRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {

        return null;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(@Nonnull CrusherRecipeWrapper recipe) {

        return Infos.CRUSHER_ID;
    }


    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(CrusherRecipeWrapper recipe) {
        return recipe;
    }

    @Nonnull
    @Override
    public boolean isRecipeValid(CrusherRecipeWrapper recipe) {
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