package com.worldofazos.machinefactory.blocks;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.items.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBase extends Block implements ItemModelProvider {

    protected String name;

    public BlockBase(Material material, String name) {
        super(material);

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