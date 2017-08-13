package com.worldofazos.machinefactory.blocks.EnergyConduit;

import com.worldofazos.machinefactory.blocks.BlockConduit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BasicEnergyConduitBlock extends BlockConduit {

    public BasicEnergyConduitBlock(){
        super("basic_energy_conduit");
    }


    @Nullable
    @Override
    public conduitTE createTileEntity(World world, IBlockState state) {
        return new conduitTE();
    }

}