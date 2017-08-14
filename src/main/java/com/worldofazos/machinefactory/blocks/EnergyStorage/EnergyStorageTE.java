package com.worldofazos.machinefactory.blocks.EnergyStorage;

import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class EnergyStorageTE extends TileEntityMachineBase implements ITickable {

    private int IN = 2500;
    private int OUTPUT = 2500;
    private int EnergyStored;
    private int rfToGive;

    public EnergyStorageTE(){
        super(100000, 2500);
    }

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public void update(){

        if(!this.getWorld().isRemote){
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));
        }

        this.EnergyStored = this.getEnergyStored();
        this.rfToGive = this.OUTPUT <= this.getEnergyStored() ? this.OUTPUT : this.getEnergyStored();


        // Charge Item in Slot
        if(inventory.getStackInSlot(0) != null){
            ItemStack stack = inventory.getStackInSlot(0);

            // is Forge Energy
            if(stack.hasCapability(CapabilityEnergy.ENERGY, null) && this.getEnergyStored() > 0){
                int charge = stack.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(rfToGive, false);
                this.EnergyStored -= this.getStorage().extractEnergy(charge, false);

            // is Tesla Energy
            }else if(stack.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null) && this.getEnergyStored() > 0){
                long charge = stack.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER,null).givePower(rfToGive, false);
                this.EnergyStored -= this.getTesla().takePower(charge, false);
            }


        }

        this.transmitPower(this.pos.down(), EnumFacing.UP);
        //this.transmitPower(this.pos.up(), EnumFacing.DOWN);
        //this.transmitPower(this.pos.north(), EnumFacing.SOUTH);
        //this.transmitPower(this.pos.south(), EnumFacing.NORTH);
        //this.transmitPower(this.pos.east(), EnumFacing.EAST);
        //this.transmitPower(this.pos.west(), EnumFacing.WEST);
        this.markDirty();
    }

    private void transmitPower(BlockPos pos, EnumFacing facing) {
        if (this.EnergyStored > 0) {
            TileEntity te = this.worldObj.getTileEntity(pos);
            if (te != null) {

                if (te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
                    int received = te.getCapability(CapabilityEnergy.ENERGY, facing).receiveEnergy(this.rfToGive, false);
                    this.EnergyStored -= this.getStorage().extractEnergy(received, false);

                } else if (te.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, facing) || te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing)) {
                    long received = te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing).givePower(rfToGive, false);
                    this.EnergyStored -= this.getTesla().takePower(received, false);

                }

            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inputSlot", inventory.serializeNBT());
        this.getStorage().writeToNBT(tag);
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("inputSlot"));
        this.getStorage().readFromNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)inventory;
        }else if (capability == CapabilityEnergy.ENERGY) {
            return (T) getStorage();
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
            return (T) getTesla();
        }

        return super.getCapability(capability, facing);
    }

}