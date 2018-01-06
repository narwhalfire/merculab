package net.scottnotfound.merculab.util.chemistry.atom;


import net.scottnotfound.merculab.util.chemistry.element.IElement;


public class Atom implements IElement {
    /**
     * This class is part of the Graph representation of a molecule. It represents the vertex of a graph.
     */

    private int uniqueLabel;


    public Atom(int uniqueLabel) {
        super();
        this.uniqueLabel = uniqueLabel;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Atom)) return false;

        Atom n_object = (Atom) object;
        return n_object.uniqueLabel == uniqueLabel;
    }

    @Override
    public int hashCode() {
        return uniqueLabel;
    }

    public int getLabel() {
        return uniqueLabel;
    }

    public void setLabel(int uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

}
