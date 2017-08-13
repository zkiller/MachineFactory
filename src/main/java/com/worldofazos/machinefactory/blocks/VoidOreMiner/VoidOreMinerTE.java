package com.worldofazos.machinefactory.blocks.VoidOreMiner;

import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import com.worldofazos.machinefactory.utils.StackUtil;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class VoidOreMinerTE extends TileEntityMachineBase {

    public static boolean isCall = true;
    public ArrayList<ItemStack> oreLists;

    public VoidOreMinerTE(){
        super(50000, 1000, 0);

        this.setTotalCookTime(200);
        this.setEnergyUsage(500);
    }

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        public int getStackLimit(int slot, ItemStack stack) {
            return 64;
        }
    };

    @Override
    public void update(){
        if (!this.getWorld().isRemote) {
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));

            boolean flag = false;

            ItemStack output = new ItemStack(Blocks.DIAMOND_ORE, 1);

            if(this.canGenerate(output)) {
                if(this.getStorage().getEnergyStored() >= this.getEnergyUsage()){
                    this.cookTime++;

                    if (this.cookTime >= this.getTotalCookTime()) {
                        this.generate(output);
                        this.cookTime = 0;
                        flag = true;
                    }
                    this.getStorage().extractEnergyInternal(this.getEnergyUsage(), false);
                }

            }

            if(flag){
                this.markDirty();
            }

        }
    }

    public Boolean canGenerate(ItemStack output){

        if(!StackUtil.isValid(this.inventory.getStackInSlot(0)) || (this.inventory.getStackInSlot(0).isItemEqual(output) && StackUtil.getStackSize(this.inventory.getStackInSlot(0)) <= this.inventory.getStackInSlot(0).getMaxStackSize()-StackUtil.getStackSize(output))){
            return true;
        }
        this.cookTime = 0;
        return false;
    }

    public void generate(ItemStack output){
        if(StackUtil.isValid(output)) {
            if (!StackUtil.isValid(this.inventory.getStackInSlot(0))) {
                this.inventory.setStackInSlot(0, output.copy());
            } else if (this.inventory.getStackInSlot(0).getItem() == output.getItem()) {
                this.inventory.setStackInSlot(0, StackUtil.addStackSize(this.inventory.getStackInSlot(0), StackUtil.getStackSize(output)));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inputSlot", inventory.serializeNBT());
        tag.setInteger("cookTime", this.cookTime);
        tag.setInteger("totalCookTime", this.getTotalCookTime());
        this.getStorage().writeToNBT(tag);
        tag.setInteger("energyUsage", this.getEnergyUsage());
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("inputSlot"));
        this.cookTime = tag.getInteger("cookTime");
        this.setTotalCookTime(tag.getInteger("totalCookTime"));
        this.getStorage().readFromNBT(tag);
        this.setEnergyUsage(tag.getInteger("energyUsage"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)this.inventory;
        }else if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.getStorage();
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER) {
            return (T) this.getTesla();
        }

        return super.getCapability(capability, facing);
    }

}