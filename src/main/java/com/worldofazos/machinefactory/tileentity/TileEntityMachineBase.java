package com.worldofazos.machinefactory.tileentity;

import com.worldofazos.machinefactory.utils.energy.CustomEnergyStorage;
import com.worldofazos.machinefactory.utils.energy.TeslaAdapter;

public class TileEntityMachineBase extends TileEntityInventoryBase {

    private CustomEnergyStorage storage;
    private TeslaAdapter tesla;

    public int cookTime = 0;
    private int totalCookTime;

    private int energyUsage;

    public TileEntityMachineBase(int capacity){

        this.storage = new CustomEnergyStorage(capacity);
        this.tesla = new TeslaAdapter(this.storage);

    }

    public TileEntityMachineBase(int capacity, int maxTransfer)
    {
        this.storage = new CustomEnergyStorage(capacity, maxTransfer);
        this.tesla = new TeslaAdapter(this.storage);
    }

    public TileEntityMachineBase(int capacity, int maxReceive, int maxExtract){
        this.storage = new CustomEnergyStorage(capacity, maxReceive, maxExtract);
        this.tesla = new TeslaAdapter(this.storage);
    }

    public CustomEnergyStorage getStorage(){
        return this.storage;
    }
    public TeslaAdapter getTesla() { return tesla; }

    public int getEnergyStored(){
        return this.storage.getEnergyStored();
    }

    public int getMaxEnergyStored(){
        return this.storage.getMaxEnergyStored();
    }

    public void setTotalCookTime(int totalCookTime){
        this.totalCookTime = totalCookTime;
    }

    public int getTotalCookTime(){
        return this.totalCookTime;
    }

    public void setEnergyUsage(int energyUsage){
        this.energyUsage = energyUsage;
    }

    public int getEnergyUsage(){
        return this.energyUsage;
    }

    public Boolean isMachinePowered(){

        int power = this.getWorld().getStrongPower(this.getPos());

        return this.getWorld().getStrongPower(this.getPos()) > 0;

    }

}