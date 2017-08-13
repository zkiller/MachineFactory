package com.worldofazos.machinefactory.proxy;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberTE;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberTESR;
import com.worldofazos.machinefactory.blocks.FusionPedestal.FusionPedestalTE;
import com.worldofazos.machinefactory.blocks.FusionPedestal.FusionPedestalTESR;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Infos.MODID + ":" + id, "inventory"));
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(FusionChamberTE.class, new FusionChamberTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(FusionPedestalTE.class, new FusionPedestalTESR());
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }

}
