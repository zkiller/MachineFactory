package com.worldofazos.machinefactory.blocks.WorkBench;

import com.worldofazos.machinefactory.Infos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWorkdBench extends GuiContainer {

    private WorkBenchTE te;
    private InventoryPlayer playerInv;

    public GuiWorkdBench(InventoryPlayer playerInv, WorkBenchTE te){
        super(new WorkBenchContainer(playerInv, te));

        this.xSize = 237; //Texture xSize
        this.ySize = 255; //Texture ySize

        this.te = te;
        this.playerInv = playerInv;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        String TileText = net.minecraft.util.text.translation.I18n.translateToLocal("Hardcore WorkBench");

        mc.fontRendererObj.drawStringWithShadow(TileText, guiLeft + xSize / 2 - mc.fontRendererObj.getStringWidth(TileText) / 2, guiTop - 10, 0xFFFFFF);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); //Grey background
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Infos.MODID, "textures/gui/hardcore_workbench.png")); //Binds the texture for rendering
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize); //Draws our texture
    }

}
