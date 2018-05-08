package net.scottnotfound.merculab.chemical.capability.template;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.scottnotfound.merculab.chemical.ChemicalStack;
import net.scottnotfound.merculab.chemical.capability.CapabilityChemicalHandler;
import net.scottnotfound.merculab.chemical.capability.IChemicalContainerProperties;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChemicalHandlerItemStack implements IChemicalHandlerItem, ICapabilityProvider
{
    public static final String CHEMICAL_NBT_KEY = "Chemical";

    @Nonnull
    protected ItemStack container;
    protected int capacity;

    /**
     * @param container The container itemStack, data is stored on it directly as NBT.
     * @param capacity The maximum capacity of this chemical container.
     */
    public ChemicalHandlerItemStack(@Nonnull ItemStack container, int capacity)
    {
        this.container = container;
        this.capacity = capacity;
    }

    @Nullable
    public ChemicalStack getChemical()
    {
        NBTTagCompound tagCompound = container.getTagCompound();
        if (tagCompound == null || !tagCompound.hasKey(CHEMICAL_NBT_KEY))
        {
            return null;
        }
        return ChemicalStack.loadChemicalStackFromNBT(tagCompound.getCompoundTag(CHEMICAL_NBT_KEY));
    }

    protected void setChemical(ChemicalStack chemical)
    {
        if (!container.hasTagCompound())
        {
            container.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound chemicalTag = new NBTTagCompound();
        chemical.writeToNBT(chemicalTag);
        container.getTagCompound().setTag(CHEMICAL_NBT_KEY, chemicalTag);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_ITEM_CAPABILITY;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_ITEM_CAPABILITY ? (T) this : null;
    }

    @Nonnull
    @Override
    public ItemStack getContainer()
    {
        return container;
    }

    @Override
    public IChemicalContainerProperties[] getContainerProperties()
    {
        return new IChemicalContainerProperties[0];
    }

    @Override
    public int insert(ChemicalStack resource, boolean doInsert)
    {
        if (container.getCount() != 1 || resource == null || resource.amount <= 0 || !canInsertChemicalType(resource))
        {
            return 0;
        }

        ChemicalStack contained = getChemical();
        if (contained == null)
        {
            int insertAmount = Math.min(capacity, resource.amount);

            if (doInsert)
            {
                ChemicalStack inserted = resource.copy();
                inserted.amount = insertAmount;
                setChemical(inserted);
            }

            return insertAmount;
        }
        else
        {
            if (contained.isChemicalEqual(resource))
            {
                int insertAmount = Math.min(capacity - contained.amount, resource.amount);

                if (doInsert && insertAmount > 0)
                {
                    contained.amount += insertAmount;
                    setChemical(contained);
                }

                return insertAmount;
            }

            return 0;
        }
    }

    @Nullable
    @Override
    public ChemicalStack extract(ChemicalStack resource, boolean doExtract)
    {
        if (container.getCount() != 1 || resource == null || resource.amount <= 0 || !resource.isChemicalEqual(getChemical()))
        {
            return null;
        }
        return extract(resource.amount, doExtract);
    }

    @Nullable
    @Override
    public ChemicalStack extract(int maxExtract, boolean doExtract)
    {
        if (container.getCount() != 1 || maxExtract <= 0)
        {
            return null;
        }

        ChemicalStack contained = getChemical();
        if (contained == null || contained.amount <= 0 || !canExtractChemicalType(contained))
        {
            return null;
        }

        final int extractAmount = Math.min(contained.amount, maxExtract);

        ChemicalStack extracted = contained.copy();
        extracted.amount = extractAmount;

        if (doExtract)
        {
            contained.amount -= extractAmount;
            if (contained.amount == 0)
            {
                setContainerToEmpty();
            }
            else
            {
                setChemical(contained);
            }
        }

        return extracted;
    }

    public boolean canInsertChemicalType(ChemicalStack chemical)
    {
        return true;
    }

    public boolean canExtractChemicalType(ChemicalStack chemical)
    {
        return true;
    }

    protected void setContainerToEmpty()
    {
        if (container.getTagCompound() != null)
        {
            container.getTagCompound().removeTag(CHEMICAL_NBT_KEY);
        }
    }

    public static class Consumable extends ChemicalHandlerItemStack
    {
        public Consumable(ItemStack container, int capacity)
        {
            super(container, capacity);
        }

        @Override
        protected void setContainerToEmpty()
        {
            super.setContainerToEmpty();
            container.shrink(1);
        }
    }

    public static class SwapEmpty extends ChemicalHandlerItemStack
    {
        protected final ItemStack emptyContainer;

        public SwapEmpty(ItemStack container, ItemStack emptyContainer, int capacity)
        {
            super(container, capacity);
            this.emptyContainer = emptyContainer;
        }

        @Override
        protected void setContainerToEmpty()
        {
            super.setContainerToEmpty();
            container = emptyContainer;
        }
    }
}
