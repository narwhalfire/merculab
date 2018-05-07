package net.scottnotfound.merculab.chemical.capability.template;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.chemical.ChemicalStack;
import net.scottnotfound.merculab.chemical.capability.IChemicalContainerProperties;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChemicalHandlerItemStack implements IChemicalHandlerItem, ICapabilityProvider
{
    public ChemicalHandlerItemStack()
    {
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack getContainer()
    {
        return null;
    }

    @Override
    public IChemicalContainerProperties[] getContainerProperties()
    {
        return new IChemicalContainerProperties[0];
    }

    @Override
    public int insert(ChemicalStack resource, boolean doInsert)
    {
        return 0;
    }

    @Nullable
    @Override
    public ChemicalStack extract(ChemicalStack resource, boolean doExtract)
    {
        return null;
    }

    @Nullable
    @Override
    public ChemicalStack extract(int maxExtract, boolean doExtract)
    {
        return null;
    }
}
