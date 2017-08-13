package com.worldofazos.machinefactory.items;

import com.worldofazos.machinefactory.utils.energy.CustomEnergyStorage;
import com.worldofazos.machinefactory.utils.energy.TeslaAdapter;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

class ItemEnergy  extends ItemBase{

    private int Capacity;


    public ItemEnergy(String name, int Capacity){
        super(name);
        this.setMaxStackSize(1);

        this.Capacity = Capacity;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool){
        super.addInformation(stack, player, list, bool);
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null) {
                list.add(0, "Energy Stored : " + storage.getEnergyStored() + "/" + storage.getMaxEnergyStored() + "RF");
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list){
        ItemStack stackFull = new ItemStack(this);
        this.setEnergy(stackFull, this.getMaxEnergyStored(stackFull));
        list.add(stackFull);

        ItemStack stackEmpty = new ItemStack(this);
        this.setEnergy(stackEmpty, 0);
        list.add(stackEmpty);
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                double maxAmount = storage.getMaxEnergyStored();
                double energyDif = maxAmount-storage.getEnergyStored();
                return energyDif/maxAmount;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).setEnergyStored(energy);
            }
        }
    }

    public int receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).receiveEnergyInternal(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage){
                ((CustomEnergyStorage)storage).extractEnergyInternal(maxExtract, simulate);
            }
        }
        return 0;
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.receiveEnergy(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.extractEnergy(maxExtract, simulate);
            }
        }
        return 0;
    }

    public int getEnergyStored(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.getEnergyStored();
            }
        }
        return 0;
    }

    public int getMaxEnergyStored(ItemStack stack){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                return storage.getMaxEnergyStored();
            }
        }
        return 0;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider{

        private final CustomEnergyStorage storage;
        private TeslaAdapter tesla;

        public EnergyCapabilityProvider(final ItemStack stack, ItemEnergy item){

            this.storage = new CustomEnergyStorage(item.Capacity){
                @Override
                public int getEnergyStored(){
                    if(stack.hasTagCompound()){
                        return stack.getTagCompound().getInteger("Energy");
                    }
                    else{
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy){
                    if(!stack.hasTagCompound()){
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("Energy", energy);
                }
            };


            this.tesla = new TeslaAdapter(this.storage);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityEnergy.ENERGY || capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER;
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {

            if (capability == CapabilityEnergy.ENERGY) {
                return (T) this.storage;
            }else if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_HOLDER) {
                return (T) this.tesla;
            }

            return null;
        }
    }
}