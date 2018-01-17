package net.scottnotfound.merculab.chemical.representation;

public class Bond {

    protected Atom atom_a;
    protected Atom atom_b;


    public Bond() {

    }

    public Bond(Atom atom_a, Atom atom_b) {
        this.atom_a = atom_a;
        this.atom_b = atom_b;
    }
}
