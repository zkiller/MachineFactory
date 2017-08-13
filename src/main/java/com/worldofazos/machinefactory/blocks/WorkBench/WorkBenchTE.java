package com.worldofazos.machinefactory.blocks.WorkBench;

import com.worldofazos.machinefactory.gui.ItemCraftHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class WorkBenchTE extends TileEntity implements ICapabilityProvider {
    public ItemCraftHandler matrix = new ItemCraftHandler(81, this);
    private ItemStack result;

    public ItemStack getResult()
    {
        return result;
    }

    public void setResult(ItemStack result)
    {
        this.result = result;
    }

    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        matrix.setStackInSlot(slot, stack);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("matrix", matrix.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        matrix.deserializeNBT(compound.getCompoundTag("matrix"));
        super.readFromNBT(compound);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.getWorld().getTileEntity(this.getPos()) == this
                && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }
}