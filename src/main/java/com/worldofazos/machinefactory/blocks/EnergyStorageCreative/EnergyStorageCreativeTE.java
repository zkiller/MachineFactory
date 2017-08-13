package com.worldofazos.machinefactory.blocks.EnergyStorageCreative;

import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class EnergyStorageCreativeTE extends TileEntityMachineBase implements ITickable {

    public EnergyStorageCreativeTE(){
        super(1000000, 0, 1000000);
    }

    public void update(){

        if(!this.getWorld().isRemote){
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));

            this.markDirty();

            this.transmitPower(this.pos.down(), EnumFacing.UP);
            this.transmitPower(this.pos.up(), EnumFacing.DOWN);
            this.transmitPower(this.pos.north(), EnumFacing.SOUTH);
            this.transmitPower(this.pos.south(), EnumFacing.NORTH);
            this.transmitPower(this.pos.east(), EnumFacing.EAST);
            this.transmitPower(this.pos.west(), EnumFacing.WEST);

        }

    }

    private void transmitPower(BlockPos pos, EnumFacing facing) {
        TileEntity te = this.worldObj.getTileEntity(pos);
        if(te != null) {

            if(te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
                IEnergyStorage energy = te.getCapability(CapabilityEnergy.ENERGY, facing);
                energy.receiveEnergy(1000000, false);
            }

            if(te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null)){
                ITeslaConsumer energy = te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER,null);
                energy.givePower(1000000, false);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.getStorage().writeToNBT(tag);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.getStorage().readFromNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.getStorage();
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER) {
            return (T) this.getTesla();
        }

        return super.getCapability(capability, facing);
    }

}
