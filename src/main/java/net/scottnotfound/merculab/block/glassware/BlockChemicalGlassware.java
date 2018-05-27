package net.scottnotfound.merculab.block.glassware;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.block.BlockChemicalBase;
import net.scottnotfound.merculab.init.MercuLabItems;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class BlockChemicalGlassware extends BlockChemicalBase {

    public static final PropertyEnum<EnumGlasswareType> VARIANT = PropertyEnum.create("variant", EnumGlasswareType.class);

    static final int BASE_CAPACITY = 1000;

    final int capacity = 1000;

    public BlockChemicalGlassware() {
        super(Material.GLASS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumGlasswareType.GLASS_VIAL));
        this.setCreativeTab(MercuLabItems.CHEM);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumGlasswareType glasswareType : EnumGlasswareType.values()) {
            items.add(new ItemStack(this, 1, glasswareType.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumGlasswareType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        //todo: empty contents on shift click
        return true;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack itemStack) {

        Item item = Item.getItemFromBlock(this);
        int i = 0;

        if (item.getHasSubtypes()) {
            i = this.getMetaFromState(state);
        }

        ItemStack newStack = new ItemStack(item, 1, i);

        NBTTagCompound tileEntityTag = new NBTTagCompound();

        if (te != null) {
            tileEntityTag = te.writeToNBT(tileEntityTag);
        }

        tileEntityTag.setInteger("x", 0);
        tileEntityTag.setInteger("y", 0);
        tileEntityTag.setInteger("z", 0);

        newStack.setTagInfo("BlockEntityTag", tileEntityTag);
        spawnAsEntity(worldIn, pos, newStack);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        try {
            return EnumGlasswareType.byMetadata(meta).getTileEntityClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void init() {

        // register this Block
        this.setRegistryName("glassware");
        ForgeRegistries.BLOCKS.register(this);

        // register this block's ItemBlock
        ItemBlock itemBlock = new ItemBlockChemicalGlassware(this);
        itemBlock.setRegistryName("glassware");
        ForgeRegistries.ITEMS.register(itemBlock);

        // register this block's TileEntities
        for (EnumGlasswareType glasswareType : EnumGlasswareType.values()) {
            GameRegistry.registerTileEntity(glasswareType.getTileEntityClass(), glasswareType.getName());
        }

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        for (EnumGlasswareType glasswareType : EnumGlasswareType.values()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    glasswareType.getMetadata(),
                    new ModelResourceLocation(
                            getRegistryName(),
                            "inventory"));
        }

    }
}
