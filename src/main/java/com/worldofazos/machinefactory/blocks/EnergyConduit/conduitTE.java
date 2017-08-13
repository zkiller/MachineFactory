package com.worldofazos.machinefactory.blocks.EnergyConduit;

import com.worldofazos.machinefactory.utils.energy.CustomEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class conduitTE extends TileEntity implements ITickable {


    /** Forge Energy **/
    private CustomEnergyStorage storage;

    private int transmited;
    private int transmission;

    public conduitTE(){
        super();

        this.storage = new CustomEnergyStorage(100000);
    }

    @Override
    public void update() {
        if(!this.worldObj.isRemote) {
            transmission = 0;

            this.transmitPower(this.pos.down(), EnumFacing.UP);
            this.transmitPower(this.pos.up(), EnumFacing.DOWN);
            this.transmitPower(this.pos.north(), EnumFacing.SOUTH);
            this.transmitPower(this.pos.south(), EnumFacing.NORTH);
            this.transmitPower(this.pos.east(), EnumFacing.EAST);
            this.transmitPower(this.pos.west(), EnumFacing.WEST);

            transmited = transmission;
            this.markDirty();
        }
    }

    private void transmitPower(BlockPos pos, EnumFacing facing) {
        TileEntity te = this.worldObj.getTileEntity(pos);
        if(te != null) {
            if(te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
                IEnergyStorage teEnergy = te.getCapability(CapabilityEnergy.ENERGY, facing);

                // Special case of my cables
                if(te instanceof conduitTE) {
                    if(this.storage.getEnergyStored() > teEnergy.getEnergyStored()) {
                        int delta = (this.storage.getEnergyStored() - teEnergy.getEnergyStored()) / 2;
                        int maxTransmitQuantity = this.storage.extractEnergy(delta, true);
                        int maxEnergyToTransmit = teEnergy.receiveEnergy(maxTransmitQuantity, true);

                        this.storage.extractEnergy(maxEnergyToTransmit, false);
                        teEnergy.receiveEnergy(maxEnergyToTransmit, false);
                        transmission += maxEnergyToTransmit;
                    }
                }
                // For "normal" TE
                else {
                    int maxTransmitQuantity = this.storage.extractEnergy(this.storage.getEnergyStored(), true);
                    int maxEnergyToTransmit = teEnergy.receiveEnergy(maxTransmitQuantity, true);

                    this.storage.extractEnergy(maxEnergyToTransmit, false);
                    teEnergy.receiveEnergy(maxEnergyToTransmit, false);
                    transmission += maxEnergyToTransmit;
                }
            }
        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        this.storage.writeToNBT(nbt);

        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.storage.readFromNBT(nbt);
    }

    public int getEnergyStored() {
        return this.storage.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) {
            return (T)this.storage;
        }
        return super.getCapability(capability, facing);
    }

    public String toDebug() {
        return String.format("Stored: %d, Trans: %d", this.storage.getEnergyStored(), this.transmited);
    }

}