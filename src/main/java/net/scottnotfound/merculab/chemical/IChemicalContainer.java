package net.scottnotfound.merculab.chemical;

import javax.annotation.Nullable;

/**
 * A chemical container is the unit of interaction with chemical inventories.
 *
 * A reference implementation can be found at {@link ChemicalContainer}
 */
public interface IChemicalContainer
{
    /**
     * @return ChemicalStack representing the chemical in the container, null if the container is empty.
     */
    @Nullable
    ChemicalStack getChemical();

    /**
     * @return Current amount of chemical in the container.
     */
    int getChemicalAmount();

    /**
     * @return Capacity of this chemical container.
     */
    int getCapacity();

    /**
     * Returns a wrapper object {@link ChemicalContainerInfo } containing the capacity of the container and the
     * ChemicalStack it holds.
     *
     * Should prevent manipulation of the IChemicalContainer. See {@link ChemicalContainer}.
     *
     * @return State information for the IChemicalContainer.
     */
    ChemicalContainerInfo getInfo();

    /**
     *
     * @param resource
     *            ChemicalStack attempting to insert the container.
     * @param doInsert
     *            If false, the insert will only be simulated.
     * @return Amount of chemical that was accepted by the container.
     */
    int insert(ChemicalStack resource, boolean doInsert);

    /**
     *
     * @param maxExtract
     *            Maximum amount of chemical to be removed from the container.
     * @param doExtract
     *            If false, the extract will only be simulated.
     * @return Amount of chemical that was removed from the container.
     */
    @Nullable
    ChemicalStack extract(int maxExtract, boolean doExtract);
}
