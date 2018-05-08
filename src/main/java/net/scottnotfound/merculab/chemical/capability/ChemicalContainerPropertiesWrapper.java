package net.scottnotfound.merculab.chemical.capability;

import net.scottnotfound.merculab.chemical.ChemicalContainer;
import net.scottnotfound.merculab.chemical.ChemicalStack;

import javax.annotation.Nullable;

public class ChemicalContainerPropertiesWrapper implements IChemicalContainerProperties
{
    protected final ChemicalContainer container;

    public ChemicalContainerPropertiesWrapper(ChemicalContainer container)
    {
        this.container = container;
    }

    @Nullable
    @Override
    public ChemicalStack getContents()
    {
        ChemicalStack contents = container.getChemical();
        return contents == null ? null : contents.copy();
    }

    @Override
    public int getCapacity()
    {
        return container.getCapacity();
    }

    @Override
    public boolean canInsert()
    {
        return container.canInsert();
    }

    @Override
    public boolean canExtract()
    {
        return container.canExtract();
    }

    @Override
    public boolean canInsertChemicalType(ChemicalStack chemicalStack)
    {
        return container.canInsertChemicalType(chemicalStack);
    }

    @Override
    public boolean canExtractChemicalType(ChemicalStack chemicalStack)
    {
        return container.canExtractChemicalType(chemicalStack);
    }


}
