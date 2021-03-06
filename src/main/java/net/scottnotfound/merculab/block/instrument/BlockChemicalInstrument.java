package net.scottnotfound.merculab.block.instrument;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.scottnotfound.merculab.block.BlockChemicalBase;
import net.scottnotfound.merculab.init.MercuLab;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class BlockChemicalInstrument extends BlockChemicalBase {

    static final int BASE_CAPACITY = 1000;

    final int capacity = 1000;

    private final String name;

    public BlockChemicalInstrument(String name) {
        super(Material.ROCK);
        this.name = name;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(MercuLab.MOD_ID, name));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        //todo: show gui for container control
        return true;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        ItemStack newStack = new ItemStack(getItemDropped(state, new Random(), 0));

        NBTTagCompound tileEntityTag = new NBTTagCompound();

        if (te != null) {
            tileEntityTag = te.writeToNBT(tileEntityTag);
        }

        tileEntityTag.setInteger("x", 0);
        tileEntityTag.setInteger("y", 0);
        tileEntityTag.setInteger("z", 0);

        newStack.setTagInfo("BlockEntityTag", tileEntityTag);
        spawnAsEntity(worldIn, pos, stack);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        TileEntity tileEntity;

        try {
            Class<? extends TileEntity> tileEntityClass = this.getTileEntityClass();
            Constructor<? extends TileEntity> constructor = tileEntityClass.getConstructor();
            tileEntity = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            FMLLog.bigWarning("Fatal internal error.", e);
            FMLCommonHandler.instance().exitJava(1, false);
            tileEntity = null;
        }

        return tileEntity;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return null;
    }

    @Override
    public ResourceLocation getTileRegistryName() {
        return null;
    }
}
