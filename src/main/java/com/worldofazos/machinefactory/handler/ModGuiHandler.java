package com.worldofazos.machinefactory.handler;

import com.worldofazos.machinefactory.blocks.Crusher.CrusherContainer;
import com.worldofazos.machinefactory.blocks.Crusher.CrusherGUI;
import com.worldofazos.machinefactory.blocks.Crusher.CrusherTE;
import com.worldofazos.machinefactory.blocks.EnergyStorage.EnergyStorageContainer;
import com.worldofazos.machinefactory.blocks.EnergyStorage.EnergyStorageTE;
import com.worldofazos.machinefactory.blocks.EnergyStorage.GuiEnergyStorage;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberContainer;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberGUI;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberTE;
import com.worldofazos.machinefactory.blocks.Smelter.SmelterContainer;
import com.worldofazos.machinefactory.blocks.Smelter.SmelterGUI;
import com.worldofazos.machinefactory.blocks.Smelter.SmelterTE;
import com.worldofazos.machinefactory.blocks.SolarGenerator.GuiSolarGenerator;
import com.worldofazos.machinefactory.blocks.SolarGenerator.SolarGeneratorContainer;
import com.worldofazos.machinefactory.blocks.SolarGenerator.SolarGeneratorTE;
import com.worldofazos.machinefactory.blocks.VoidOreMiner.VoidOreMinerContainer;
import com.worldofazos.machinefactory.blocks.VoidOreMiner.VoidOreMinerGui;
import com.worldofazos.machinefactory.blocks.VoidOreMiner.VoidOreMinerTE;
import com.worldofazos.machinefactory.blocks.WorkBench.GuiWorkdBench;
import com.worldofazos.machinefactory.blocks.WorkBench.WorkBenchContainer;
import com.worldofazos.machinefactory.blocks.WorkBench.WorkBenchTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {

    public static final int EnergyStorage = 0;
    public static final int SolarGenerator = 1;
    public static final int WorkBench = 2;
    public static final int Crusher = 3;
    public static final int Smelter = 4;
    public static final int Miner = 5;
    public static final int FusionChamber = 6;

    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        switch (ID) {
            case EnergyStorage:
                return new EnergyStorageContainer(player.inventory, (EnergyStorageTE) te);
            case SolarGenerator:
                return new SolarGeneratorContainer(player.inventory);
            case  WorkBench:
                return new WorkBenchContainer(player.inventory, (WorkBenchTE) te);
            case Crusher:
                return new CrusherContainer(player.inventory, (CrusherTE) te);
            case Smelter:
                return new SmelterContainer(player.inventory, (SmelterTE) te);
            case Miner:
                return new VoidOreMinerContainer(player.inventory, (VoidOreMinerTE) te);
            case FusionChamber:
                return new FusionChamberContainer(player.inventory, (FusionChamberTE) te);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        switch (ID) {
            case EnergyStorage:
                EnergyStorageTE containerTileEntity = (EnergyStorageTE) te;
                return new GuiEnergyStorage(containerTileEntity, new EnergyStorageContainer(player.inventory, containerTileEntity));
            case SolarGenerator:
                return new GuiSolarGenerator((SolarGeneratorTE) te, new SolarGeneratorContainer(player.inventory));
            case WorkBench:
                return new GuiWorkdBench(player.inventory, (WorkBenchTE) te);
            case Crusher:
                return new CrusherGUI((CrusherTE) te, new CrusherContainer(player.inventory, (CrusherTE) te));
            case Smelter:
                return new SmelterGUI((SmelterTE) te, new SmelterContainer(player.inventory, (SmelterTE) te));
            case Miner:
                return new VoidOreMinerGui((VoidOreMinerTE) te, new VoidOreMinerContainer(player.inventory, (VoidOreMinerTE) te));
            case FusionChamber:
                return new FusionChamberGUI((FusionChamberTE) te, new FusionChamberContainer(player.inventory, (FusionChamberTE) te));
            default:
                return null;
        }
    }

}
