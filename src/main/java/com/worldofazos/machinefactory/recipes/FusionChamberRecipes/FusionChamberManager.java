package com.worldofazos.machinefactory.recipes.FusionChamberRecipes;

import com.worldofazos.machinefactory.utils.StackUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FusionChamberManager {

    public static FusionChamberManager instance()
    {
        return new FusionChamberManager();
    }

    public static final ArrayList<FusionChamberRecipes> recipes = new ArrayList<FusionChamberRecipes>();

    public FusionChamberManager()
    {

        // Test recipes
        addRecipe(
            new ItemStack(Items.NETHER_STAR, 2), // Results
            new ItemStack(Items.EMERALD), // Chamber
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND), // Ingredient
            new ItemStack(Items.DIAMOND)  // Ingredient
        );

        // Another Test Recipes
        addRecipe(
            new ItemStack(Items.END_CRYSTAL, 4), // Results
            new ItemStack(Item.getItemFromBlock(Blocks.EMERALD_BLOCK)), // Chamber
            new ItemStack(Items.NETHER_STAR),   // Ingredient
            new ItemStack(Items.NETHER_STAR),   // Ingredient
            new ItemStack(Items.NETHER_STAR),   // Ingredient
            new ItemStack(Items.NETHER_STAR),   // Ingredient
            new ItemStack(Items.DIAMOND),       // Ingredient
            new ItemStack(Items.DIAMOND),       // Ingredient
            new ItemStack(Items.DIAMOND),       // Ingredient
            new ItemStack(Items.DIAMOND)        // Ingredient
        );


    }

    private static void addRecipe(ItemStack result, ItemStack chamber, ItemStack... ingredients)
    {
        recipes.add(new FusionChamberRecipes(result, chamber, ingredients));
    }

    @Nullable
    public ItemStack getResult(ItemStack chamber, ItemStack... ingredients)
    {
        for (FusionChamberRecipes entry : recipes) {

            if (ingredients != null) {

                if (StackUtil.areItemStackListEqual(entry.ingredients, ingredients) && StackUtil.areItemsEqual(chamber, entry.chamber, true)) return entry.result;

            }
        }

        return StackUtil.getNull();
    }

    public ArrayList<FusionChamberRecipes> getRecipesList()
    {
        return this.recipes;
    }
}