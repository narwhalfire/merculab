package net.scottnotfound.merculab.chemical;

import javax.annotation.Nullable;

/**
 * Wrapper class used to encapsulate information about an IChemicalContainer.
 */
public class ChemicalContainerInfo {
    @Nullable
    public final ChemicalStack chemical;
    public final int capacity;

    public ChemicalContainerInfo(@Nullable ChemicalStack chemical, int capacity) {
        this.chemical = chemical;
        this.capacity = capacity;
    }

    public ChemicalContainerInfo(IChemicalContainer container) {
        this.chemical = container.getChemical();
        this.capacity = container.getCapacity();
    }
}
