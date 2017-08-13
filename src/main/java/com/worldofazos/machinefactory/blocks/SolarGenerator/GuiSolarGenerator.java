package com.worldofazos.machinefactory.blocks.SolarGenerator;

import com.worldofazos.machinefactory.Infos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiSolarGenerator extends GuiContainer {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Infos.MODID, "textures/gui/machine/solar_generator.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private SolarGeneratorTE te;

    public GuiSolarGenerator(SolarGeneratorTE tileEntity, SolarGeneratorContainer container) {
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
            this.drawTexturedModalRect(guiLeft + 82, guiTop + 65-i, 178, 4, 12, i);
        }

        String LeadText = I18n.translateToLocal("Solar Generator");
        mc.fontRendererObj.drawStringWithShadow(LeadText, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(LeadText) / 2, guiTop - 10, 0xFFFFFF);

        String LeadBank = I18n.translateToLocal(te.getEnergyStored() + " / " + te.getMaxEnergyStored() + " RF");
        mc.fontRendererObj.drawStringWithShadow(LeadBank, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(LeadBank) / 2, guiTop + 4, 0xFFFFFF);

        String LeadGen = I18n.translateToLocal(te.getCurrentGen() + " RF/T");
        mc.fontRendererObj.drawStringWithShadow(LeadGen, guiLeft + 4, guiTop + 16, 0xFFFFFF);
    }

    private int getEnergyScaled(int i) {
        return te.getEnergyStored() * i / te.getMaxEnergyStored();
    }

}