package com.worldofazos.machinefactory.blocks.EnergyStorage;

import com.worldofazos.machinefactory.Infos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class GuiEnergyStorage extends GuiContainer {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Infos.MODID, "textures/gui/machine/energy_storage.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private EnergyStorageTE te;

    public GuiEnergyStorage(EnergyStorageTE tileEntity, EnergyStorageContainer container) {
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

        String LeadText = I18n.translateToLocal("Energy Storage");
        mc.fontRendererObj.drawStringWithShadow(LeadText, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(LeadText) / 2, guiTop - 10, 0xFFFFFF);

        String LeadBank = I18n.translateToLocal(te.getEnergyStored() + " / " + te.getMaxEnergyStored() + " RF");
        mc.fontRendererObj.drawStringWithShadow(LeadBank, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(LeadBank) / 2, guiTop + 4, 0xFFFFFF);
    }

    private int getEnergyScaled(int i) {
        return te.getEnergyStored() * i / te.getMaxEnergyStored();
    }

}
