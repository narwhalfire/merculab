package net.scottnotfound.merculab.item;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.scottnotfound.merculab.chemical.Chemical;
import net.scottnotfound.merculab.event.MerculabEventFactory;

/**
 *  The Vial is used to store fluid or solid material (or maybe even gaseous).
 *  Used as temporary storage.
 */
public class ItemVial extends Item
{

    private final int capacity;
    private final Chemical containedChemical;

    public ItemVial(int capacity, Chemical chemical)
    {
        this.capacity = capacity;
        this.containedChemical = chemical;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.MISC);
        this.setRegistryName("vial");
    }

    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        boolean flag = this.containedChemical == null;
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, flag);
        ActionResult<ItemStack> ret = MerculabEventFactory.onVialUse(playerIn, worldIn, itemStack, rayTraceResult);
        if (ret != null)
        {
            return ret;
        }

        if (rayTraceResult == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
        }
        else if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
        }
        else
        {
            BlockPos blockPos = rayTraceResult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockPos))
            {
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
            }
            else if (flag)
            {
                if (!playerIn.canPlayerEdit(blockPos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, itemStack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                }
                else
                {
                    IBlockState iBlockState = worldIn.getBlockState(blockPos);
                    Material material = iBlockState.getMaterial();

                    /* water and lava checking for the bucket */

                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                }
            }
            else
            {
                boolean flag1 = worldIn.getBlockState(blockPos).getBlock().isReplaceable(worldIn, blockPos);
                BlockPos blockPos1 = flag1 && rayTraceResult.sideHit == EnumFacing.UP ? blockPos : blockPos.offset(rayTraceResult.sideHit);

                if (!playerIn.canPlayerEdit(blockPos1, rayTraceResult.sideHit, itemStack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                }
                else
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                }
            }
        }
    }

}
