package net.scottnotfound.merculab.init;

import net.minecraft.block.Block;
import net.scottnotfound.merculab.block.glassware.BlockChemicalGlassware;

import java.util.ArrayList;
import java.util.List;

public class MercuLabBlocks {


    /* *** GENERATED START *** */

    private static final List<Block> initList = new ArrayList<>();

    // BlockChemicalGlassware
    public static BlockChemicalGlassware glassware;


    public static void init() {

        initBlockChemicalGlasswares();

    }

    private static void initBlockChemicalGlasswares() {

        glassware               = initBlockChemicalGlassware("glassware");

    }

    /* *** GENERATED END *** */


    private static BlockChemicalGlassware initBlockChemicalGlassware(String name) {

        // TODO: this
        return null;
    }

    public static void register() {
        // TODO: this
    }
    
}
