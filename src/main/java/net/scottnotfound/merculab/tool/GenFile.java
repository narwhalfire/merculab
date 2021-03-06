package net.scottnotfound.merculab.tool;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class GenFile {

    protected Map<String,List<String>> mapInit;
    protected List<String> importStrs = new LinkedList<>();
    protected List<String> destLines = null;
    protected String packageStr = "";
    protected String annotationStr = "";
    protected String classStr = "";
    protected String typeStr = "";

    protected File srcFile = null;
    protected File destFile = null;
    
    private static final String MARK_START = "/* *** GENERATED START *** */";
    private static final String MARK_END = "/* *** GENERATED END *** */";

    private int genStart = -1;
    private int genEnd = -1;

    GenFile() {
        initFile();
        mapInit = buildMap(readInitList());
    }

    protected abstract void initFile();

    /**
     * Searches to see if the destination file already exists. If it does already exist, then it searches that
     * file for the markers that mark the beginning (start) and ending (end) of the section of generated code
     * that is replaceable. The section between the markers is what will be updated and replaced upon
     * regeneration of the file. Code lying outside these markers will be left alone.
     *
     * @return List of all lines of the destination file that already exists.
     * @throws FileNotFoundException thrown when the destination file does not exist
     * @throws FileAlreadyExistsException thrown when the destination file exists with no markers
     */
    protected List<String> scrapeDest() throws FileNotFoundException, FileAlreadyExistsException {

        FileReader fileReader = new FileReader(destFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        destLines = bufferedReader.lines().collect(Collectors.toCollection(LinkedList::new));

        for (int i = 0; i < destLines.size(); i++) {
            String line = destLines.get(i).trim();
            if (line.equals(MARK_START)) {
                genStart = i;
                genEnd = i;
            }
            if (line.equals(MARK_END)) {
                genEnd = i;
            }
        }

        if (genStart == -1 || genEnd == -1) {
            throw new FileAlreadyExistsException("Generated file exists but no generated markers found.");
        }

        return destLines;
    }

    /**
     * Attempts to regenerate the file at the destination. Generates a new file if destination does not exist.
     */
    protected void genFile() {

        try {
            regenFile();
        } catch (FileNotFoundException e) {
            genNewFile();
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        }

    }

    /**
     * Attempts to regenerate the file at the destination by replacing the code between the markers.
     * Throws exceptions from scrapeDest()
     *
     * @see #scrapeDest()
     */
    protected void regenFile() throws FileNotFoundException, FileAlreadyExistsException {

        List<String> newList = new LinkedList<>();
        List<String> oldList = scrapeDest();
        newList.addAll(oldList.subList(0, genStart));
        newList.addAll(buildRegen());
        newList.addAll(oldList.subList(genEnd+1, oldList.size()));

        try {
            Files.write(destFile.toPath(), newList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void genNewFile() {

        try {
            Files.write(destFile.toPath(), buildNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected List<String> readInitList() {

        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(srcFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (new BufferedReader(new InputStreamReader(inputStream))).lines()
                                                                       .filter(Predicate.isEqual("").negate())
                                                                       .collect(Collectors.toList());

    }

    /**
     * Organizes a list of strings into a map with sections of strings. A line surrounded in square brackets is
     * the beginning of a new section with name being that of the string inside those brackets. All following
     * lines are added to that section until a new section is found. In the case that a section is found that
     * has already been placed into the map, the lines in that newly found section will be appended to the one
     * under the same name that already exists.
     *
     * @param list lines from file
     * @return sectioned lines
     */
    protected Map<String,List<String>> buildMap(List<String> list) {
        Map<String,List<String>> map = new HashMap<>();

        String k = null;
        List<String> v = new LinkedList<>();
        for (String string : list) {
            String s = string.trim();
            if (s.length() >= 2 && s.charAt(0) == '[' && s.charAt(s.length() - 1) == ']') {
                if (k != null) {
                    if (map.containsKey(k)) {
                        map.get(k).addAll(v);
                    } else {
                        map.put(k, v);
                    }
                }
                k = s.substring(1, s.length() - 1);
                v = new LinkedList<>();
            } else {
                v.add(s);
            }
        }
        if (k != null && !v.isEmpty()) {
            if (map.containsKey(k)) {
                map.get(k).addAll(v);
            } else {
                map.put(k, v);
            }
        }

        return map;
    }

    protected List<String> buildRegen() {

        List<String> list = new LinkedList<>();

        list.add(prefixTab(MARK_START));
        list.add("");
        list.addAll(prefixTabs(buildFields()));
        list.add("");
        list.addAll(prefixTabs(buildMethodsForRegen()));
        list.add("");
        list.add(prefixTab(MARK_END));
        list.addAll(prefixTabs(buildMethodsForOpt()));

        return list;
    }

    protected List<String> buildNewFile() {

        List<String> list = new LinkedList<>();

        list.add(packageStr);
        list.add("");
        list.addAll(buildImports());
        list.add("");
        list.addAll(buildClass());

        return list;
    }

    protected List<String> buildImports() {
        return importStrs;
    }

    protected List<String> buildClass() {

        List<String> list = new LinkedList<>();

        list.add(String.format("public class %s {", classStr));
        list.add("");
        list.add("");
        list.add(prefixTab(MARK_START));
        list.add("");
        list.addAll(prefixTabs(buildFields()));
        list.add("");
        list.addAll(prefixTabs(buildMethodsForRegen()));
        list.add("");
        list.add(prefixTab(MARK_END));
        list.add("");
        list.add("");
        list.addAll(prefixTabs(buildMethodsForOpt()));
        list.add("");
        list.add("}");

        return list;
    }

    protected List<String> buildFields() {

        List<String> list = new LinkedList<>();

        list.add(String.format("private static final List<%s> initList = new ArrayList<>();", typeStr));
        list.addAll(collectInsts());

        return list;
    }

    protected List<String> buildMethods() {

        List<String> list = new LinkedList<>();

        list.addAll(buildMethInit());
        list.add("");
        list.addAll(buildMethInitTypes());
        list.add("");
        list.addAll(buildMethInitType());
        list.add("");
        list.addAll(buildMethRegister());

        return list;
    }

    protected List<String> buildMethodsForRegen() {

        List<String> list = new LinkedList<>();

        list.addAll(buildMethInit());
        list.addAll(buildMethInitTypes());

        return list;
    }

    protected List<String> buildMethodsForOpt() {

        List<String> list = new LinkedList<>();

        list.addAll(buildMethInitTypeEmpty());
        list.addAll(buildMethRegisterEmpty());

        return list;
    }

    protected List<String> buildMethRegister() {

        List<String> list = new LinkedList<>();

        list.add("public static void register() {");
        list.add("");
        list.add(prefixTab(String.format("for (%s %s : initList) {", typeStr, typeStr.toLowerCase())));
        list.add("");
        list.add(prefixTab(String.format("GameRegistry.findRegistry(%s.class).register(%s);", typeStr, typeStr.toLowerCase()), 2));
        list.add("");
        list.add(prefixTab("}"));
        list.add("");
        list.add("}");

        return list;
    }

    protected List<String> buildMethRegisterEmpty() {

        List<String> list = new LinkedList<>();

        if (destLines != null) {
            for (String string : destLines) {
                if (string.contains("public static void register()")) {
                    return list;
                }
            }
        }

        list.add("");
        list.add("public static void register() {");
        list.add(prefixTab("// TODO: this"));
        list.add("}");

        return list;
    }

    protected List<String> buildMethInit() {

        List<String> list = new LinkedList<>();

        list.add("public static void init() {");
        list.add("");
        list.addAll(mapInit.keySet()
                                      .stream()
                                      .map(s -> prefixTab(String.format("init%ss();", s)))
                                      .collect(Collectors.toCollection(LinkedList::new)));
        list.add("");
        list.add("}");

        return list;
    }

    protected List<String> buildMethInitTypes() {

        List<String> list = new LinkedList<>();

        for (String type : mapInit.keySet()) {
            list.add("");
            list.add(String.format("private static void init%ss() {", type));
            list.add("");
            list.addAll(prefixTabs(collectInits(type)));
            list.add("");
            list.add("}");
        }

        return list;
    }

    protected List<String> buildMethInitType() {

        List<String> list = new LinkedList<>();

        for(String type : mapInit.keySet()) {
            list.add(String.format("private static %1$s init%1$s(String name) {", type));
            list.add("");
            list.add(prefixTab(String.format("%1$s %2$s = new %1$s(name);", type, type.toLowerCase())));
            list.add(prefixTab(String.format("%s.setRegistryName(name);", type.toLowerCase())));
            list.add(prefixTab(String.format("initList.add(%s);", type.toLowerCase())));
            list.add("");
            list.add(prefixTab(String.format("return %s;", type.toLowerCase())));
            list.add("");
            list.add("}");
        }

        return list;
    }

    protected List<String> buildMethInitTypeEmpty() {

        List<String> list = new LinkedList<>();

        for(String type : mapInit.keySet()) {

            if (destLines != null) {
                boolean flag = false;
                for (String string : destLines) {
                    if (string.contains(String.format("private static %1$s init%1$s(String name)", type))) {
                        flag = true;
                    }
                }
                if (flag) continue;
            }

            list.add("");
            list.add(String.format("private static %1$s init%1$s(String name) {", type));
            list.add("");
            list.add(prefixTab("// TODO: this"));
            list.add(prefixTab("return null;"));
            list.add("}");
        }

        return list;
    }

    protected List<String> collectInsts() {
        List<String> list = new LinkedList<>();
        for (String type : mapInit.keySet()) {
            list.add("");
            list.add("// " + type);
            list.addAll(mapInit.get(type)
                               .stream()
                               .map(s -> mapInst(type, s))
                               .collect(Collectors.toCollection(LinkedList::new)));
        }
        list.add("");
        return list;
    }

    protected List<String> collectInits(String type) {
        return mapInit.get(type)
                      .stream()
                      .map(s -> mapInit(type, s))
                      .collect(Collectors.toCollection(LinkedList::new));
    }

    protected String mapInst(String c, String s) {
        return s.startsWith("//") || s.equals("") ? s : String.format("public static %s %s;", c, s);
    }

    protected String mapInit(String c, String s) {
        return s.startsWith("//") || s.equals("") ? s : String.format("%2$-23s = init%1$s(\"%2$s\");", c, s);
    }

    protected String prefixTab(String s) {
        return s.equals("") ? "" : "    " + s;
    }

    protected String prefixTab(String s, int n) {
        if (s.equals("")) return "";
        StringBuilder sB = new StringBuilder(s);
        for (; n > 0; n--) sB.insert(0, "    ");
        return sB.toString();
    }

    protected List<String> prefixTabs(List<String> list) {
        return list.stream().map(this::prefixTab).collect(Collectors.toCollection(LinkedList::new));
    }

}
