package com.worldofazos.machinefactory.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class UtilsRecipes {

    public static void removeRecipe(ItemStack result){
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        for(int i=9; i<recipes.size(); i++){
            IRecipe recipe = recipes.get(i);
            if(result.isItemEqual(recipe.getRecipeOutput())){
                recipes.remove(i);
                i--;
            }
        }
    }

    public static void addToolAndArmorRecipes(ItemStack base, Item pickaxe, Item sword, Item axe, Item shovel, Item hoe, Item helm, Item chest, Item pants, Item boots){
        //Pickaxe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pickaxe),
                "EEE", " S ", " S ",
                'E', base,
                'S', "stickWood"));

        //Sword
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword),
                "E", "E", "S",
                'E', base,
                'S', "stickWood"));

        //Axe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(axe),
                "EE", "ES", " S",
                'E', base,
                'S', "stickWood"));

        //Shovel
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shovel),
                "E", "S", "S",
                'E', base,
                'S', "stickWood"));

        //Hoe
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hoe),
                "EE", " S", " S",
                'E', base,
                'S', "stickWood"));

        //Helm
        GameRegistry.addRecipe(new ItemStack(helm),
                "OOO", "O O",
                'O', base);

        //Chest
        GameRegistry.addRecipe(new ItemStack(chest),
                "O O", "OOO", "OOO",
                'O', base);

        //Legs
        GameRegistry.addRecipe(new ItemStack(pants),
                "OOO", "O O", "O O",
                'O', base);

        //Boots
        GameRegistry.addRecipe(new ItemStack(boots),
                "O O", "O O",
                'O', base);
    }

}
