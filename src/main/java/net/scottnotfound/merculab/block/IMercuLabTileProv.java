package net.scottnotfound.merculab.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public interface IMercuLabTileProv extends ITileEntityProvider {

    Class<? extends TileEntity> getTileEntityClass();

    ResourceLocation getTileRegistryName();

}
