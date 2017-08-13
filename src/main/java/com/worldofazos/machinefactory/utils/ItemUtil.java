package com.worldofazos.machinefactory.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class ItemUtil {

    public static boolean isEnabled(ItemStack stack){
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("IsEnabled");
    }

    public static void changeEnabled(EntityPlayer player, EnumHand hand){
        changeEnabled(player.getHeldItem(hand));
    }

    public static void changeEnabled(ItemStack stack){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }

        boolean isEnabled = isEnabled(stack);
        stack.getTagCompound().setBoolean("IsEnabled", !isEnabled);
    }

}
