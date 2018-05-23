package net.scottnotfound.merculab.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.test.TestItem;

import java.util.ArrayList;

public class MercuLabItems {

    private static ArrayList<IInitializer> initList = new ArrayList<>();

    public static TestItem testItem;
    public static TestItem testItemA;
    public static TestItem testItemB;

    public static final CreativeTabs CHEM = new CreativeTabs("chem") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.SUGAR);
        }
    };


    private static void preInitItems() {

        testItem = TestItem.t;
        testItemA = TestItem.a;
        testItemB = TestItem.b;

    }

    private static void preInitList() {

        initList.add(testItem);
        initList.add(testItemA);
        initList.add(testItemB);

    }

    public static void initItems() {

        preInitItems();
        preInitList();

        for (IInitializer init : initList) {
            init.init();
        }

    }

    public static void registerItems() {



    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        testItem.initModel();
        testItemA.initModel();
        testItemB.initModel();
    }
}
