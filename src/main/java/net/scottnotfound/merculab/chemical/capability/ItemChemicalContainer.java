package net.scottnotfound.merculab.chemical.capability;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.chemical.capability.template.ChemicalHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A simple chemical container.
 */
public class ItemChemicalContainer extends Item {
    protected final int capacity;

    /**
     * @param capacity Maximum capacity of the chemical container.
     */
    public ItemChemicalContainer(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ChemicalHandlerItemStack(stack, capacity);
    }
}
