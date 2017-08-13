package com.worldofazos.machinefactory.items;

import com.worldofazos.machinefactory.MachineFactory;
import net.minecraft.item.Item;

public class ItemBase extends Item implements ItemModelProvider {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);

        setCreativeTab(MachineFactory.creativeTab);
    }

    @Override
    public void registerItemModel(Item item) {
        MachineFactory.proxy.registerItemRenderer(item, 0, name);
    }

}