package net.scottnotfound.merculab.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.block.ItemBlockChemicalBase;
import net.scottnotfound.merculab.chemical.capability.template.ChemicalHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemBlockChemicalMachine extends ItemBlockChemicalBase {

    private final int capacity;

    public ItemBlockChemicalMachine(BlockChemicalMachine block) {
        super(block);
        this.capacity = block.capacity;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        IBlockState iBlockState = worldIn.getBlockState(pos);
        Block block = iBlockState.getBlock();

        //todo: check for special blocks that may handle this item differently and handle the behavior in that block's methods

        if (!block.isReplaceable(worldIn, pos)) {
            pos = pos.offset(facing);
        }

        ItemStack itemStack = player.getHeldItem(hand);

        if (!itemStack.isEmpty() && player.canPlayerEdit(pos, facing, itemStack)
            && worldIn.mayPlace(this.block, pos, false, facing, (Entity) null)) {

            int i = this.getMetadata(itemStack.getMetadata());
            IBlockState newState = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if (placeBlockAt(itemStack, player, worldIn, pos, facing, hitX, hitY, hitZ, newState)) {
                newState = worldIn.getBlockState(pos);
                SoundType soundType = newState.getBlock().getSoundType(newState, worldIn, pos, player);
                worldIn.playSound(player, pos, soundType.getPlaceSound(), SoundCategory.BLOCKS,
                                  (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
                itemStack.shrink(1);
            }

            return EnumActionResult.SUCCESS;

        } else {
            return EnumActionResult.FAIL;
        }

    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ChemicalHandlerItemStack(stack, capacity);
    }
}
