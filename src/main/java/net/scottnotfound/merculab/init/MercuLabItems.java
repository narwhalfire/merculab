package net.scottnotfound.merculab.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.item.glassware.ItemVial;
import net.scottnotfound.merculab.test.TestItem;

public class MercuLabItems {

    @GameRegistry.ObjectHolder("merculab:test_item")
    public static TestItem testItem = TestItem.t;

    @GameRegistry.ObjectHolder("merculab:testA")
    public static TestItem testItemA = TestItem.a;

    @GameRegistry.ObjectHolder("merculab:testB")
    public static TestItem testItemB = TestItem.b;

    @GameRegistry.ObjectHolder("merculab:vial")
    public static ItemVial Vial = new ItemVial(Fluid.BUCKET_VOLUME);

    public static final CreativeTabs CHEM = new CreativeTabs("chem") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.SUGAR);
        }
    };

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testItem.initModel();
        testItemA.initModel();
        testItemB.initModel();
    }
}
