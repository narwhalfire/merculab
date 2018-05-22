package net.scottnotfound.merculab.chemical.capability;

import net.scottnotfound.merculab.chemical.ChemicalStack;

import javax.annotation.Nullable;

/**
 * Very similar to Forge's {@link net.minecraftforge.fluids.capability.IFluidTankProperties}.
 */
public interface IChemicalContainerProperties {
    /**
     * @return A copy of the chemical contents of this container. May be null.
     * To modify the contents, use {@link IChemicalHandler}
     */
    @Nullable
    ChemicalStack getContents();

    /**
     * @return The maximum amount of chemical this container can hold.
     */
    int getCapacity();

    /**
     * Returns true if the container can be inserted at any time.
     * It does not consider the contents or capacity of the container.
     *
     * This value is constant. If the container behavior is more complicated, returns true.
     */
    boolean canInsert();

    /**
     * returns true if the container can be extracted at any time.
     * It does not consider the contents or capacity of the container.
     *
     * This value is constant. If the container behavior is more complicated, returns true.
     */
    boolean canExtract();

    /**
     * Returns true fo the container can be inserted with a specific type of chemical.
     * Used as a filter for chemical types.
     *
     * Does not consider the current contents or capacity of the container,
     * only whether it could ever add with this type of chemical.
     * {@link ChemicalStack} is used here because chemical properties can depend on NBT, the amount is ignored.
     */
    boolean canInsertChemicalType(ChemicalStack chemicalStack);

    /**
     * Returns true fo the container can remove out this specific type of chemical.
     * Used as a filter for chemical types.
     *
     * Does not consider the current contents or capacity of the container,
     * only whether it could ever add with this type of chemical.
     * {@link ChemicalStack} is used here because chemical properties can depend on NBT, the amount is ignored.
     */
    boolean canExtractChemicalType(ChemicalStack chemicalStack);
}
