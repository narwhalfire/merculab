package net.scottnotfound.merculab.compounds;

public class Compound {

    private String IUPACname;
    private String CASnumber;
    private String chemicalFormula;
    private String appearance;
    private String odor;
    private float molarMass;
    private float density;
    private float meltingPoint;
    private float solubility;


    public Compound(String IUPACname, String CASnumber, String chemicalFormula, String appearance, String odor,
                    float molarMass, float density, float meltingPoint, float solubility) {

        this.IUPACname = IUPACname;
        this.CASnumber = CASnumber;
        this.chemicalFormula = chemicalFormula;
        this.appearance = appearance;
        this.odor = odor;
        this.molarMass = molarMass;
        this.density = density;
        this.meltingPoint = meltingPoint;
        this.solubility = solubility;

    }






    public static final Compound SodiumChloride = new Compound("sodium chloride", "7647-14-5",
            "NaCl", "colorless crystals", "none", 58.440f, 2.1650f,
            801.0f, 359.0f);
}
