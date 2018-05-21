package net.scottnotfound.merculab.item.glassware;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.chemical.capability.template.ChemicalHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *  The Vial is used to store fluid or solid material (or maybe even gaseous).
 *  Used as temporary storage.
 */
public class ItemVial extends Item
{

    private final int capacity;

    public ItemVial(int capacity)
    {
        this.capacity = capacity;
        this.setRegistryName("vial");
    }




    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ChemicalHandlerItemStack(stack, capacity);
    }

}
