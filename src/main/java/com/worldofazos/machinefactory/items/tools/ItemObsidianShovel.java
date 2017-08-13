package com.worldofazos.machinefactory.items.tools;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.items.ItemModelProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemObsidianShovel extends ItemSpade implements ItemModelProvider {

	private String name;

	public ItemObsidianShovel(ToolMaterial material, String name) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setHarvestLevel("shovel", material.getHarvestLevel());
		this.setCreativeTab(MachineFactory.creativeTab);
		this.setMaxStackSize(1);
		this.name = name;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced){
		int damage = stack.getMaxDamage() - stack.getItemDamage();
		tooltip.add("HP : \u00A7c" + damage);
	}

	@Override
	public void registerItemModel(Item item) {
		MachineFactory.proxy.registerItemRenderer(this, 0, name);
	}

}
