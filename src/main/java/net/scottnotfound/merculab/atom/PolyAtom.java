package net.scottnotfound.merculab.atom;


import java.util.*;
import java.util.stream.Collectors;


public class PolyAtom {
    /**
     * This class is essentially a Graph data structure consisting of Atoms (vertices) and Bonds (edges).
     */

    private Set<Atom> atoms;
    private Set<Bond> bonds;
    private Map<Atom, Set<Bond>> adjacentList;


    public PolyAtom() {
        atoms = new HashSet<>();
        bonds = new HashSet<>();
        adjacentList = new HashMap<>();
    }

    public boolean addAtom(int label) {
        return atoms.add(new Atom(label));
    }

    public boolean addAtom(Atom atom) {
        return atoms.add(atom);
    }

    public boolean addAtoms(Collection<Atom> atoms) {
        return this.atoms.addAll(atoms);
    }

    public boolean removeAtom(int label) {
        return atoms.remove(new Atom(label));
    }

    public boolean removeAtom(Atom atom) {
        return atoms.remove(atom);
    }

    public boolean removeAtoms(Collection<Atom> atoms) {
        return this.atoms.removeAll(atoms);
    }

    public boolean addBond(Bond bond) {
        if (!bonds.add(bond)) return false;

        adjacentList.putIfAbsent(bond.atom_a, new HashSet<>());
        adjacentList.putIfAbsent(bond.atom_b, new HashSet<>());

        adjacentList.get(bond.atom_a).add(bond);
        adjacentList.get(bond.atom_b).add(bond);

        return true;
    }

    public boolean addBond(int atomLabelA, int atomLabelB) {
        return addBond(new Bond(new Atom(atomLabelA), new Atom(atomLabelB)));
    }

    public boolean removeBond(Bond bond) {
        if (!bonds.remove(bond)) return false;
        Set<Bond> bondsAtomA = adjacentList.get(bond.atom_a);
        Set<Bond> bondsAtomB = adjacentList.get(bond.atom_b);

        if (bondsAtomA != null) bondsAtomA.remove(bond);
        if (bondsAtomB != null) bondsAtomB.remove(bond);

        return true;
    }

    public boolean removeBond(int atomLabelA, int atomLabelB) {
        return removeBond(new Bond(new Atom(atomLabelA), new Atom(atomLabelB)));
    }

    public Set<Atom> getAdjacentAtoms(Atom atom) {
        return adjacentList.get(atom).stream().map(bond -> bond.atom_a.equals(atom) ? bond.atom_b : bond.atom_a).collect(Collectors.toSet());
    }

    public Set<Atom> getAtoms() {
        return Collections.unmodifiableSet(atoms);
    }

    public Set<Bond> getBonds() {
        return Collections.unmodifiableSet(bonds);
    }

    public Map<Atom, Set<Bond>> getAdjacentList() {
        return Collections.unmodifiableMap(adjacentList);
    }
}
