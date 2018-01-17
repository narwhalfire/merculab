package net.scottnotfound.merculab.chemical;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChemicalStorage implements IChemicalStorage {

    private Map<String, ChemicalAmount> chemicalAmounts = new HashMap<>();

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
    public Map<String, ChemicalAmount> getChemicalAmountsStored() {
        return Collections.unmodifiableMap(chemicalAmounts);
    }

    @Override
    public Map<String, ChemicalAmount> recieveChemicals(Map<String, ChemicalAmount> maxTransferred, boolean simulate) {


        return null;
    }

    @Override
    public Map<String, ChemicalAmount> exctractChemicals(Map<String, ChemicalAmount> maxTransferred, boolean simulate) {
        return null;
    }

    @Override
    public Map<String, ChemicalAmount> extractVolume(long volume) {
        return null;
    }
}
