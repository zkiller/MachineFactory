package com.worldofazos.machinefactory.blocks.EnergyStorage;

import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.utils.energy.CustomEnergyStorage;
import com.worldofazos.machinefactory.utils.energy.TeslaAdapter;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class EnergyStorageTE extends TileEntity implements ITickable {

    private CustomEnergyStorage storage = new CustomEnergyStorage(1000000, 2500);
    private TeslaAdapter tesla = new TeslaAdapter(this.storage);

    private int IN = 2500;
    private int OUTPUT = 2500;

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
    };

    public int getEnergyStored(){
        return this.storage.getEnergyStored();
    }

    public int getMaxEnergyStored(){
        return this.storage.getMaxEnergyStored();
    }

    public void update(){

        if(!this.getWorld().isRemote){
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));
        }

        int energyStored = this.getEnergyStored();

        int rfToGive = this.OUTPUT <= this.getEnergyStored() ? this.OUTPUT : this.getEnergyStored();


        // Chargement des items dans la slot
        if(inventory.getStackInSlot(0) != null){
            ItemStack stack = inventory.getStackInSlot(0);

            // is Forge Energy
            if(stack.hasCapability(CapabilityEnergy.ENERGY, null) && this.getEnergyStored() > 0){
                int charge = stack.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(rfToGive, false);
                energyStored -= this.storage.extractEnergy(charge, false);

            // is Tesla Energy
            }else if(stack.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null) && this.getEnergyStored() > 0){
                long charge = stack.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER,null).givePower(rfToGive, false);
                energyStored -= this.tesla.takePower(charge, false);
            }


        }

        // Transfert d'oxygen vers les machines
        for (EnumFacing facing : EnumFacing.values()) {

            // Ont output seulement par le bas
            if(facing == EnumFacing.DOWN){

                BlockPos pos = getPos().offset(facing);
                TileEntity te = this.getWorld().getTileEntity(pos);

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
        tag.setTag("inputSlot", inventory.serializeNBT());
        this.storage.writeToNBT(tag);
        return tag;
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("inputSlot"));
        this.storage.readFromNBT(tag);
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

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)inventory;
        }else if (capability == CapabilityEnergy.ENERGY) {
            return (T) storage;
        }else if (capability == TeslaCapabilities.CAPABILITY_HOLDER || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER) {
            return (T) tesla;
        }

        return super.getCapability(capability, facing);
    }

}