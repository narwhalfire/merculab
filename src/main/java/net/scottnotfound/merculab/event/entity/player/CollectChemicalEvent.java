package net.scottnotfound.merculab.event.entity.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Cancelable
@Event.HasResult
public class CollectChemicalEvent extends PlayerEvent {

    private final ItemStack current;
    private final World world;
    @Nullable
    private final RayTraceResult target;

    private ItemStack result;

    public CollectChemicalEvent(EntityPlayer player, @Nonnull ItemStack current, World world, @Nullable RayTraceResult target) {
        super(player);
        this.current = current;
        this.world = world;
        this.target = target;
    }

    @Nonnull
    public ItemStack getEmptyContainer() {
        return this.current;
    }
    public World getWorld() {
        return this.world;
    }
    @Nullable
    public RayTraceResult getTarget() {
        return this.target;
    }
    @Nonnull
    public ItemStack getFilledContainer() {
        return this.result;
    }
    public void setFilledContainer(@Nonnull ItemStack container) {
        this.result = container;
    }

}
