package com.worldofazos.machinefactory.handler;

import net.minecraft.init.Items;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler {

    @SubscribeEvent
    public void itemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack() != null && event.getItemStack().getItem() == Items.END_CRYSTAL) {
            event.getToolTip().add(TextFormatting.DARK_GREEN + "Recipe tweaked by Machine Factory.");
        }
    }

}