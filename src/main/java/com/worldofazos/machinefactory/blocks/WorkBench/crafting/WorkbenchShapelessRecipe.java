package com.worldofazos.machinefactory.blocks.WorkBench.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkbenchShapelessRecipe implements IRecipe {
    /**
     * Is the ItemStack that you get when craft the recipes.
     */
    private final ItemStack recipeOutput;
    /**
     * Is a List of ItemStack that composes the recipes.
     */
    public final ArrayList<Object> recipeItems = new ArrayList<Object>();

    public WorkbenchShapelessRecipe(ItemStack result, List ingredients) {
        this.recipeOutput = result;
        for (Object in : ingredients) {
            if (in instanceof ItemStack)
            {
                recipeItems.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                recipeItems.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                recipeItems.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                recipeItems.add(OreDictionary.getOres((String)in));
            }
            else if (in instanceof List)
            {
                recipeItems.add(in);
            }
            else
            {
                String ret = "Invalid shapeless ore recipes: ";
                for (Object tmp :  ingredients)
                {
                    ret += tmp + ", ";
                }
                ret += recipeOutput;
                throw new RuntimeException(ret);
            }
        }
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return new ItemStack[0];
    }

    /**
     * Used to check if a recipes matches current crafting inventory
     */
    public boolean matches(InventoryCrafting matrix, World world) {
        ArrayList arraylist = new ArrayList(this.recipeItems);

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                ItemStack itemstack = matrix.getStackInRowAndColumn(j, i);

                if (itemstack != null) {
                    boolean flag = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext()) {
                        Object next = iterator.next();

                        if(next instanceof ItemStack) {
                            ItemStack itemstack1 = (ItemStack)next;
                            if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getItemDamage() == 32767 || itemstack.getItemDamage() == itemstack1.getItemDamage())) {
                                flag = true;
                                arraylist.remove(itemstack1);
                                break;
                            }
                        }
                        else if(next instanceof List)
                        {
                            Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
                            while (itr.hasNext() && !flag)
                            {
                                flag = OreDictionary.itemMatches(itr.next(), itemstack, false);
                            }
                            if(flag) {
                                arraylist.remove(next);
                                break;
                            }
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipes
     */
    public ItemStack getCraftingResult(InventoryCrafting matrix) {
        return this.recipeOutput.copy();
    }

    /**
     * Returns the size of the recipes area
     */
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}