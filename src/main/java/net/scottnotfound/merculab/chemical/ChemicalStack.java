package net.scottnotfound.merculab.chemical;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.registries.IRegistryDelegate;

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

    }

}
