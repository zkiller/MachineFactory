package com.worldofazos.machinefactory.blocks.Crusher;

import com.worldofazos.machinefactory.Infos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class CrusherGUI extends GuiContainer {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Infos.MODID, "textures/gui/machine/crusher.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private CrusherTE te;

    public CrusherGUI(CrusherTE tileEntity, CrusherContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        te = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (this.te.getEnergyStored() > 0) {
            int i = getEnergyScaled(40);
            this.drawTexturedModalRect(guiLeft + 10, guiTop + 53-i, 178, 4, 12, i);
        }

        if(this.te.cookTime > 0){
            int e = this.getProgressScaled(21);
            this.drawTexturedModalRect(guiLeft + 77, guiTop + 32, 177, 46, e, 15);
        }

        String Name = I18n.translateToLocal("Crusher");
        mc.fontRendererObj.drawStringWithShadow(Name, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(Name) / 2, guiTop - 10, 0xFFFFFF);

        if (this.isPointInRegion(10, 13, 12, 40, mouseX, mouseY)) {
            List<String> rf = new ArrayList<String>();
            rf.add(te.getEnergyStored() + " / " + te.getMaxEnergyStored() + " RF");
            rf.add(te.getEnergyUsage() + " RF/T");
            GuiUtils.drawHoveringText(rf, mouseX, mouseY, 30, 250, -10, mc.fontRendererObj);
        }
    }

    private int getEnergyScaled(int i) {
        return te.getEnergyStored() * i / te.getMaxEnergyStored();
    }

    private int getProgressScaled(int pixels) {

        int i = te.cookTime;
        int j = te.getTotalCookTime();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

}
