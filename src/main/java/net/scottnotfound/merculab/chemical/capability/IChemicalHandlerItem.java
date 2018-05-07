package net.scottnotfound.merculab.chemical.capability;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * ItemStacks handled by an {@link IChemicalHandler} may change, so this class allows
 * users of the chemical handler to get the container after it has been used.
 */
public interface IChemicalHandlerItem extends IChemicalHandler
{
    /**
     * Get the container currently acted on by this chemical handler.
     * The ItemStack may be different from its initial state, in the case of chemical containers that have different items
     * for their filled and empty states.
     * May be an empty item if the container was drained and is consumable.
     */
    @Nonnull
    ItemStack getContainer();
}
