package net.scottnotfound.merculab.chemical;

public class PairChemicalAmount {
    public Chemical chemical;
    public long amount;
    /**
     *
     * @param chemical the chemical in storage
     * @param amount the amount of the chemical in nano moles
     */
    public PairChemicalAmount(Chemical chemical, long amount) {
        this.chemical = chemical;
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return chemical.hashCode();
    }

    public Chemical getChemical() {
        return chemical;
    }

    /**
     *
     * @return amount of chemical in nano moles
     */
    public long getAmount() {
        return amount;
    }

    /**
     *
     * @return mass of chemical in grams
     */
    public long getMass() {
        return 0;
    }
}