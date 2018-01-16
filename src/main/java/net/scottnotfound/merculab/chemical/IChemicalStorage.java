package net.scottnotfound.merculab.chemical;

import java.util.Collection;

public interface IChemicalStorage {

    /**
     *
     * @return true if chemicals may be extracted
     */
    boolean canExtractChemicals();

    /**
     *
     * @return true if chemicals may be received
     */
    boolean canRecieveChemicals();

    /**
     *
     * @return maximum volume of the container
     */
    long getMaxVolume();

    /**
     *
     * @return current volume of container occupied
     */
    long getCurrentVolume();

    /**
     *
     * @return the amounts of all chemicals stored
     */
    Collection<PairChemicalAmount> getChemicalAmountsStored();

    /**
     *
     * @param maxTransferred the maximum amount to be received
     * @param simulate transfer is simulated and does not actually occur
     * @return the amounts of chemicals received
     */
    Collection<PairChemicalAmount> recieveChemicals(Collection<PairChemicalAmount> maxTransferred, boolean simulate);

    /**
     *
     * @param maxTransferred the maximum amount to be extracted
     * @param simulate transfer is simulated and does not actually occur
     * @return the amounts of chemicals extracted
     */
    Collection<PairChemicalAmount> exctractChemicals(Collection<PairChemicalAmount> maxTransferred, boolean simulate);

    /**
     *
     * @param volume volume of stuff to be extracted in micro liters
     * @return the amounts of chemicals extracted
     */
    Collection<PairChemicalAmount> extractVolume(long volume);

}
