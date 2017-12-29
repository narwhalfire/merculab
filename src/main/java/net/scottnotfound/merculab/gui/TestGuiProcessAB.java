package net.scottnotfound.merculab.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.inventory.TestContainerProcessAB;

public class TestGuiProcessAB extends GuiContainer {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(MercuLab.MODID, "Textures/gui/testcontainer");
    private final IInventory playerInventory;
    private final IInventory processInventory;


    public TestGuiProcessAB(IInventory playerInv, IInventory processInv) {
        super(new TestContainerProcessAB(playerInv, processInv));
        this.playerInventory = playerInv;
        this.processInventory = processInv;
    }


    protected void drawGuiContainerBackgroundLayer(float partialticks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }


}