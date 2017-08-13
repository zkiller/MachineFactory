package com.worldofazos.machinefactory.recipes;

import com.google.common.collect.Maps;
import com.worldofazos.machinefactory.items.ModItems;
import com.worldofazos.machinefactory.utils.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class CrusherRecipes {

    public static CrusherRecipes instance()
    {
        return new CrusherRecipes();
    }

    private final Map<ItemStack, ItemStack> recipes = Maps.<ItemStack, ItemStack>newHashMap();

    public CrusherRecipes(){

        addRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL));
        addRecipe(Blocks.GRAVEL, new ItemStack(Blocks.SAND));
        addRecipe(Blocks.STONE, new ItemStack(Blocks.GRAVEL));

        addRecipe(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 12, 4));
        addRecipe(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD, 2));
        addRecipe(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ, 4));
        addRecipe(Blocks.GLOWSTONE, new ItemStack(Items.GLOWSTONE_DUST, 4));
        addRecipe(Blocks.WOOL, new ItemStack(Items.STRING, 4));

        addRecipe(Items.DIAMOND, new ItemStack(ModItems.dustDiamond, 1));

        String[] oreDictionary = OreDictionary.getOreNames();

        for(String entry : oreDictionary) {
            if(entry.startsWith("dust")) {
                List<ItemStack> oreList = OreDictionary.getOres(entry.replaceFirst("dust", "ore"));
                if(!oreList.isEmpty()) {
                    List<ItemStack> dustList = OreDictionary.getOres(entry);
                    if(!dustList.isEmpty())
                        addRecipe(getOreDict(entry.replaceFirst("dust", "ore")), new ItemStack(dustList.get(0).getItem(), 2, dustList.get(0).getItemDamage()));
                }
            } else if(entry.startsWith("ingot")) {
                List<ItemStack> dustList = OreDictionary.getOres(entry.replaceFirst("ingot", "dust"));
                if(!dustList.isEmpty()) {
                    addRecipe(getOreDict(entry), dustList.get(0).copy());
                }
            }
        }

    }

    public void addRecipe(String input, ItemStack output){

        List<ItemStack> oreList = OreDictionary.getOres(input);
        if(!oreList.isEmpty()) {
            for (ItemStack entry : oreList ){

                ItemStack stack = entry.copy();
                stack.stackSize = 1;
                this.addRecipe(stack, output);

            }
        }

    }

    public void addRecipe(String input, String output){

        List<ItemStack> oreList1 = OreDictionary.getOres(input);
        List<ItemStack> oreList2 = OreDictionary.getOres(output);

        if(!oreList1.isEmpty() && !oreList2.isEmpty()) {
            for (ItemStack entry1 : oreList1 ){
                for (ItemStack entry2 : oreList2 ){
                    ItemStack stack = entry1.copy();
                    stack.stackSize = 1;
                    this.addRecipe(stack, entry2.copy());
                }
            }
        }


    }

    public void addRecipe(Block input, ItemStack output)
    {
        this.addRecipe(Item.getItemFromBlock(input), output);
    }

    public void addRecipe(Item input, ItemStack output)
    {
        this.addRecipe(new ItemStack(input, 1, 32767), output);
    }

    public void addRecipe(ItemStack input, ItemStack output){
        if (getResult(input) != null) { net.minecraftforge.fml.common.FMLLog.info("Ignored crusher recipes with conflicting input: " + input + " = " + output); return; }
        this.recipes.put(input, output);
    }

    @Nullable
    public ItemStack getResult(ItemStack stack)
    {

        for (Map.Entry<ItemStack, ItemStack> entry : this.recipes.entrySet() ){

            if(StackUtil.areItemsEqual(stack, entry.getKey(), true)){
                return (ItemStack) entry.getValue();
            }

        }
        return StackUtil.getNull();
    }

    public Map<ItemStack, ItemStack> getRecipesList()
    {
        return this.recipes;
    }

    public static ItemStack getOreDict(String oreIdName) {
        List<ItemStack> ores = OreDictionary.getOres(oreIdName);
        if (ores != null && ores.size() > 0)
            return ores.get(0).copy();
        return null;
    }

}