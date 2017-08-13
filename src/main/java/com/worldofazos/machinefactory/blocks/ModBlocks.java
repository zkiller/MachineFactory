package com.worldofazos.machinefactory.blocks;

import com.worldofazos.machinefactory.blocks.Crusher.CrusherBlock;
import com.worldofazos.machinefactory.blocks.EnergyConduit.BasicEnergyConduitBlock;
import com.worldofazos.machinefactory.blocks.EnergyStorage.BlockEnergyStorage;
import com.worldofazos.machinefactory.blocks.EnergyStorageCreative.EnergyStorageCreativeBlock;
import com.worldofazos.machinefactory.blocks.FusionChamber.FusionChamberBlock;
import com.worldofazos.machinefactory.blocks.FusionPedestal.FusionPedestalBlock;
import com.worldofazos.machinefactory.blocks.Smelter.SmelterBlock;
import com.worldofazos.machinefactory.blocks.SolarGenerator.BlockSolarGenerator;
import com.worldofazos.machinefactory.blocks.VoidOreMiner.VoidOreMinerBlock;
import com.worldofazos.machinefactory.blocks.WorkBench.BlockWorkBench;
import com.worldofazos.machinefactory.items.ItemModelProvider;
import com.worldofazos.machinefactory.items.ItemOreDict;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static BlockBase hardcoreWorkdbench;

    // Ore
    public static BlockOre orePlatine;
    public static BlockOre oreThorium;

    // Metal
    public static BlockOre blockPlatine;
    public static BlockOre blockNetherStar;
    public static BlockOre blockThorium;
    public static BlockOre blockSteel;

    // Machine
    public static BlockBase MachineFrame;
    public static BlockBase Structure;
    public static BlockSolarGenerator SolarGenerator;
    public static BlockEnergyStorage EnergyStorage;
    public static EnergyStorageCreativeBlock EnergyStorageCreative;
    public static CrusherBlock Crusher;
    public static SmelterBlock Smelter;

    public static BasicEnergyConduitBlock BasicConduit;

    public static VoidOreMinerBlock VoidOreMiner;

    public static FusionChamberBlock FusionChamber;
    public static FusionPedestalBlock FusionPedestal;

    public static void init() {

        hardcoreWorkdbench = register(new BlockWorkBench());

        // Ore
        orePlatine = register(new BlockOre("ore_platine", "orePlatine"));
        oreThorium = register(new BlockOre("ore_thorium", "oreThorium"));

        // Metal
        blockPlatine = register(new BlockOre("block_platine", "blockPlatine"));
        blockNetherStar = register(new BlockOre("block_nether_star", "blockNetherStar"));
        blockThorium = register(new BlockOre("block_thorium", "blockThorium"));
        blockSteel = register(new BlockOre("block_steel", "blockSteel"));

        // Machine
        MachineFrame = register(new BlockBase(Material.ROCK, "machine_frame"));
        Structure = register(new BlockBase(Material.ROCK, "structure"));
        SolarGenerator = register(new BlockSolarGenerator());
        EnergyStorage = register(new BlockEnergyStorage());
        EnergyStorageCreative = register(new EnergyStorageCreativeBlock());
        Crusher = register(new CrusherBlock());
        Smelter = register(new SmelterBlock());

        BasicConduit = register(new BasicEnergyConduitBlock());
        VoidOreMiner = register(new VoidOreMinerBlock());

        FusionChamber = register(new FusionChamberBlock());
        FusionPedestal = register(new FusionPedestalBlock());

    }

    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if (itemBlock != null) {
            GameRegistry.register(itemBlock);

            if (block instanceof ItemModelProvider) {
                ((ItemModelProvider)block).registerItemModel(itemBlock);
            }
            if (block instanceof ItemOreDict) {
                ((ItemOreDict)block).initOreDict();
            } else if (itemBlock instanceof ItemOreDict) {
                ((ItemOreDict)itemBlock).initOreDict();
            }
        }

        if (block instanceof BlockTileEntity) {
            GameRegistry.registerTileEntity(((BlockTileEntity<?>)block).getTileEntityClass(), block.getRegistryName().toString());
        }

        return block;
    }

    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

}