package scottnotfound.enderappliedchemistry.elements;


public class Element {

    private String name;
    private String symbol;

    private int atomicNumber;
    private float averageMass;

    private int group;
    private int period;
    private String electronicConfig;

    public Element() {

    }

    public Element(String name, String symbol, int atomicNumber, float averageMass, int group,
                   int period, String electronicConfig) {

        this.name = name;
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.averageMass = averageMass;
        this.group = group;
        this.period = period;
        this.electronicConfig = electronicConfig;

    }





    public static final Element HYDROGEN = new Element("Hydrogen", "H", 1, 1.008f,
            1, 1, "(1s^1)");
    public static final Element HELIUM = new Element("Helium", "He", 2, 4.0026f,
            18, 1, "(1s^2)");



    public static final Element CARBON = new Element("Carbon", "C", 6, 12.011f,
            14, 2, "[He](2s^2)(2p^2)");
    public static final Element NITROGEN = new Element("Nitrogen", "N", 7, 14.007f,
            15, 2, "[He](2s^2)(2p^3)");
    public static final Element OXYGEN = new Element("Oxygen", "O", 8, 15.999f,
            16, 2, "[He](2s^2)(2p^4)");
    public static final Element FLUORINE = new Element("Fluorine", "F", 9, 18.998f,
            17, 2, "[He](2s^2)(2p^5)");

}
