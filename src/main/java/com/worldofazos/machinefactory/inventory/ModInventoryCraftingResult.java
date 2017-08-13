package com.worldofazos.machinefactory.inventory;

import com.worldofazos.machinefactory.blocks.WorkBench.WorkBenchTE;
import net.minecraft.item.ItemStack;

public class ModInventoryCraftingResult extends net.minecraft.inventory.InventoryCraftResult {

    private WorkBenchTE craft;

    public ModInventoryCraftingResult(WorkBenchTE te)
    {
        craft = te;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? craft.getResult() : null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement) {
        //return craft.decrStackSize(slot, decrement);
        ItemStack stack = craft.getResult();
        if (stack != null) {
            ItemStack itemstack = stack;
            craft.setResult(null);
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        craft.setResult(stack);
    }
}