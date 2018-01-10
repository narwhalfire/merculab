package net.scottnotfound.merculab.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.block.property.UnlistedPropertyBlockAvailable;
import net.scottnotfound.merculab.client.render.block.model.TestBakedModel;
import net.scottnotfound.merculab.init.MercuLabBlocks;
import net.scottnotfound.merculab.test.TestBlock;

public class TestBlockbakedmodel extends Block {

    public static final UnlistedPropertyBlockAvailable NORTH = new
            UnlistedPropertyBlockAvailable("north");
    public static final UnlistedPropertyBlockAvailable SOUTH = new
            UnlistedPropertyBlockAvailable("south");
    public static final UnlistedPropertyBlockAvailable WEST = new
            UnlistedPropertyBlockAvailable("west");
    public static final UnlistedPropertyBlockAvailable EAST = new
            UnlistedPropertyBlockAvailable("east");
    public static final UnlistedPropertyBlockAvailable UP = new
            UnlistedPropertyBlockAvailable("up");
    public static final UnlistedPropertyBlockAvailable DOWN = new
            UnlistedPropertyBlockAvailable("down");

    public TestBlockbakedmodel() {
        super(Material.ROCK);
        setUnlocalizedName(MercuLab.MODID + ".bakedmodelblock");
        setRegistryName("bakedmodelblock");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return TestBakedModel.TEST_BAKED_MODEL;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(MercuLab.MODID, "bakedmodelblock"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {

        double x1, y1, z1, x2, y2, z2;

        /*
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        boolean north = extendedBlockState.getValue(TestBlockbakedmodel.NORTH);
        boolean south = extendedBlockState.getValue(TestBlockbakedmodel.SOUTH);
        boolean west = extendedBlockState.getValue(TestBlockbakedmodel.WEST);
        boolean east = extendedBlockState.getValue(TestBlockbakedmodel.EAST);
        boolean up = extendedBlockState.getValue(TestBlockbakedmodel.UP);
        boolean down = extendedBlockState.getValue(TestBlockbakedmodel.DOWN);
        */
        double o = 0.4d;
        x1 = o;
        y1 = o;
        z1 = o;
        x2 = 1 - o;
        y2 = 1 - o;
        z2 = 1 - o;

        return new AxisAlignedBB(x1,y1,z1,x2,y2,z2);

    }

    @Override
    protected BlockStateContainer createBlockState() {
        IProperty[] listedProperties = new IProperty[0];
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] {NORTH, SOUTH, WEST, EAST, UP, DOWN};
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        boolean north = isSameBlock(world, pos.north());
        boolean south = isSameBlock(world, pos.south());
        boolean west = isSameBlock(world, pos.west());
        boolean east = isSameBlock(world, pos.east());
        boolean up = isSameBlock(world, pos.up());
        boolean down = isSameBlock(world, pos.down());

        return extendedBlockState
                .withProperty(NORTH, north)
                .withProperty(SOUTH, south)
                .withProperty(WEST, west)
                .withProperty(EAST, east)
                .withProperty(UP, up)
                .withProperty(DOWN, down)
                ;
    }

    private boolean isSameBlock(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == MercuLabBlocks.bakedModelBlock;
    }

}
