package net.scottnotfound.merculab.chemical;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IRegistryDelegate;
import net.scottnotfound.merculab.MercuLab;

import javax.annotation.Nullable;

/**
 * ItemStack substitute for Chemicals.
 *
 * NOTE: Equality is based on the chemical, not the amount.
 *
 */
public class ChemicalStack
{

    public int amount;
    public NBTTagCompound tag;
    private IRegistryDelegate<Chemical> chemicalDelegate;

    public ChemicalStack(Chemical chemical, int amount)
    {
        if (chemical == null)
        {
            FMLLog.bigWarning("Null chemical supplied to chemicalstack. Did you try and create a stack for an unknown registered chemical?");
            throw new IllegalArgumentException("Cannot create a chemicalstack from a null chemical");
        }
        else if (!GameRegistry.findRegistry(Chemical.class).containsValue(chemical))
        {
            FMLLog.bigWarning("Failed to attempt to create a ChemicalStack for an unregistered Chemical {} (type {})",
                              chemical.getName(), chemical.getClass().getName());
            throw new IllegalArgumentException("Cannot create a chemicalstack from an unregistered chemical");
        }
        this.chemicalDelegate = chemical.delegate;
        this.amount = amount;
    }

    public ChemicalStack(Chemical chemical, int amount, NBTTagCompound nbt)
    {
        this(chemical, amount);

        if (nbt != null)
        {
            tag = (NBTTagCompound) nbt.copy();
        }
    }

    public ChemicalStack(ChemicalStack stack, int amount)
    {
        this(stack.getChemical(), amount, stack.tag);
    }

    @Nullable
    public static ChemicalStack loadChemicalStackFromNBT(NBTTagCompound nbt)
    {
        if (nbt == null)
        {
            return null;
        }
        if (!nbt.hasKey("ChemicalName", Constants.NBT.TAG_STRING))
        {
            return null;
        }
        String chemicalName = nbt.getString("ChemicalName");
        ResourceLocation resourceLocation = new ResourceLocation(MercuLab.MOD_ID, chemicalName);
        if (Chemical.REGISTRY.getObject(resourceLocation) == null)
        {
            return null;
        }
        ChemicalStack stack = new ChemicalStack(Chemical.REGISTRY.getObject(resourceLocation), nbt.getInteger("Amount"));
        if (nbt.hasKey("Tag"))
        {
            stack.tag = nbt.getCompoundTag("Tag");
        }
        return stack;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setString("ChemicalName", getChemical().chemicalName);
        nbt.setInteger("Amount", amount);

        if (this.tag != null)
        {
            nbt.setTag("Tag", this.tag);
        }
        return nbt;
    }

    public Chemical getChemical()
    {
        return chemicalDelegate.get();
    }

    public String getLocalizedName()
    {
        return this.getChemical().getLocalizedName(this);
    }

    public String getUnlocalizedName()
    {
        return this.getChemical().getUnlocalizedName(this);
    }

    /**
     * @return A copy of this ChemicalStack
     */
    public ChemicalStack copy()
    {
        return new ChemicalStack(getChemical(), amount, tag);
    }

    /**
     * Determines if the ChemicalIDs and NBT Tags are equal. This does not check amounts.
     *
     * @param other The ChemicalStack for comparison.
     * @return true if the Chemicals (IDs and NBT Tags) are the same
     */
    public boolean isChemicalEqual(@Nullable ChemicalStack other)
    {
        return other != null && getChemical() == other.getChemical() && isChemicalStackTagEqual(other);
    }

    private boolean isChemicalStackTagEqual(ChemicalStack other)
    {
        return tag == null ? other.tag == null : other.tag != null && tag.equals(other.tag);
    }

    /**
     * Determines if the NBT Tags are equal. Useful if the ChemicalIDs are known to be equal.
     */
    public static boolean areChemicalStackTagsEqual(@Nullable ChemicalStack stack1, @Nullable ChemicalStack stack2)
    {
        return stack1 == null && stack2 == null || (stack1 != null && stack2 != null && stack1.isChemicalStackTagEqual(stack2));
    }

    /**
     * Determines if the chemicals are equal and this stack is larger.
     *
     * @param other The stack to compare
     * @return true of this ChemicalStack contains the other ChemicalStack (same chemical and >= amount)
     */
    public boolean containsChemical(@Nullable ChemicalStack other)
    {
        return isChemicalEqual(other) && amount >= (other != null ? other.amount : 0);
    }

    /**
     * Determines if the ChemicalIDs, Amounts, and NBT Tags are all equal.
     *
     * @param other the ChemicalStack for comparison
     * @return true if the two ChemicalStacks are exactly the same
     */
    public boolean isChemicalStackIdentical(ChemicalStack other)
    {
        return isChemicalEqual(other) && amount == other.amount;
    }

    /**
     * Determines if the ChemicalIDs and NBT Tags are equal compared to a registered container
     * ItemStack. This does not check amounts.
     *
     * @param other The ItemStack for comparison
     * @return true if the Chemicals (IDs and NBT Tags) are the same
     */
    public boolean isChemicalEqual(ItemStack other)
    {
        if (other == null)
        {
            return false;
        }

        return false;//isChemicalEqual(ChemicalUtil.getChemicalContained(other));
    }

    @Override
    public final int hashCode()
    {
        int code = 1;
        code = 31*code + getChemical().hashCode();
        code = 31*code + amount;
        if (tag != null)
        {
            code = 31*code + tag.hashCode();
        }
        return code;
    }

    /**
     * Default equality comparison for a ChemicalStack. Same functionality as isChemicalEqual().
     *
     * This is included for use in data structures.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ChemicalStack))
        {
            return false;
        }

        return isChemicalEqual((ChemicalStack) obj);
    }
}
