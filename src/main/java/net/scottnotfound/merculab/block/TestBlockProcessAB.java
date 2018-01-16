package net.scottnotfound.merculab.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.tileentity.TestTileEntityProcessAB;

public class TestBlockProcessAB extends Block implements ITileEntityProvider {

    public static final int GUI_ID = 2;

    public TestBlockProcessAB() {
        super(Material.ROCK);
        setRegistryName("processab");
        setUnlocalizedName(MercuLab.MOD_ID + ".processab");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TestTileEntityProcessAB();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float htZ) {

        if (world.isRemote) {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!(tileEntity instanceof TestTileEntityProcessAB)) {
            return false;
        }
        player.openGui(MercuLab.instance, GUI_ID, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return true;
    }

}
