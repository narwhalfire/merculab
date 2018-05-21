package net.scottnotfound.merculab.block.glassware;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.scottnotfound.merculab.chemical.capability.TileChemicalHandler;

import javax.annotation.Nullable;

public class BlockVial extends Block implements ITileEntityProvider
{

    private final int capacity;

    public BlockVial(int capacity)
    {
        super(Material.GLASS);
        this.capacity = capacity;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileChemicalHandler();
    }
}
