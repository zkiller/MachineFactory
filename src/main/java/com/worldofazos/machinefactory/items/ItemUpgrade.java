package com.worldofazos.machinefactory.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemUpgrade extends ItemBase{

    public ItemUpgrade(String name){
        super(name);
        setMaxStackSize(20);

    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
        super.addInformation(stack, player, list, bool);
        list.add(0, "Improve speed");
    }

}