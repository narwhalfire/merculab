package net.scottnotfound.merculab;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.test.TestItem;

public class MercuLabItems {

    @GameRegistry.ObjectHolder("merculab:test_item")
    public static TestItem testItem = TestItem.t;

    @GameRegistry.ObjectHolder("merculab:testA")
    public static TestItem testItemA = TestItem.a;

    @GameRegistry.ObjectHolder("merculab:testB")
    public static TestItem testItemB = TestItem.b;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testItem.initModel();
        testItemA.initModel();
        testItemB.initModel();
    }
}
