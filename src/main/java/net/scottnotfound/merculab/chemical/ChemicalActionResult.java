package net.scottnotfound.merculab.chemical;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Holds the result of a chemical action from {@link ChemicalUtil}.
 *
 * Failed actions will always have {@link #isSuccess()} == false and an empty ItemStack result. See {@link #FAILURE}.
 *
 * Successful actions will always have {@link #isSuccess()} == true.
 * Successful actions may have an empty ItemStack result in some cases,
 * for example the action succeeded and the resulting item was consumed.
 */
public class ChemicalActionResult {
    public static final ChemicalActionResult FAILURE = new ChemicalActionResult(false, ItemStack.EMPTY);

    public final boolean success;
    @Nonnull
    public final ItemStack result;

    public ChemicalActionResult(@Nonnull ItemStack result) {
        this(true, result);
    }

    private ChemicalActionResult(boolean success, @Nonnull ItemStack result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nonnull
    public ItemStack getResult() {
        return result;
    }
}
