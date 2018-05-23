package net.scottnotfound.merculab.init;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.block.TestBlockProcessAB;
import net.scottnotfound.merculab.block.TestBlockbakedmodel;
import net.scottnotfound.merculab.block.glassware.BlockChemicalGlassware;
import net.scottnotfound.merculab.test.TestBlock;
import net.scottnotfound.merculab.test.TestContainerBlock;

import java.util.ArrayList;

public class MercuLabBlocks {

    private static ArrayList<IInitializer> initList = new ArrayList<>();

    public static TestBlock testBlock;
    public static TestContainerBlock testContainerBlock;
    public static TestBlockProcessAB testBlockProcessAB;
    public static TestBlockbakedmodel bakedModelBlock;

    public static BlockChemicalGlassware vial;


    private static void preInitBlocks() {

        testBlock = new TestBlock();
        testContainerBlock = new TestContainerBlock();
        testBlockProcessAB = new TestBlockProcessAB();
        bakedModelBlock = new TestBlockbakedmodel();

        vial = new BlockChemicalGlassware("vial");

    }

    private static void preInitList() {

        initList.add(testBlock);
        initList.add(testContainerBlock);
        initList.add(testBlockProcessAB);
        initList.add(bakedModelBlock);

        initList.add(vial);

    }

    public static void initBlocks() {

        preInitBlocks();
        preInitList();

        for (IInitializer init : initList) {
            init.init();
        }

    }

    public static void registerBlocks() {



    }

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