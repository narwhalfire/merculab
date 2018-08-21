package net.scottnotfound.merculab.tool;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GenFileBlock extends GenFile {

    public GenFileBlock() {
        super();
    }

    @Override
    protected void initFile() {

        final String prefix = "import";
        final String fmlPackage = "net.minecraftforge.fml";
        final String blockPackage = "net.scottnotfound.merculab.block";

        importStrs = new LinkedList<>();

        importStrs.add(String.format("%s %s;", prefix, "net.minecraft.block.Block"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "common.registry.GameRegistry"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.Side"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.SideOnly"));
        importStrs.add(String.format("%s %s.%s;", prefix, blockPackage, "*"));
        importStrs.add(String.format("%s %s.%s;", prefix, blockPackage, "glassware.*"));
        importStrs.add(String.format("%s %s.%s;", prefix, blockPackage, "instrument.*"));
        importStrs.add(String.format("%s %s.%s;", prefix, blockPackage, "machine.*"));
        importStrs.add(String.format("%s %s.%s;", prefix, blockPackage, "storage.*"));
        importStrs.add("");
        importStrs.add(String.format("%s %s;", prefix, "java.util.ArrayList"));
        importStrs.add(String.format("%s %s;", prefix, "java.util.List"));

        packageStr = "package net.scottnotfound.merculab.init;";
        annotationStr = "@GameRegistry.ObjectHolder(\"merculab\")";
        classStr = "MercuLabBlocks";
        typeStr = "Block";

        srcFile = new File("src/main/resources/tool/generate_init/blocks/block_list.csv");
        destFile = new File("src/main/java/net/scottnotfound/merculab/init/MercuLabBlocks.java");

    }

    @Override
    protected List<String> buildMethRegister() {

        List<String> list = new LinkedList<>();

        list.add("public static void register() {");
        list.add("");
        list.add(prefixTab(String.format("for (%s %s : initList) {", typeStr, typeStr.toLowerCase())));
        list.add("");
        list.add(prefixTab(String.format("if (%s instanceof IMercuLabTileProv) {", typeStr.toLowerCase()), 2));
        list.add(prefixTab("GameRegistry.registerTileEntity(", 3));
        list.add(prefixTab(String.format("((IMercuLabTileProv) %s).getTileEntityClass(),", typeStr.toLowerCase()), 5));
        list.add(prefixTab(String.format("((IMercuLabTileProv) %s).getTileRegistryName());", typeStr.toLowerCase()), 5));
        list.add(prefixTab("}", 2));
        list.add("");
        list.add(prefixTab(String.format("GameRegistry.findRegistry(%s.class).register(%s);", typeStr, typeStr.toLowerCase()), 2));
        list.add("");
        list.add(prefixTab("}"));
        list.add("");
        list.add("}");

        return list;
    }

}
