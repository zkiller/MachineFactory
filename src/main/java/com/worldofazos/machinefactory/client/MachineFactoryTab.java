package com.worldofazos.machinefactory.client;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MachineFactoryTab extends CreativeTabs {

    public MachineFactoryTab() {
        super(Infos.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModBlocks.SolarGenerator);
    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }

}