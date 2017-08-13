package com.worldofazos.machinefactory.network;

import com.worldofazos.machinefactory.Infos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper network;

    private static int id = 0;

    public static void registerMessages(){
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Infos.MODID);
        network.registerMessage(MessageTEUpdate.MessageHolder.class,MessageTEUpdate.class,id++,Side.CLIENT);
        network.registerMessage(new PacketUpdateFusionChamber.Handler(), PacketUpdateFusionChamber.class, id++, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateFusionChamber.Handler(), PacketRequestUpdateFusionChamber.class, id++, Side.SERVER);
        network.registerMessage(new PacketUpdateFusionPedestal.Handler(), PacketUpdateFusionPedestal.class, id++, Side.CLIENT);
        network.registerMessage(new PacketRequestUpdateFusionPedestal.Handler(), PacketRequestUpdateFusionPedestal.class, id++, Side.SERVER);
    }
}