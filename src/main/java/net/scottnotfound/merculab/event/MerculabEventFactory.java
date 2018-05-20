package net.scottnotfound.merculab.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.scottnotfound.merculab.event.entity.player.CollectChemicalEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MerculabEventFactory
{

    @Nullable
    public static ActionResult<ItemStack> onVialUse(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull ItemStack stack, @Nullable RayTraceResult target)
    {
        CollectChemicalEvent event = new CollectChemicalEvent(player, stack, world, target);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
        }

        if (event.getResult() == Event.Result.ALLOW)
        {
            if (player.capabilities.isCreativeMode)
            {
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
            }

            stack.shrink(1);
            if (stack.isEmpty())
            {
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, event.getFilledContainer());
            }

            if (!player.inventory.addItemStackToInventory(event.getFilledContainer()))
            {
                player.dropItem(event.getFilledContainer(), false);
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        }

        return null;
    }

}
