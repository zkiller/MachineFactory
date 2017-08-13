package com.worldofazos.machinefactory.network;

import com.worldofazos.machinefactory.blocks.FusionPedestal.FusionPedestalTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestUpdateFusionPedestal implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateFusionPedestal(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateFusionPedestal(FusionPedestalTE te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateFusionPedestal() {
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

    public static class Handler implements IMessageHandler<PacketRequestUpdateFusionPedestal, PacketUpdateFusionPedestal> {

        @Override
        public PacketUpdateFusionPedestal onMessage(PacketRequestUpdateFusionPedestal message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dimension);
            FusionPedestalTE te = (FusionPedestalTE)world.getTileEntity(message.pos);
            if (te != null) {
                return new PacketUpdateFusionPedestal(te);
            } else {
                return null;
            }
        }

    }

}
