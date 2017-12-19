package net.scottnotfound.merculab.atom;

public class Bond {

    private static final int DEFAULT_WEIGHT = 1;


    Atom atom_a;
    Atom atom_b;
    int weight;

    public Bond(Atom atom_a, Atom atom_b) {
        this(atom_a, atom_b, DEFAULT_WEIGHT);
    }

    public Bond(Atom atom_a, Atom atom_b, int weight) {
        super();
        this.atom_a = atom_a;
        this.atom_b = atom_b;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Bond)) return false;

        Bond n_object = (Bond) object;
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
