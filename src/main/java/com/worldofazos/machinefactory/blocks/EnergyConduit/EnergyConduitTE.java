package com.worldofazos.machinefactory.blocks.EnergyConduit;

import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyConduitTE extends TileEntityMachineBase implements ITickable {

    private int transmited;
    private int transmission;

    public EnergyConduitTE(){
        super(100000);
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
                if(te instanceof EnergyConduitTE) {
                    if(this.getStorage().getEnergyStored() > teEnergy.getEnergyStored()) {
                        int delta = (this.getStorage().getEnergyStored() - teEnergy.getEnergyStored()) / 2;
                        int maxTransmitQuantity = this.getStorage().extractEnergy(delta, true);
                        int maxEnergyToTransmit = teEnergy.receiveEnergy(maxTransmitQuantity, true);

                        this.getStorage().extractEnergy(maxEnergyToTransmit, false);
                        teEnergy.receiveEnergy(maxEnergyToTransmit, false);
                        transmission += maxEnergyToTransmit;
                    }
                }
                // For "normal" TE
                else {
                    int maxTransmitQuantity = this.getStorage().extractEnergy(this.getStorage().getEnergyStored(), true);
                    int maxEnergyToTransmit = teEnergy.receiveEnergy(maxTransmitQuantity, true);

                    this.getStorage().extractEnergy(maxEnergyToTransmit, false);
                    teEnergy.receiveEnergy(maxEnergyToTransmit, false);
                    transmission += maxEnergyToTransmit;
                }
            }else if(te.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, facing) || te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing)){
                ITeslaHolder teTeslaHolder = te.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, facing);
                ITeslaConsumer teTeslaConsumer = te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing);

                // Special case of my cables
                if(te instanceof EnergyConduitTE) {
                    if (this.getTesla().getStoredPower() > teTeslaHolder.getStoredPower()) {
                        long delta = (this.getTesla().getStoredPower() - teTeslaHolder.getStoredPower()) / 2;
                        long maxTransmitQuantity = this.getTesla().takePower(delta, true);
                        long maxEnergyToTransmit = teTeslaConsumer.givePower(maxTransmitQuantity, true);

                        this.getTesla().takePower(maxEnergyToTransmit, false);
                        teTeslaConsumer.givePower(maxEnergyToTransmit, false);
                        transmission += maxEnergyToTransmit;
                    }
                }
                // For "normal" TE
                else {

                    long maxTransmitQuantity = this.getTesla().takePower(this.getTesla().getStoredPower(), true);
                    long maxEnergyToTransmit = teTeslaConsumer.givePower(maxTransmitQuantity, true);

                    this.getTesla().takePower(maxEnergyToTransmit, false);
                    teTeslaConsumer.givePower(maxEnergyToTransmit, false);
                    transmission += maxEnergyToTransmit;
                }
            }

        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        this.getStorage().writeToNBT(nbt);

        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.getStorage().readFromNBT(nbt);
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
            return (T)this.getStorage();
        }
        if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER){
            return (T)this.getTesla();
        }
        return super.getCapability(capability, facing);
    }

    public String toDebug() {
        return String.format("Stored: %d, Trans: %d", this.getEnergyStored(), this.transmited);
    }

}