package com.worldofazos.machinefactory.items;

import com.worldofazos.machinefactory.utils.ItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class ItemBattery extends ItemEnergy{

    public ItemBattery(String name){
        super(name, 100000);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public boolean hasEffect(ItemStack stack){
        return ItemUtil.isEnabled(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
        super.addInformation(stack, player, list, bool);
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && player.isSneaking()) {

            ItemUtil.changeEnabled(player, hand);

            return new ActionResult(EnumActionResult.SUCCESS, itemStack);
        }
        return super.onItemRightClick(itemStack, world, player, hand);
    }

}