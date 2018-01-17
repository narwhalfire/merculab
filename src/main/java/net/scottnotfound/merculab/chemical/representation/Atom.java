package net.scottnotfound.merculab.chemical.representation;

import net.scottnotfound.merculab.chemical.element.Element;

public class Atom {

    public final Element ELEMENT;

    public Atom() {
        this.ELEMENT = null;
    }

    public Atom(Element element) {
        this.ELEMENT = element;
    }
}
