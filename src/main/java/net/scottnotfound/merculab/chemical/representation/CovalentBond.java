package net.scottnotfound.merculab.chemical.representation;

public class CovalentBond extends Bond {
    /**
     * This class is a part of the Graph representation of a molecule. It represents the edge of a graph.
     */

    private static final int DEFAULT_WEIGHT = 1;
    private int weight;

    public CovalentBond(CovalentAtom atom_a, CovalentAtom atom_b) {
        this(atom_a, atom_b, DEFAULT_WEIGHT);
    }

    public CovalentBond(CovalentAtom atom_a, CovalentAtom atom_b, int weight) {
        super(atom_a, atom_b);
        this.weight = weight;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CovalentBond)) return false;

        CovalentBond n_object = (CovalentBond) object;
        return n_object.atom_a.equals(this.atom_a) && n_object.atom_b.equals(this.atom_b) && n_object.weight == this.weight;
    }

    @Override
    public int hashCode() {
        int result = atom_a.hashCode();
        result = 31 * result + atom_b.hashCode();
        result = 31 * result + weight;
        return result;
    }

}
