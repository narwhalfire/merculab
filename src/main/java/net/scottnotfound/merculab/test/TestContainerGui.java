package net.scottnotfound.merculab.test;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.scottnotfound.merculab.init.MercuLab;

public class TestContainerGui extends GuiContainer {


    private static final ResourceLocation background = new ResourceLocation(MercuLab.MOD_ID, "textures/gui/testcontainer.png");

    public TestContainerGui(TestContainerTileEntity tileEntity, TestContainer container) {
        super(container);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(0, 0, 0, 0, xSize, ySize);
    }
}
