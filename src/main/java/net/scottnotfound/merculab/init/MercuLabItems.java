package net.scottnotfound.merculab.init;

import net.minecraft.item.Item;
import net.scottnotfound.merculab.test.TestItem;

import java.util.ArrayList;
import java.util.List;

public class MercuLabItems {


    /* *** GENERATED START *** */

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

    /* *** GENERATED END *** */


    private static TestItem initTestItem(String name) {

        // TODO: this
        return null;
    }

    public static void register() {
        // TODO: this
    }

}
