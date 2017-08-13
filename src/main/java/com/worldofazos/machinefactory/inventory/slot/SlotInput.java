package com.worldofazos.machinefactory.inventory.slot;

import com.worldofazos.machinefactory.utils.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotInput extends SlotItemHandler {

    public SlotInput(IItemHandler itemHandler, int index, int xPosition, int yPosition){

        super(itemHandler, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return StackUtil.isValid(itemstack);
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {

        if (playerIn instanceof EntityPlayer){
            return true;
        }

        return false;
    }

}