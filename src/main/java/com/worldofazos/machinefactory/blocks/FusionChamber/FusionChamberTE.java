package com.worldofazos.machinefactory.blocks.FusionChamber;

import com.worldofazos.machinefactory.blocks.FusionPedestal.FusionPedestalTE;
import com.worldofazos.machinefactory.network.PacketRequestUpdateFusionChamber;
import com.worldofazos.machinefactory.network.PacketUpdateFusionChamber;
import com.worldofazos.machinefactory.network.MessageTEUpdate;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.recipes.FusionChamberRecipes.FusionChamberManager;
import com.worldofazos.machinefactory.tileentity.TileEntityMachineBase;
import com.worldofazos.machinefactory.utils.StackUtil;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class FusionChamberTE extends TileEntityMachineBase {

    public FusionChamberTE(){
        super(50000, 1000, 0);

    }

    public ItemStackHandler inputSlot = new ItemStackHandler(1) {

        @Override
        protected void onContentsChanged(int slot) {
            if (!worldObj.isRemote) {
                lastChangeTime = worldObj.getTotalWorldTime();
                PacketHandler.network.sendToAllAround(new PacketUpdateFusionChamber(FusionChamberTE.this), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }

    };
    public ItemStackHandler outputSlot = new ItemStackHandler(1);
    public ItemStackHandler upgradeSlot = new ItemStackHandler(1);

    public long lastChangeTime;

    @Override
    public void onLoad() {
        if (worldObj.isRemote) {
            PacketHandler.network.sendToServer(new PacketRequestUpdateFusionChamber(this));
        }
    }

    public boolean checkMultiBlock(){

        TileEntity te1 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()-2));
        TileEntity te2 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()+2));
        TileEntity te3 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()-2));
        TileEntity te4 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()+2));

        TileEntity te5 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-3, pos.getY(), this.pos.getZ()));
        TileEntity te6 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+3, pos.getY(), this.pos.getZ()));
        TileEntity te7 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()-3));
        TileEntity te8 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()+3));

        if(te1 != null && (te1 instanceof FusionPedestalTE) && te2 != null && (te2 instanceof FusionPedestalTE) && te3 != null && (te3 instanceof FusionPedestalTE) && te4 != null && (te4 instanceof FusionPedestalTE) && te5 != null && (te5 instanceof FusionPedestalTE) && te6 != null && (te6 instanceof FusionPedestalTE) && te7 != null && (te7 instanceof FusionPedestalTE) && te8 != null && (te8 instanceof FusionPedestalTE)){
            return true;
        }

        return false;

    }

    @Override
    public void update(){

        if (!this.getWorld().isRemote) {
            PacketHandler.network.sendToAll(new MessageTEUpdate(this));

            if(StackUtil.isValid(this.upgradeSlot.getStackInSlot(0)) && this.upgradeSlot.getStackInSlot(0).stackSize >=1){

                int i = this.upgradeSlot.getStackInSlot(0).stackSize + 1;

                int total = 200-(i*10);

                this.setTotalCookTime(total > 0 ? total : 1);
                this.setEnergyUsage(3*(i*10));

            }else{
                this.setTotalCookTime(200);
                this.setEnergyUsage(30);
            }

            boolean flag = false;

            if(this.canSmelt()) {
                if(this.getStorage().getEnergyStored() >= this.getEnergyUsage() && !this.isMachinePowered() && this.checkMultiBlock()){
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

        TileEntity te1 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()-2));
        TileEntity te2 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()+2));
        TileEntity te3 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()-2));
        TileEntity te4 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()+2));

        TileEntity te5 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-3, pos.getY(), this.pos.getZ()));
        TileEntity te6 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+3, pos.getY(), this.pos.getZ()));
        TileEntity te7 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()-3));
        TileEntity te8 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()+3));

        ItemStack output = null;

        if(te1 != null && (te1 instanceof FusionPedestalTE) && te2 != null && (te2 instanceof FusionPedestalTE) && te3 != null && (te3 instanceof FusionPedestalTE) && te4 != null && (te4 instanceof FusionPedestalTE) && te5 != null && (te5 instanceof FusionPedestalTE) && te6 != null && (te6 instanceof FusionPedestalTE) && te7 != null && (te7 instanceof FusionPedestalTE) && te8 != null && (te8 instanceof FusionPedestalTE)){
            output = FusionChamberManager.instance().getResult(
                    this.inputSlot.getStackInSlot(0),
                    ((FusionPedestalTE) te1).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te2).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te3).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te4).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te5).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te6).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te7).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te8).inventory.getStackInSlot(0)
            );
        }
            if(StackUtil.isValid(output)){
                if(!StackUtil.isValid(this.outputSlot.getStackInSlot(0)) || (this.outputSlot.getStackInSlot(0).isItemEqual(output) && StackUtil.getStackSize(this.outputSlot.getStackInSlot(0)) <= this.outputSlot.getStackInSlot(0).getMaxStackSize()-StackUtil.getStackSize(output))){
                    return true;
                }
            }
            this.cookTime = 0;
            return false;
    }

    public void smeltItems(){

        TileEntity te1 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()-2));
        TileEntity te2 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()+2));
        TileEntity te3 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+2, pos.getY(), this.pos.getZ()-2));
        TileEntity te4 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-2, pos.getY(), this.pos.getZ()+2));

        TileEntity te5 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()-3, pos.getY(), this.pos.getZ()));
        TileEntity te6 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX()+3, pos.getY(), this.pos.getZ()));
        TileEntity te7 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()-3));
        TileEntity te8 = this.getWorld().getTileEntity(new BlockPos(this.pos.getX(), pos.getY(), this.pos.getZ()+3));

        ItemStack output = null;

        if(te1 != null && (te1 instanceof FusionPedestalTE) && te2 != null && (te2 instanceof FusionPedestalTE) && te3 != null && (te3 instanceof FusionPedestalTE) && te4 != null && (te4 instanceof FusionPedestalTE) && te5 != null && (te5 instanceof FusionPedestalTE) && te6 != null && (te6 instanceof FusionPedestalTE) && te7 != null && (te7 instanceof FusionPedestalTE) && te8 != null && (te8 instanceof FusionPedestalTE)){
            output = FusionChamberManager.instance().getResult(
                    this.inputSlot.getStackInSlot(0),
                    ((FusionPedestalTE) te1).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te2).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te3).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te4).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te5).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te6).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te7).inventory.getStackInSlot(0),
                    ((FusionPedestalTE) te8).inventory.getStackInSlot(0)
            );

            if(!StackUtil.isValid(this.outputSlot.getStackInSlot(0))){
                this.outputSlot.setStackInSlot(0, output.copy());
            }
            else if(this.outputSlot.getStackInSlot(0).getItem() == output.getItem()){
                this.outputSlot.setStackInSlot(0, StackUtil.addStackSize(this.outputSlot.getStackInSlot(0), StackUtil.getStackSize(output)));
            }

            this.inputSlot.setStackInSlot(0, StackUtil.addStackSize(this.inputSlot.getStackInSlot(0), -1));
            ((FusionPedestalTE) te1).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te2).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te3).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te4).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te5).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te6).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te7).inventory.extractItem(0, 1, false);
            ((FusionPedestalTE) te8).inventory.extractItem(0, 1, false);

            //te1.inventory.extractItem(0, 1, false);

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
        tag.setLong("lastChangeTime", this.lastChangeTime);
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
        this.lastChangeTime = tag.getLong("lastChangeTime");
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