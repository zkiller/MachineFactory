package com.worldofazos.machinefactory.recipes.FusionChamberRecipes;

import net.minecraft.item.ItemStack;

public class FusionChamberRecipes {

    protected ItemStack result;
    protected ItemStack chamber;
    protected ItemStack[] ingredients;

    public FusionChamberRecipes(ItemStack result, ItemStack chamber, ItemStack... ingredients){

        this.result = result;
        this.chamber = chamber;
        this.ingredients = ingredients;

    }

    public ItemStack getRecipeOutput() {
        return result;
    }

    public ItemStack getRecipeChamber() {
        return chamber;
    }

    public ItemStack[] getRecipeIngredients() {
        return ingredients;
    }

}