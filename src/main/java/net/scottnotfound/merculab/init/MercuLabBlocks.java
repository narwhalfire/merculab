package net.scottnotfound.merculab.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.scottnotfound.merculab.block.IMercuLabTileProv;
import net.scottnotfound.merculab.block.glassware.BlockChemicalGlassware;

import java.util.ArrayList;
import java.util.List;

public class MercuLabBlocks {

    private static final List<Block> initList = new ArrayList<>();


    // BlockChemicalGlassware
    public static BlockChemicalGlassware glassware;


    public static void init() {

        initBlockChemicalGlasswares();

    }


    private static void initBlockChemicalGlasswares() {

        glassware               = initBlockChemicalGlassware("glassware");

    }

    private static BlockChemicalGlassware initBlockChemicalGlassware(String name) {

        BlockChemicalGlassware blockchemicalglassware = new BlockChemicalGlassware(name);
        blockchemicalglassware.setRegistryName(name);
        initList.add(blockchemicalglassware);

        return blockchemicalglassware;

    }

    public static void register() {

        for (Block block : initList) {

            if (block instanceof IMercuLabTileProv) {
                GameRegistry.registerTileEntity(
                        ((IMercuLabTileProv) block).getTileEntityClass(),
                        ((IMercuLabTileProv) block).getTileRegistryName());
            }

            GameRegistry.findRegistry(Block.class).register(block);

        }

    }

}
