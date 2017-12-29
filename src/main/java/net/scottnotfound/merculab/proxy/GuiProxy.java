package net.scottnotfound.merculab.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.gui.TestGuiProcessAB;
import net.scottnotfound.merculab.inventory.TestContainerProcessAB;
import net.scottnotfound.merculab.test.TestContainer;
import net.scottnotfound.merculab.test.TestContainerGui;
import net.scottnotfound.merculab.test.TestContainerTileEntity;
import net.scottnotfound.merculab.tileentity.TestTileEntityProcessAB;

public class GuiProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TestContainerTileEntity) {
            return new TestContainer(player.inventory, (TestContainerTileEntity) tileEntity);
        } else if (tileEntity instanceof TestTileEntityProcessAB) {
            TestTileEntityProcessAB tileEntityProcessAB = (TestTileEntityProcessAB) tileEntity;
            return new TestContainerProcessAB(player.inventory, tileEntityProcessAB.processABInv);
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TestContainerTileEntity) {
            TestContainerTileEntity containerTileEntity = (TestContainerTileEntity) tileEntity;
            return new TestContainerGui(containerTileEntity, new TestContainer(player.inventory, containerTileEntity));
        } else  if (tileEntity instanceof TestTileEntityProcessAB) {
            TestTileEntityProcessAB tileEntityProcessAB = (TestTileEntityProcessAB) tileEntity;
            return new TestGuiProcessAB(player.inventory, tileEntityProcessAB.processABInv);
        }
        return null;
    }
}
