package net.scottnotfound.merculab.chemical;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ChemicalStorage implements IChemicalStorage {

    private List<PairChemicalAmount> chemicalAmounts = null;

    public ChemicalStorage() {}

    @Override
    public boolean canExtractChemicals() {
        return false;
    }

    @Override
    public boolean canRecieveChemicals() {
        return false;
    }

    @Override
    public long getMaxVolume() {
        return 0;
    }

    @Override
    public long getCurrentVolume() {
        return 0;
    }

    @Override
    public Collection<PairChemicalAmount> getChemicalAmountsStored() {
        return Collections.unmodifiableCollection(chemicalAmounts);
    }

    @Override
    public Collection<PairChemicalAmount> recieveChemicals(Collection<PairChemicalAmount> maxTransferred, boolean simulate) {

        long transferVolume = 0;
        float transferFraction = 1.0f;
        for (PairChemicalAmount transfer : maxTransferred) {
                    //todo: finish this
        }


        for (PairChemicalAmount pairChemicalAmount : chemicalAmounts) {
            for (PairChemicalAmount transfer : maxTransferred) {

            }
        }


        return null;
    }

    @Override
    public Collection<PairChemicalAmount> exctractChemicals(Collection<PairChemicalAmount> maxTransferred, boolean simulate) {
        return null;
    }

    @Override
    public Collection<PairChemicalAmount> extractVolume(long volume) {
        return null;
    }
}
