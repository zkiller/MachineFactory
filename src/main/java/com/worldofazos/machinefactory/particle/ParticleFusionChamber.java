package com.worldofazos.machinefactory.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleFusionChamber extends Particle{

    private ParticleFusionChamber(World world, double posX, double posY, double posZ, ItemStack stack, double motionY){
        super(world, posX, posY, posZ);
    }
}