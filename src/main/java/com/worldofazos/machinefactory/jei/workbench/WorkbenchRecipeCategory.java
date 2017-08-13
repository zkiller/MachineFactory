package com.worldofazos.machinefactory.jei.workbench;

import com.worldofazos.machinefactory.Infos;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class WorkbenchRecipeCategory  extends BlankRecipeCategory<IRecipeWrapper> {

    public static final int WIDTH = 162;
    public static final int HEIGHT = 199;

    private final IDrawable background;
    //private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;

    public WorkbenchRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation(Infos.MODID, "textures/gui/hardcore_workbench_jei.png");
        background = guiHelper.createDrawable(location, 0, 0, WIDTH, HEIGHT);
        craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
    }

    @Override
    public String getUid() {
        return Infos.WORKBENCH_ID;
    }

    @Override
    public String getTitle() {
        return "Hardcore Workbench";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 71, 176);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int index = 1 + j + (i * 9);
                guiItemStacks.init(index, true, j * 18, i * 18);
            }
        }

        recipeLayout.setRecipeTransferButton(145, 185);

        /* Uncomment once updated to 1.11+
        if(recipeWrapper instanceof ExtremeShapelessRecipeWrapper)
            recipeLayout.setShapeless();
        */

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);

        craftingGridHelper.setInputStacks(guiItemStacks, inputs);
        craftingGridHelper.setOutput(guiItemStacks, outputs);
    }
}