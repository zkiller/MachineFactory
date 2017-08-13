package com.worldofazos.machinefactory.inventory.slot;

import com.worldofazos.machinefactory.inventory.ModInventoryCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class SlotCraftingItemHandler extends Slot {
    private final ModInventoryCrafting craftingInv;
    private final IItemHandler matrix;
    private final EntityPlayer player;

    public SlotCraftingItemHandler(EntityPlayer player, net.minecraft.inventory.InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
    {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        craftingInv = (ModInventoryCrafting)craftingInventory;
        this.player = player;
        this.matrix = ((ModInventoryCrafting)craftingInventory).craft.matrix;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean isItemValid(@Nullable ItemStack stack)
    {
        return false;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        for (int i = 0; i < matrix.getSlots(); i++) {
            if(matrix.getStackInSlot(i) != null)
            {
                matrix.extractItem(i, 1, false);
            }
        }
        //craftingInv.craft.setResult(WorkBenchManager.getInstance().findMatchingRecipe(craftingInv, craftingInv.craft.getWorld()));
        craftingInv.container.onCraftMatrixChanged(craftingInv);
    }
}