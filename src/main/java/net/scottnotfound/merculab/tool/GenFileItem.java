package net.scottnotfound.merculab.tool;

import java.io.File;
import java.util.LinkedList;

public class GenFileItem extends GenFile {

    public GenFileItem() {
        super();
    }

    @Override
    protected void initFile() {

        final String prefix = "import";
        final String fmlPackage = "net.minecraftforge.fml";
        final String itemPackage = "net.scottnotfound.merculab.item";

        importStrs = new LinkedList<>();

        importStrs.add(String.format("%s %s;", prefix, "net.minecraft.item.Item"));
        importStrs.add(String.format("%s %s;", prefix, "net.scottnotfound.merculab.test.TestItem"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "common.registry.GameRegistry"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.Side"));
        importStrs.add(String.format("%s %s.%s;", prefix, fmlPackage, "relauncher.SideOnly"));
        importStrs.add(String.format("%s %s.%s;", prefix, itemPackage, "*"));
        importStrs.add("");
        importStrs.add(String.format("%s %s;", prefix, "java.util.ArrayList"));
        importStrs.add(String.format("%s %s;", prefix, "java.util.List"));

        packageStr = "package net.scottnotfound.merculab.init;";
        annotationStr = "@GameRegistry.ObjectHolder(\"merculab\")";
        classStr = "MercuLabItems";
        typeStr = "Item";

        srcFile = new File("src/main/resources/tool/generate_init/items/item_list.csv");
        destFile = new File("src/main/java/net/scottnotfound/merculab/init/MercuLabItems.java");

    }

}
