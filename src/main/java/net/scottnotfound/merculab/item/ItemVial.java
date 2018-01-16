package net.scottnotfound.merculab.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.init.MercuLabItems;

import javax.annotation.Nullable;

public class ItemVial extends Item {
    /**
     *  The Vial is used to store fluid or solid material (or maybe even gaseous).
     *  Used as temporary storage.
     */


    private Block containedBlock;

    public ItemVial(Block containedBlock) {
        this.setRegistryName("vial");
        this.setUnlocalizedName(MercuLab.MOD_ID + ".vial");

        this.maxStackSize = 1;
        this.containedBlock = containedBlock;
        this.setCreativeTab(MercuLabItems.CHEM);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        boolean flag = this.containedBlock == Blocks.AIR;
        ItemStack itemStack = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = this.rayTrace(world, player, flag);

        if (rayTraceResult == null) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
        } else if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStack);
        } else {
            BlockPos pos = rayTraceResult.getBlockPos();

            if (flag) {
                if (!player.canPlayerEdit(pos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, itemStack)) {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                } else {
                    IBlockState iBlockState = world.getBlockState(pos);
                    Material material = iBlockState.getMaterial();

                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                    Block block = iBlockState.getBlock();
                    this.containedBlock = block;
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillVial(itemStack, player, itemStack.getItem()));
                }
            } else {
                boolean flag1 = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
                BlockPos pos1 = flag1 && rayTraceResult.sideHit == EnumFacing.UP ? pos : pos.offset(rayTraceResult.sideHit);

                if (!player.canPlayerEdit(pos1, rayTraceResult.sideHit, itemStack)) {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                } else if (this.tryPlaceContained(player, world, pos1)) {
                    if (player instanceof EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos1, itemStack);
                    }

                    return !player.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(this)) : new ActionResult(EnumActionResult.SUCCESS, itemStack);
                } else {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStack);
                }
            }

        }

    }



    public boolean tryPlaceContained(@Nullable EntityPlayer player, World world, BlockPos pos) {
        if (this.containedBlock == Blocks.AIR) {
            return false;
        } else {
            IBlockState iBlockState = world.getBlockState(pos);
            Material material = iBlockState.getMaterial();
            boolean flag = !material.isSolid();
            boolean flag1 = iBlockState.getBlock().isReplaceable(world, pos);

            world.setBlockState(pos, this.containedBlock.getDefaultState(), 11);
            this.containedBlock = Blocks.AIR;
            return true;
        }
    }

    private ItemStack fillVial(ItemStack emptyVials, EntityPlayer player, Item fullVial) {

        if (player.capabilities.isCreativeMode) {
            return emptyVials;
        } else {
            emptyVials.shrink(1);
            if (emptyVials.isEmpty()) {
                return new ItemStack(fullVial);
            } else {
                if (!player.inventory.addItemStackToInventory(new ItemStack(fullVial))) {
                    player.dropItem(new ItemStack(fullVial), false);
                }

                return emptyVials;
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {

        return super.initCapabilities(stack, nbt);
    }
}
