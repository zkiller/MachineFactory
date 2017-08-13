package com.worldofazos.machinefactory.blocks.Smelter;

import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import com.worldofazos.machinefactory.utils.StackUtil;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class SmelterTE extends TileEntityMachineBase {

    public ItemStackHandler inputSlot = new ItemStackHandler(1);
    public ItemStackHandler outputSlot = new ItemStackHandler(1);
    public ItemStackHandler upgradeSlot = new ItemStackHandler(1);

    public SmelterTE(){
        super(50000, 1000, 0);
    }

    @Override
    public void update(){

        if (!this.getWorld().isRemote) {
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));

            this.machineUpgrade();

            boolean flag = false;

            if(this.canSmelt()) {
                if(this.getStorage().getEnergyStored() >= this.getEnergyUsage()){
                    this.cookTime++;

                    if (this.cookTime >= this.getTotalCookTime()) {
                        this.smeltItems();
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

    public boolean canSmelt(){
        if(StackUtil.isValid(this.inputSlot.getStackInSlot(0))){
            ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.inputSlot.getStackInSlot(0));
            if(StackUtil.isValid(output)){
                if(!StackUtil.isValid(this.outputSlot.getStackInSlot(0)) || (this.outputSlot.getStackInSlot(0).isItemEqual(output) && StackUtil.getStackSize(this.outputSlot.getStackInSlot(0)) <= this.outputSlot.getStackInSlot(0).getMaxStackSize()-StackUtil.getStackSize(output))){
                    return true;
                }
            }
        }
        this.cookTime = 0;
        return false;
    }

    public void smeltItems(){
        ItemStack output = FurnaceRecipes.instance().getSmeltingResult(this.inputSlot.getStackInSlot(0));

        if(!StackUtil.isValid(this.outputSlot.getStackInSlot(0))){
            this.outputSlot.setStackInSlot(0, output.copy());
        }
        else if(this.outputSlot.getStackInSlot(0).getItem() == output.getItem()){
            this.outputSlot.setStackInSlot(0, StackUtil.addStackSize(this.outputSlot.getStackInSlot(0), StackUtil.getStackSize(output)));
        }

        this.inputSlot.setStackInSlot(0, StackUtil.addStackSize(this.inputSlot.getStackInSlot(0), -1));

    }

    public void machineUpgrade(){

        if(StackUtil.isValid(this.upgradeSlot.getStackInSlot(0)) && this.upgradeSlot.getStackInSlot(0).stackSize >=1){

            int i = this.upgradeSlot.getStackInSlot(0).stackSize + 1;

            int total = 200-(i*10);

            this.setTotalCookTime(total > 0 ? total : 1);
            this.setEnergyUsage(3*(i*10));

        }else{
            this.setTotalCookTime(200);
            this.setEnergyUsage(30);
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inputSlot", inputSlot.serializeNBT());
        tag.setTag("outputSlot", outputSlot.serializeNBT());
        tag.setTag("upgradeSlot", upgradeSlot.serializeNBT());
        tag.setInteger("cookTime", this.cookTime);
        tag.setInteger("totalCookTime", this.getTotalCookTime());
        this.getStorage().writeToNBT(tag);
        tag.setInteger("energyUsage", this.getEnergyUsage());
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inputSlot.deserializeNBT(tag.getCompoundTag("inputSlot"));
        outputSlot.deserializeNBT(tag.getCompoundTag("outputSlot"));
        upgradeSlot.deserializeNBT(tag.getCompoundTag("upgradeSlot"));
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

            if(facing == null) {
                return (T) new CombinedInvWrapper(inputSlot, outputSlot, upgradeSlot);
            }

            if(facing == EnumFacing.UP || facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH || facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                return (T) this.inputSlot;
            }

            if(facing == EnumFacing.DOWN) {
                return (T) this.outputSlot;
            }

        }else if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.getStorage();
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER) {
            return (T) this.getTesla();
        }

        return super.getCapability(capability, facing);
    }

}