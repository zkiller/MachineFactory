package com.worldofazos.machinefactory.blocks.SolarGenerator;

import com.worldofazos.machinefactory.config.ModConfig;
import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.utils.energy.CustomEnergyStorage;
import com.worldofazos.machinefactory.utils.energy.TeslaAdapter;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class SolarGeneratorTE extends TileEntity implements ITickable {

    private CustomEnergyStorage storage = new CustomEnergyStorage(ModConfig.SolarCapacityRF, 0, 1000);
    private TeslaAdapter tesla = new TeslaAdapter(this.storage);

    private int OUTPUT = ModConfig.SolarOutputRF;
    private int currentGen;

    public int getEnergyStored(){
        return this.storage.getEnergyStored();
    }

    public int getMaxEnergyStored(){
        return this.storage.getMaxEnergyStored();
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

            // Generation de l'oxygen
            if (this.canGenerateEnergy()) {

                if (isDesert()) {
                    this.currentGen = Math.round(ModConfig.SolarGenRF + 80);
                }else {
                    this.currentGen = ModConfig.SolarGenRF;
                }

                this.storage.receiveEnergyInternal(this.getCurrentGen(), false);

            }else{
                this.currentGen = 0;
            }
        }

        int energyStored = this.getEnergyStored();

        for (EnumFacing facing : EnumFacing.values()) {

            // Ont output l'énergy
            if(facing != EnumFacing.UP){

                BlockPos pos = getPos().offset(facing);
                TileEntity te = this.getWorld().getTileEntity(pos);

                int rfToGive = this.OUTPUT <= this.getEnergyStored() ? this.OUTPUT : this.getEnergyStored();

                // is Forge Energy
                if(te != null && te.hasCapability(CapabilityEnergy.ENERGY, null)){
                    int received = te.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(rfToGive, false);
                    energyStored -= this.storage.extractEnergy(received, false);

                // is Tesla Energy
                }else if(te != null && te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null)){
                    long received = te.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER,null).givePower(rfToGive, false);
                    energyStored -= this.tesla.takePower(received, false);
                }

                if (energyStored <= 0) {
                    break;
                }
            }

        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.storage.writeToNBT(tag);
        tag.setInteger("currentGen", this.getCurrentGen());
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.storage.readFromNBT(tag);
        this.currentGen = tag.getInteger("currentGen");
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        this.getWorld().markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }


    public void markForUpdate() {
        if (this.getWorld() != null) {
            Block block = this.getWorld().getBlockState(this.pos).getBlock();
            this.getWorld().notifyBlockUpdate(this.pos, this.getWorld().getBlockState(this.pos), this.getWorld().getBlockState(this.pos), 3);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if (capability == CapabilityEnergy.ENERGY) {
            return (T) storage;
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
            return (T) tesla;
        }

        return super.getCapability(capability, facing);
    }

}