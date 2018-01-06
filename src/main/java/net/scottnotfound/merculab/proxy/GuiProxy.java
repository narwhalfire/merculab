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
    public Object getServerGuiElement(int guiid, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (guiid == 1) {
            return new TestContainer(player.inventory, (TestContainerTileEntity) tileEntity);
        } else if (guiid == 2) {
            return new TestContainerProcessAB(player.inventory, tileEntity);
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int guiid, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (guiid == 1) {
            return new TestContainerGui((TestContainerTileEntity) tileEntity,
                    new TestContainer(player.inventory, (TestContainerTileEntity) tileEntity));
        } else if (guiid == 2) {
            return new TestGuiProcessAB(player.inventory, tileEntity);
        }

        return null;
    }
}
