package net.scottnotfound.merculab.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.scottnotfound.merculab.init.IInitializer;

public abstract class BlockChemicalBase extends Block implements IInitializer, ITileEntityProvider {

    public BlockChemicalBase(Material material) {
        super(material);
    }
}
