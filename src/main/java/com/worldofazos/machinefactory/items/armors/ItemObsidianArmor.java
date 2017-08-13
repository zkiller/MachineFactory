package com.worldofazos.machinefactory.items.armors;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.items.ItemModelProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemObsidianArmor extends ItemArmor implements ItemModelProvider {

    private String name;

    public ItemObsidianArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name){
        super(material, 0, slot);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(MachineFactory.creativeTab);
        this.setMaxStackSize(1);
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced){
        int damage = stack.getMaxDamage() - stack.getItemDamage();
        tooltip.add("Durability: \u00A7c" + damage);
    }

    public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack){
        //ItemStack head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        //ItemStack legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        //ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);

        if(chest != null && chest.getItem() instanceof ItemObsidianArmor || entity.capabilities.isCreativeMode || entity.isSpectator()) {
            // Add potion effect to armor

            entity.fallDistance = 0.0F;
        }
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        return repair.getItem() == getItemFromBlock(Blocks.OBSIDIAN);
    }

    @Override
    public void registerItemModel(Item item) {
        MachineFactory.proxy.registerItemRenderer(this, 0, name);
    }

    public static class abilityHandler {

        public static List<String> playersWithSet = new ArrayList<String>();

        public static String playerKey(EntityPlayer player) {
            return player.getGameProfile().getName() +":"+ player.worldObj.isRemote;
        }

        public static boolean playerHasSet(EntityPlayer entity) {
            // ItemStack head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            ItemStack chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            //ItemStack legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
            //ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);

            return chest != null && chest.getItem() instanceof ItemObsidianArmor;
        }

        @SubscribeEvent
        public void updatePlayerAbilityStatus(LivingEvent.LivingUpdateEvent event) {
            if(event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)event.getEntityLiving();
                String key = playerKey(player);

                Boolean hasSet = playerHasSet(player);
                if(playersWithSet.contains(key)){
                    if(hasSet){
                        //player.stepHeight = 1.0F;
                        player.capabilities.allowFlying = true;
                    } else {
                        player.stepHeight = 0.5F;
                        if(!player.capabilities.isCreativeMode && !player.isSpectator()){
                            player.capabilities.allowFlying = false;
                            player.capabilities.isFlying = false;
                        }
                        playersWithSet.remove(key);
                    }
                } else if(hasSet) {
                    playersWithSet.add(key);
                }
            }
        }

    }

}