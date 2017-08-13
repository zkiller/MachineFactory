package com.worldofazos.machinefactory.inventory.slot;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotEnergy extends SlotItemHandler {

    public SlotEnergy(IItemHandler itemHandler, int index, int xPosition, int yPosition){

        super(itemHandler, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return itemstack.hasCapability(CapabilityEnergy.ENERGY, null) || itemstack.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null);
    }

}