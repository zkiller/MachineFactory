package com.worldofazos.machinefactory.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotResult extends SlotItemHandler {

    public SlotResult(IItemHandler itemHandler, int index, int xPosition, int yPosition){

        super(itemHandler, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {

        return false;
    }

}