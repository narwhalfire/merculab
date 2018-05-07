package net.scottnotfound.merculab.chemical;

import net.minecraft.nbt.NBTTagCompound;
import net.scottnotfound.merculab.chemical.capability.IChemicalContainerProperties;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandler;

import javax.annotation.Nullable;

public class ChemicalContainer implements IChemicalContainer, IChemicalHandler
{
    private int capacity;

    @Nullable
    @Override
    public ChemicalStack getChemical()
    {
        return null;
    }

    @Override
    public int getChemicalAmount()
    {
        return 0;
    }

    @Override
    public int getCapacity()
    {
        return 0;
    }

    @Override
    public ChemicalContainerInfo getInfo()
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

    public void readFromNBT(NBTTagCompound tags)
    {
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
}
