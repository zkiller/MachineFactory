package com.worldofazos.machinefactory.blocks.WorkBench;

import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkBenchManager;
import com.worldofazos.machinefactory.gui.ItemCraftHandler;
import com.worldofazos.machinefactory.inventory.ModInventoryCraftingResult;
import com.worldofazos.machinefactory.inventory.ModInventoryCrafting;
import com.worldofazos.machinefactory.inventory.slot.SlotCraftingItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class WorkBenchContainer extends Container {

    public net.minecraft.inventory.InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private WorkBenchTE te;
    private IItemHandler handler;

    public WorkBenchContainer(InventoryPlayer player, WorkBenchTE te) {
        this.te = te;
        handler = te.matrix;
        craftMatrix = new ModInventoryCrafting(this, te);
        craftResult = new ModInventoryCraftingResult(te);
        this.addSlotToContainer(new SlotCraftingItemHandler(player.player, this.craftMatrix, this.craftResult, 0, 210, 80));
        int wy;
        int ex;

        for (wy = 0; wy < 9; ++wy) {
            for (ex = 0; ex < 9; ++ex) {
                this.addSlotToContainer(new SlotItemHandler(handler, ex + wy * 9, 39 + ex * 18, 8 + wy * 18));
            }
        }

        for (wy = 0; wy < 3; ++wy) {
            for (ex = 0; ex < 9; ++ex) {
                this.addSlotToContainer(new Slot(player, ex + wy * 9 + 9, 39 + ex * 18, 174 + wy * 18));
            }
        }

        for (ex = 0; ex < 9; ++ex) {
            this.addSlotToContainer(new Slot(player, ex, 39 + ex * 18, 232));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
        ((ItemCraftHandler)handler).crafting = craftMatrix;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory matrix) {
        this.craftResult.setInventorySlotContents(0, WorkBenchManager.getInstance().findMatchingRecipe(this.craftMatrix, te.getWorld()));
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return te.isUseableByPlayer(player);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(slotNumber);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotNumber == 0) {
                if (!this.mergeItemStack(itemstack1, 82, 118, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (slotNumber >= 82 && slotNumber < 109) {
                if (!this.mergeItemStack(itemstack1, 109, 118, false)) {
                    return null;
                }
            } else if (slotNumber >= 109 && slotNumber < 118) {
                if (!this.mergeItemStack(itemstack1, 82, 109, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 82, 118, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}