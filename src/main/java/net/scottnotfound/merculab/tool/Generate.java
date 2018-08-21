package net.scottnotfound.merculab.tool;

import java.util.ArrayList;
import java.util.List;

public class Generate {

    private static final List<GenFile> files = new ArrayList<>();

    public static void main(String[] args) {

        initFiles();
        generateFiles();

    }

    private static void initFiles() {

        files.add(new GenFileChemical());
        files.add(new GenFileBlock());
        files.add(new GenFileItem());

    }

    private static void generateFiles() {

        for (GenFile file : files) {
            file.generateFile();
        }

    }


}
