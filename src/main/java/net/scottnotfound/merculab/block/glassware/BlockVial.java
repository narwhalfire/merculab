package net.scottnotfound.merculab.block.glassware;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scottnotfound.merculab.chemical.capability.TileChemicalHandler;
import net.scottnotfound.merculab.init.MercuLabItems;

import java.util.Random;

public class BlockVial extends Block implements ITileEntityProvider
{

    private final int capacity;

    public BlockVial(int capacity)
    {
        super(Material.GLASS);
        this.capacity = capacity;
        this.setRegistryName("merculab:vial_block");
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return MercuLabItems.Vial;
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        //todo: implement this
        return true;
    }

    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        //todo: empty contents on shift click
        return true;
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack itemStack)
    {
        ItemStack newStack = new ItemStack(getItemDropped(state, new Random(), 0));

        NBTTagCompound tileEntityTag = new NBTTagCompound();

        if (tileEntity != null)
        {
            tileEntityTag = tileEntity.writeToNBT(tileEntityTag);
        }

        tileEntityTag.setInteger("x", 0);
        tileEntityTag.setInteger("y", 0);
        tileEntityTag.setInteger("z", 0);

        newStack.setTagInfo("BlockEntityTag", tileEntityTag);
        spawnAsEntity(worldIn, pos, newStack);
    }




    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileChemicalHandler();
    }
}
