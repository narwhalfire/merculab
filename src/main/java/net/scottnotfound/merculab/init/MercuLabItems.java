package net.scottnotfound.merculab.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.scottnotfound.merculab.test.TestItem;

import java.util.ArrayList;
import java.util.List;

public class MercuLabItems {

    private static final List<Item> initList = new ArrayList<>();


    // TestItem
    public static TestItem testItem;
    public static TestItem testItemA;
    public static TestItem testItemB;


    public static void init() {

        initTestItems();

    }


    private static void initTestItems() {

        testItem                = initTestItem("testItem");
        testItemA               = initTestItem("testItemA");
        testItemB               = initTestItem("testItemB");

    }

    private static TestItem initTestItem(String name) {

        TestItem testitem = new TestItem(name);
        testitem.setRegistryName(name);
        initList.add(testitem);

        return testitem;

    }

    public static void register() {

        for (Item item : initList) {

            GameRegistry.findRegistry(Item.class).register(item);

        }

    }

}
