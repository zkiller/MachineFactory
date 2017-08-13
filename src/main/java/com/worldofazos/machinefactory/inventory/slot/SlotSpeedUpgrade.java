package com.worldofazos.machinefactory.inventory.slot;

import com.worldofazos.machinefactory.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotSpeedUpgrade extends SlotItemHandler {

    public SlotSpeedUpgrade(IItemHandler itemHandler, int index, int xPosition, int yPosition){

        super(itemHandler, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        if (itemstack == null)
            return false;

        return itemstack.getItem() == ModItems.SpeedUpgrade;
    }

    @Override
    public int getItemStackLimit(ItemStack stack){
        return 20;
    }

}