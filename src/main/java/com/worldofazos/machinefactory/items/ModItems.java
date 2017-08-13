package com.worldofazos.machinefactory.items;

import com.worldofazos.machinefactory.Infos;
import com.worldofazos.machinefactory.items.armors.ItemObsidianArmor;
import com.worldofazos.machinefactory.items.tools.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static final Item.ToolMaterial obsidianToolMaterial = EnumHelper.addToolMaterial("OBSIDIAN", 3, 3500, 9.0F, 10.0F, 14);
    public static final ItemObsidianArmor.ArmorMaterial obsidianArmorMaterial = EnumHelper.addArmorMaterial("OBSIDIAN", Infos.MODID + ":obsidian", 120, new int[]{5, 12, 8, 5}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F);

    // Ingots
    public static ItemBase ingotPlatine;
    public static ItemBase ingotThorium;
    public static ItemBase ingotSteel;

    // Dust
    public static ItemOre dustPlatine;
    public static ItemOre dustThorium;
    public static ItemOre dustSteel;

    public static ItemOre dustIron;
    public static ItemOre dustGold;
    public static ItemOre dustDiamond;
    public static ItemOre dustCoal;

    // Items
    public static ItemBattery Battery;
    public static ItemUpgrade SpeedUpgrade;

    // Armors
    public static ItemObsidianArmor obsidianHelmet;
    public static ItemObsidianArmor obsidianChestplate;
    public static ItemObsidianArmor obsidianLeggings;
    public static ItemObsidianArmor obsidianBoots;


    // Tools
    public static ItemObsidianSword obsidianSword;
    public static ItemObsidianPickaxe obsidianPickaxe;
    public static ItemObsidianShovel obsidianShovel;
    public static ItemObsidianAxe obsidianAxe;
    public static ItemObsidianHoe obsidianHoe;
    public static ItemObsidianPaxel obsidianPaxel;

    public static void init() {
        // Ingots
        ingotPlatine = register(new ItemOre("ingot_platine", "ingotPlatine"));
        ingotThorium = register(new ItemOre("ingot_thorium", "ingotThorium"));
        ingotSteel = register(new ItemOre("ingot_steel", "ingotSteel"));

        // Dusts
        dustPlatine = register(new ItemOre("dust_platine", "dustPlatine"));
        dustThorium = register(new ItemOre("dust_thorium", "dustThorium"));
        dustSteel = register(new ItemOre("dust_steel", "dustSteel"));

        dustIron = register(new ItemOre("dust_iron", "dustIron"));
        dustGold = register(new ItemOre("dust_gold", "dustGold"));
        dustDiamond = register(new ItemOre("dust_diamond", "dustDiamond"));
        dustCoal = register(new ItemOre("dust_coal", "dustCoal"));

        // Items
        Battery = register(new ItemBattery("battery"));
        SpeedUpgrade = register(new ItemUpgrade("speed_upgrade"));

        // Armors
        obsidianHelmet = register(new ItemObsidianArmor(obsidianArmorMaterial, EntityEquipmentSlot.HEAD, "obsidian_helmet"));
        obsidianChestplate = register(new ItemObsidianArmor(obsidianArmorMaterial, EntityEquipmentSlot.CHEST, "obsidian_chestplate"));
        obsidianLeggings = register(new ItemObsidianArmor(obsidianArmorMaterial, EntityEquipmentSlot.LEGS, "obsidian_leggings"));
        obsidianBoots = register(new ItemObsidianArmor(obsidianArmorMaterial, EntityEquipmentSlot.FEET, "obsidian_boots"));


        // Tools
        obsidianSword = register(new ItemObsidianSword(obsidianToolMaterial, "obsidian_sword"));
        obsidianPickaxe = register(new ItemObsidianPickaxe(obsidianToolMaterial, "obsidian_pickaxe"));
        obsidianShovel = register(new ItemObsidianShovel(obsidianToolMaterial, "obsidian_shovel"));
        obsidianAxe = register(new ItemObsidianAxe(obsidianToolMaterial, "obsidian_axe"));
        obsidianHoe = register(new ItemObsidianHoe(obsidianToolMaterial, "obsidian_hoe"));
        obsidianPaxel = register(new ItemObsidianPaxel(obsidianToolMaterial, "obsidian_paxel"));

    }

    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemModelProvider) {
            ((ItemModelProvider)item).registerItemModel(item);
        }
        if (item instanceof ItemOreDict) {
            ((ItemOreDict)item).initOreDict();
        }

        return item;
    }

}