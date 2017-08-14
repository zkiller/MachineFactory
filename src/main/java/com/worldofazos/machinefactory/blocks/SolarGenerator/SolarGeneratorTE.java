package com.worldofazos.machinefactory.blocks.SolarGenerator;

import com.worldofazos.machinefactory.config.ModConfig;
import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class SolarGeneratorTE extends TileEntityMachineBase implements ITickable {

    private int OUTPUT = ModConfig.SolarOutputRF;
    private int currentGen;

    public SolarGeneratorTE(){
        super(ModConfig.SolarCapacityRF, 0, 1000);
    }

    public int getCurrentGen(){
        return this.currentGen;
    }

    public boolean isDesert()
    {
        return this.getWorld().getBiomeProvider().getBiome(this.getPos()).getBiomeName() == "Desert";

    }

    public boolean canGenerateEnergy(){
        return this.getWorld().isDaytime() && !this.getWorld().isRaining() && !this.getWorld().isThundering() && this.getWorld().canBlockSeeSky(this.getPos().offset(EnumFacing.UP)) && !this.getWorld().provider.getHasNoSky() && this.getEnergyStored() != this.getMaxEnergyStored();
    }

    public void update(){
        if(!this.getWorld().isRemote) {
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));

            if (this.canGenerateEnergy()) {

                if (this.isDesert()) {
                    this.currentGen = Math.round(ModConfig.SolarGenRF + 80);
                }else {
                    this.currentGen = ModConfig.SolarGenRF;
                }

                this.getStorage().receiveEnergyInternal(this.getCurrentGen(), false);

            }else{
                this.currentGen = 0;
            }
        }

        //this.transmitPower(this.pos.down(), EnumFacing.UP);
        this.transmitPower(this.pos.up(), EnumFacing.DOWN);
        this.transmitPower(this.pos.north(), EnumFacing.SOUTH);
        this.transmitPower(this.pos.south(), EnumFacing.NORTH);
        this.transmitPower(this.pos.east(), EnumFacing.EAST);
        this.transmitPower(this.pos.west(), EnumFacing.WEST);
        this.markDirty();
    }

    private void transmitPower(BlockPos pos, EnumFacing facing) {
        TileEntity te = this.worldObj.getTileEntity(pos);
        int rfToGive = this.OUTPUT <= this.getEnergyStored() ? this.OUTPUT : this.getEnergyStored();
        int energyStored = this.getEnergyStored();

        if(energyStored > 0) {
            if (te != null) {

                if (te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
                    int received = te.getCapability(CapabilityEnergy.ENERGY, facing).receiveEnergy(rfToGive, false);
                    this.getStorage().extractEnergy(received, false);
                } else if (te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing)) {
                    long received = te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing).givePower(rfToGive, false);
                    this.getTesla().takePower(received, false);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.getStorage().writeToNBT(tag);
        tag.setInteger("currentGen", this.getCurrentGen());
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.getStorage().readFromNBT(tag);
        this.currentGen = tag.getInteger("currentGen");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.getStorage();
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
            return (T) this.getTesla();
        }

        return super.getCapability(capability, facing);
    }

}