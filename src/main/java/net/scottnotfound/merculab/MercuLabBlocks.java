package net.scottnotfound.merculab;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.block.TestBlock;
import net.scottnotfound.merculab.block.TestContainerBlock;
import net.scottnotfound.merculab.block.TestContainerTileEntity;

public class MercuLabBlocks {

    @GameRegistry.ObjectHolder("merculab:test_block")
    public static TestBlock testBlock;

    @GameRegistry.ObjectHolder("merculab:testcontainerblock")
    public static TestContainerBlock testContainerBlock;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testBlock.initModel();
        testContainerBlock.initModel();
    }
}