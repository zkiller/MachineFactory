package com.worldofazos.machinefactory;

import com.worldofazos.machinefactory.client.MachineFactoryTab;
import com.worldofazos.machinefactory.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Infos.MODID, name = Infos.MODNAME, version = Infos.VERSION, acceptedMinecraftVersions = "[1.10.2]", dependencies="required-after:Forge@[12.18.3.2185,);after:*;")
public class MachineFactory {

    @Mod.Instance(Infos.MODID)
    public static MachineFactory instance;

    @SidedProxy(serverSide = Infos.CommonProxy, clientSide = Infos.ClientProxy)
    public static CommonProxy proxy;

    public static final MachineFactoryTab creativeTab = new MachineFactoryTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}