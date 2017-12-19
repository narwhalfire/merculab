package net.scottnotfound.merculab;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.block.TestBlock;

public class MercuLabBlocks {

    @GameRegistry.ObjectHolder("merculab:test_block")
    public static TestBlock testBlock;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testBlock.initModel();
    }
}