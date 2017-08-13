package com.worldofazos.machinefactory.items.tools;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.items.ItemModelProvider;
import com.worldofazos.machinefactory.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;

public class ItemObsidianPaxel extends ItemTool implements ItemModelProvider {

    String name;

    public ItemObsidianPaxel(ToolMaterial material, String name){
        super(4, -2.1F, material, new HashSet<Block>());

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);

        setHarvestLevel("pickaxe", material.getHarvestLevel());
        setHarvestLevel("axe", material.getHarvestLevel());
        setHarvestLevel("shovel", material.getHarvestLevel());

        setCreativeTab(MachineFactory.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced){
        int damage = stack.getMaxDamage() - stack.getItemDamage();
        tooltip.add("HP : \u00A7c" + damage);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
    {
        if(toolMaterial == ModItems.obsidianToolMaterial)
            return new ItemStack(ModItems.ingotPlatine).getItem() == stack2.getItem() ? true : super.getIsRepairable(stack1, stack2);
        return false;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState blockState)
    {
        return blockState.getBlock() != Blocks.BEDROCK ? efficiencyOnProperMaterial : 1.0F;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack)
    {
        Block block = state.getBlock();

        if(block == Blocks.OBSIDIAN)
        {
            return toolMaterial.getHarvestLevel() == 3;
        }

        if(block == Blocks.DIAMOND_BLOCK || block == Blocks.DIAMOND_ORE)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }

        if(block == Blocks.GOLD_BLOCK || block == Blocks.GOLD_ORE)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }

        if(block == Blocks.IRON_BLOCK || block == Blocks.IRON_ORE)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }

        if(block == Blocks.LAPIS_BLOCK || block == Blocks.LAPIS_ORE)
        {
            return toolMaterial.getHarvestLevel() >= 1;
        }

        if(block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE)
        {
            return toolMaterial.getHarvestLevel() >= 2;
        }

        if(block == Blocks.ANVIL)
        {
            return toolMaterial.getHarvestLevel() >= 0;
        }

        if(block == Blocks.SNOW || block == Blocks.SNOW_LAYER)
        {
            return true;
        }

        if(state.getMaterial() == Material.ROCK)
        {
            return true;
        }

        return state.getMaterial() == Material.IRON;
    }

    @Override
    public void registerItemModel(Item item) {
        MachineFactory.proxy.registerItemRenderer(this, 0, name);
    }

}
