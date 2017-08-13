package com.worldofazos.machinefactory.gui;

import com.worldofazos.machinefactory.blocks.WorkBench.WorkBenchTE;
import com.worldofazos.machinefactory.blocks.WorkBench.crafting.WorkBenchManager;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraftforge.items.ItemStackHandler;

public class ItemCraftHandler extends ItemStackHandler {

    public InventoryCrafting crafting;
    private WorkBenchTE te;

    public ItemCraftHandler(int size, WorkBenchTE te)
    {
        super(size);
        this.te = te;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if(crafting != null)
            te.setResult(WorkBenchManager.getInstance().findMatchingRecipe(this.crafting, te.getWorld()));
        super.onContentsChanged(slot);
    }
}