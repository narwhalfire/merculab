package net.scottnotfound.merculab.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.chemical.Chemical;

import java.util.ArrayList;

@GameRegistry.ObjectHolder("merculab")
public class MercuLabChemicals {

    private static ArrayList<IInitializer> initList = new ArrayList<>();

    public static Chemical silicon_dioxide;


    private static void preInitChemicals() {

        silicon_dioxide = new Chemical("silicon_dioxide");

    }

    private static void preInitList() {

        initList.add(silicon_dioxide);

    }

    public static void initChemicals() {

        preInitChemicals();
        preInitList();

        for (IInitializer init : initList) {
            init.init();
        }

    }

    public static void registerChemicals() {



    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {

    }
}
