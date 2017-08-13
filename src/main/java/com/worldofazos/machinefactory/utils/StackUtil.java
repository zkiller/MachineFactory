package com.worldofazos.machinefactory.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class StackUtil{

    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static ItemStack validateCopy(ItemStack stack){
        if(isValid(stack)){
            return stack.copy();
        }
        else{
            return getNull();
        }
    }

    public static ItemStack validateCheck(ItemStack stack){
        if(isValid(stack)){
            return stack;
        }
        else{
            return getNull();
        }
    }

    public static boolean isValid(ItemStack stack){
        return stack != null && !ItemStack.areItemStacksEqual(stack, getNull()) && stack.stackSize > 0 && stack.getItem() != null;
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2, boolean checkWildcard){
        return isValid(stack1) && isValid(stack2) && (stack1.isItemEqual(stack2) || (checkWildcard && stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == WILDCARD || stack2.getItemDamage() == WILDCARD)));
    }


    public static ItemStack getNull(){
        return null;
    }

    public static int getStackSize(ItemStack stack){
        if(!isValid(stack)){
            return 0;
        }
        else{
            return stack.stackSize;
        }
    }

    public static ItemStack setStackSize(ItemStack stack, int size){
        return setStackSize(stack, size, false);
    }

    public static ItemStack setStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        if(size <= 0){
            if(isValid(stack) && containerOnEmpty){
                return stack.getItem().getContainerItem(stack);
            }
            else{
                return getNull();
            }
        }
        stack.stackSize = size;
        return stack;
    }

    public static ItemStack addStackSize(ItemStack stack, int size){
        return addStackSize(stack, size, false);
    }

    public static ItemStack addStackSize(ItemStack stack, int size, boolean containerOnEmpty){
        return setStackSize(stack, getStackSize(stack)+size, containerOnEmpty);
    }

    public static boolean areItemStackListEqual(ItemStack[] stack1, ItemStack[] stack2) {
        if(stack1.length != stack2.length) return false;

        for(int i=0; i<stack1.length; i++) {
            ItemStack item1 = stack1[i];
            ItemStack item2 = stack2[i];

            if(!isValid(item1) || !isValid(item2)
                    || !areItemsEqual(item1, item2, true))
                return false;

        }

        return true;
    }
}