package com.worldofazos.machinefactory.jei.crusher;

import com.worldofazos.machinefactory.Infos;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class CrusherRecipeCategory<T extends IRecipeWrapper> extends BlankRecipeCategory<T> {

    protected static final int inputSlot = 0;
    protected static final int outputSlot = 1;

    private final IDrawable background;
    private final IDrawableAnimated arrow;

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(Infos.MODID, "textures/gui/machine/crusher.png");
        background = guiHelper.createDrawable(location, 50, 15, 118, 50, 0, 0, 20, 0);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 177, 46, 22, 15);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return Infos.CRUSHER_ID;
    }

    @Override
    public String getTitle() {
        return "crusher";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        arrow.draw(Minecraft.getMinecraft(), 47, 17);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 24, 15);
        guiItemStacks.init(outputSlot, false, 74, 15);

        guiItemStacks.set(ingredients);
    }
}