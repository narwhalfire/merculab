package net.scottnotfound.merculab.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.inventory.container.TestContainerProcessAB;

public class TestGuiProcessAB extends GuiContainer {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(MercuLab.MODID, "textures/gui/testcontainer");


    public TestGuiProcessAB(InventoryPlayer inventory, TileEntity tile) {
        super(new TestContainerProcessAB(inventory, tile));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialticks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }


}