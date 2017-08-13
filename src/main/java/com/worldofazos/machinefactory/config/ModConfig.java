package com.worldofazos.machinefactory.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ModConfig {

    public static String configpath;
    public static Configuration config;

    public static int SolarGenRF;
    public static int SolarCapacityRF;
    public static int SolarOutputRF;

    public static boolean oreGenAll;
    public static boolean oreGenOverworld;
    public static boolean oreGenNether;
    public static boolean oreGenTheEnd;

    public static boolean oreGenPlatine;

    public static void init(FMLPreInitializationEvent event) {

        configpath = event.getModConfigurationDirectory().getAbsolutePath() + File.separator;
        config = new Configuration(new File(configpath + "MachineFactory/MachineFactory.cfg"));
        try {
            config.load();
            ModConfig.configure(config);
        } catch (Exception e1) {
            System.out.println("Error Loading Config File: MachineFactory.cfg");
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static void configure(Configuration config) {

        // Solar Generator
        config.addCustomCategoryComment("SolarGenerator", "Setting for the Basic Solar Generator");
        SolarGenRF = config.getInt("SolarGenRF", "SolarGenerator", 20, 0, 160, "Set the RF/Tick generated");
        SolarOutputRF = config.getInt("SolarOutputRF", "SolarGenerator", SolarGenRF+80, 0, 160, "Set the RF/Tick output");
        SolarCapacityRF = config.getInt("SolarCapacityRF", "SolarGenerator", 5000, 0, 10000, "Set the capacity");

        // Ore Generation
        config.addCustomCategoryComment("oreGeneration", "Setting for the ore generation");
        oreGenAll = config.getBoolean("enable", "oreGeneration", true, "Can generate all Ore");

        oreGenOverworld = config.getBoolean("Overworld", "oreGeneration", true, "Can generate all Ore in Overworld");
        oreGenNether = config.getBoolean("Nether", "oreGeneration", true, "Can generate all Ore in Nether");
        oreGenTheEnd = config.getBoolean("TheEnd", "oreGeneration", true, "Can generate all Ore in The End");

        oreGenPlatine = config.getBoolean("orePlatine", "oreGeneration", true, "Can generate orePlatine");

    }
}
