package net.scottnotfound.merculab.chemical.representation;


import net.scottnotfound.merculab.chemical.element.IElement;


public class CovalentAtom implements IElement {
    /**
     * This class is part of the Graph representation of a molecule. It represents the vertex of a graph.
     */

    private int uniqueLabel;


    public CovalentAtom(int uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CovalentAtom)) return false;

        CovalentAtom n_object = (CovalentAtom) object;
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
