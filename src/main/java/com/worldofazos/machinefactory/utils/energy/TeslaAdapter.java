package com.worldofazos.machinefactory.utils.energy;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraftforge.energy.EnergyStorage;

public final class TeslaAdapter implements ITeslaHolder, ITeslaConsumer, ITeslaProducer {

    private final CustomEnergyStorage storage;

    public TeslaAdapter(CustomEnergyStorage storage) {
        this.storage = storage;
    }

    public final EnergyStorage getStorage() {
        return this.storage;
    }

    public long getStoredPower() {
        return (long)this.storage.getEnergyStored();
    }

    public long getCapacity() {
        return (long)this.storage.getMaxEnergyStored();
    }

    public long givePower(long power, boolean simulated) {
        return (long)this.storage.receiveEnergy((int)power, simulated);
    }

    public long takePower(long power, boolean simulated) {
        return (long)this.storage.extractEnergy((int)power, simulated);
    }


}