package net.scottnotfound.merculab;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.item.TestItem;

public class MercuLabItems {

    @GameRegistry.ObjectHolder("merculab:test_item")
    public static TestItem testItem;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testItem.initModel();
    }
}
