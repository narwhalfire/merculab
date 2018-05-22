package net.scottnotfound.merculab.chemical.capability;

import net.scottnotfound.merculab.chemical.ChemicalStack;
import net.scottnotfound.merculab.chemical.IChemicalContainer;

import javax.annotation.Nullable;

/**
 * Implement this interface as a capability which should handle chemicals, generally storing them
 * in one or more internal {@link IChemicalContainer} objects.
 */
public interface IChemicalHandler {
    /**
     * Returns an array of objects which represent the internal containers.
     * These objects cannot be used to manipulate the internal containers.
     *
     * @return Properties for the relevant internal containers.
     */
    IChemicalContainerProperties[] getContainerProperties();

    /**
     * Inserts chemical into internal containers, distribution is left entirely to the IChemicalHandler.
     *
     * @param resource ChemicalStack representing the Chemical and maximum amount of chemical to be inserted.
     * @param doInsert   If false, insert will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) inserted.
     */
    int insert(ChemicalStack resource, boolean doInsert);

    /**
     * Extracts chemical out of internal containers, distribution is left entirely to the IChemicalHandler.
     *
     * @param resource ChemicalStack representing the Chemical and maximum amount of chemical to be extracted.
     * @param doExtract  If false, extract will only be simulated.
     * @return ChemicalStack representing the Chemical and amount that was (or would have been, if
     * simulated) extracted.
     */
    @Nullable
    ChemicalStack extract(ChemicalStack resource, boolean doExtract);

    /**
     * Extracts chemical out of internal containers, distribution is left entirely to the IChemicalHandler.
     * <p/>
     * This method is not Chemical-sensitive.
     *
     * @param maxExtract Maximum amount of chemical to extract.
     * @param doExtract  If false, extract will only be simulated.
     * @return ChemicalStack representing the Chemical and amount that was (or would have been, if
     * simulated) extracted.
     */
    @Nullable
    ChemicalStack extract(int maxExtract, boolean doExtract);
}
