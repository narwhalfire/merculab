package net.scottnotfound.merculab.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.block.TestBlockProcessAB;
import net.scottnotfound.merculab.block.TestBlockbakedmodel;
import net.scottnotfound.merculab.test.TestBlock;
import net.scottnotfound.merculab.test.TestContainerBlock;

public class MercuLabBlocks {

    @GameRegistry.ObjectHolder("merculab:test_block")
    public static TestBlock testBlock = new TestBlock();

    @GameRegistry.ObjectHolder("merculab:testcontainerblock")
    public static TestContainerBlock testContainerBlock = new TestContainerBlock();

    @GameRegistry.ObjectHolder("merculab:processab")
    public static TestBlockProcessAB testBlockProcessAB = new TestBlockProcessAB();

    @GameRegistry.ObjectHolder("merculab:bakedmodelblock")
    public static TestBlockbakedmodel bakedModelBlock = new TestBlockbakedmodel();


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testBlock.initModel();
        testContainerBlock.initModel();
        testBlockProcessAB.initModel();
        bakedModelBlock.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        bakedModelBlock.initItemModel();
    }
}