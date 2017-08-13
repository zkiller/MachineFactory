package com.worldofazos.machinefactory.blocks.FusionPedestal;

import com.worldofazos.machinefactory.network.PacketRequestUpdateFusionPedestal;
import com.worldofazos.machinefactory.network.PacketUpdateFusionPedestal;
import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.tileentity.TileEntityInventoryBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class FusionPedestalTE  extends TileEntityInventoryBase{

    public ItemStackHandler inventory = new ItemStackHandler(1) {

        @Override
        protected void onContentsChanged(int slot) {
            if (!worldObj.isRemote) {
                lastChangeTime = worldObj.getTotalWorldTime();
                PacketHandler.network.sendToAllAround(new PacketUpdateFusionPedestal(FusionPedestalTE.this), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }

    };

    public long lastChangeTime;

    @Override
    public void onLoad() {
        if (worldObj.isRemote) {
            PacketHandler.network.sendToServer(new PacketRequestUpdateFusionPedestal(this));
        }
    }

    @Override
    public void update() {

        if (!this.getWorld().isRemote) {
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inventory", inventory.serializeNBT());
        tag.setLong("lastChangeTime", this.lastChangeTime);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("inventory"));
        this.lastChangeTime = tag.getLong("lastChangeTime");
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)this.inventory;
        }

        return super.getCapability(capability, facing);
    }

}
