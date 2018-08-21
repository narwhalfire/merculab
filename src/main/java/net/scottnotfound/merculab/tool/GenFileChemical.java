package net.scottnotfound.merculab.tool;

import java.io.File;
import java.util.LinkedList;

public class GenFileChemical extends GenFile {

    public GenFileChemical() {
        super();
    }

    @Override
    protected void initFile() {

        final String prefix = "import";
        final String fmlPackage = "net.minecraftforge.fml";

        importStrs = new LinkedList<>();

        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "common.registry.GameRegistry"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.Side"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.SideOnly"));
        importStrs.add(String.format("%s %s;", prefix, "net.scottnotfound.merculab.chemical.Chemical"));
        importStrs.add("");
        importStrs.add(String.format("%s %s;", prefix, "java.util.ArrayList"));
        importStrs.add(String.format("%s %s;", prefix, "java.util.List"));

        packageStr = "package net.scottnotfound.merculab.init;";
        annotationStr = "@GameRegistry.ObjectHolder(\"merculab\")";
        classStr = "MercuLabChemicals";
        typeStr = "Chemical";

        srcFile = new File("src/main/resources/tool/generate_init/chemicals/chemical_list.csv");
        destFile = new File("src/main/java/net/scottnotfound/merculab/init/MercuLabChemicals.java");

    }

}
