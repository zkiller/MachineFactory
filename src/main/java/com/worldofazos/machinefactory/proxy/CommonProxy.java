package com.worldofazos.machinefactory.proxy;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.blocks.ModBlocks;
import com.worldofazos.machinefactory.config.ModConfig;
import com.worldofazos.machinefactory.handler.ModEventHandler;
import com.worldofazos.machinefactory.handler.ModGuiHandler;
import com.worldofazos.machinefactory.items.ModItems;
import com.worldofazos.machinefactory.items.armors.ItemObsidianArmor;
import com.worldofazos.machinefactory.network.PacketHandler;
import com.worldofazos.machinefactory.recipes.ModRecipes;
import com.worldofazos.machinefactory.world.ModOreGen;
import net.minecraft.item.Item;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.init();
        ModItems.init();
        ModConfig.init(event);
        PacketHandler.registerMessages();
        registerRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(MachineFactory.instance, new ModGuiHandler());
        GameRegistry.registerWorldGenerator(new ModOreGen(), 3);

    }

    public void init(FMLInitializationEvent event) {

        ModRecipes.init();

        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        MinecraftForge.EVENT_BUS.register(new ItemObsidianArmor.abilityHandler());

    }

    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.postInit();
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerRenderers() {

    }

    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }

}