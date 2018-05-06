

package net.scottnotfound.merculab.chemical;

/**
 *  This is the class that will represent any kind of chemical, be it an individual molecule,
 * a simple crystal, or a complex crystal consisting of polyatomic ions.
 */
public class Chemical {

    String IUPACname;
    String commonName;

    private boolean isPolyatomic;
    private boolean isNetworked;
    private boolean isSalt;
    private boolean isProtic;
    private boolean isPolar;
    private boolean isCharged;

    private int charge;

    //todo: Figure out how I'm going to efficiently represent metallic, ionic, and covalent bonds, including a mix.
    /*
    Could use electronegativity to roughly determine bond type but class PolyAtom is built more for covalent types.
    A graph would not work for a crystalline or metallic structure. Could use several different data structures to
    represent the different bond types and restrict bonds to being only metallic, ionic, or covalent and not a blend.
     */
}
