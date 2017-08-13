package com.worldofazos.machinefactory.network;

import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateFusionChamber implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateFusionChamber(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateFusionChamber(FusionChamberTE te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateFusionChamber() {
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateFusionChamber, PacketUpdateFusionChamber> {

        @Override
        public PacketUpdateFusionChamber onMessage(PacketRequestUpdateFusionChamber message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
            FusionChamberTE te = (FusionChamberTE)world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateFusionChamber(te);
            } else {
                return null;
            }
        }

    }

}
