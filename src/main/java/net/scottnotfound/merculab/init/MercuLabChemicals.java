package net.scottnotfound.merculab.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.chemical.Chemical;

import java.util.ArrayList;

@GameRegistry.ObjectHolder("merculab")
public class MercuLabChemicals {

    private static ArrayList<IInitializer> initList = new ArrayList<>();


    public static Chemical nil;

    public static Chemical hydrogen;
    public static Chemical helium;
    public static Chemical lithium;
    public static Chemical beryllium;
    public static Chemical boron;
    public static Chemical carbon;
    public static Chemical oxygen;
    public static Chemical nitrogen;
    public static Chemical fluorine;
    public static Chemical neon;
    public static Chemical sodium;
    public static Chemical magnesium;
    public static Chemical aluminum;
    public static Chemical silicon;
    public static Chemical sulfur;
    public static Chemical phosphorus;
    public static Chemical chlorine;
    public static Chemical argon;

    public static Chemical hydrogen_oxide;
    public static Chemical lithium_oxide;
    public static Chemical sodium_oxide;
    public static Chemical beryllium_oxide;
    public static Chemical magnesium_oxide;
    public static Chemical boron_trioxide;
    public static Chemical carbon_dioxide;
    public static Chemical carbon_monoxide;
    public static Chemical nitric_oxide;
    public static Chemical nitrogen_dioxide;
    public static Chemical nitrous_oxide;
    public static Chemical dinitrogen_trioxide;
    public static Chemical dinitrogen_tetroxide;
    public static Chemical dioxygen_difluoride;
    public static Chemical aluminum_oxide;
    public static Chemical silicon_dioxide;
    public static Chemical sulfur_dioxide;
    public static Chemical sulfur_trioxide;
    public static Chemical phosphorus_pentoxide;
    public static Chemical phosphorus_trioxide;
    public static Chemical chlorine_dioxide;
    public static Chemical chlorine_trioxide;

    public static Chemical lithium_chloride;
    public static Chemical sodium_chloride;
    public static Chemical beryllium_chloride;
    public static Chemical magnesium_chloride;
    public static Chemical boron_chloride;
    public static Chemical aluminum_chloride;


    private static void preInitChemicals() {

        nil                     = new Chemical("nil");

        hydrogen                = new Chemical("hydrogen");
        helium                  = new Chemical("helium");
        lithium                 = new Chemical("lithium");
        beryllium               = new Chemical("beryllium");
        boron                   = new Chemical("boron");
        carbon                  = new Chemical("carbon");
        oxygen                  = new Chemical("oxygen");
        nitrogen                = new Chemical("nitrogen");
        fluorine                = new Chemical("fluorine");
        neon                    = new Chemical("neon");
        sodium                  = new Chemical("sodium");
        magnesium               = new Chemical("magnesium");
        aluminum                = new Chemical("aluminum");
        silicon                 = new Chemical("silicon");
        sulfur                  = new Chemical("sulfur");
        phosphorus              = new Chemical("phosphorus");
        chlorine                = new Chemical("chlorine");
        argon                   = new Chemical("argon");

        hydrogen_oxide          = new Chemical("hydrogen_oxide");
        lithium_oxide           = new Chemical("lithium_oxide");
        sodium_oxide            = new Chemical("sodium_oxide");
        beryllium_oxide         = new Chemical("beryllium_oxide");
        magnesium_oxide         = new Chemical("magnesium_oxide");
        boron_trioxide          = new Chemical("boron_trioxide");
        carbon_dioxide          = new Chemical("carbon_dioxide");
        carbon_monoxide         = new Chemical("carbon_monoxide");
        nitric_oxide            = new Chemical("nitric_oxide");
        nitrogen_dioxide        = new Chemical("nitrogen_dioxide");
        nitrous_oxide           = new Chemical("nitrous_oxide");
        dinitrogen_trioxide     = new Chemical("dinitrogen_trioxide");
        dinitrogen_tetroxide    = new Chemical("dinitrogen_tetroxide");
        dioxygen_difluoride     = new Chemical("dioxygen_difluoride");
        aluminum_oxide          = new Chemical("aluminum_oxide");
        silicon_dioxide         = new Chemical("silicon_dioxide");
        sulfur_dioxide          = new Chemical("sulfur_dioxide");
        sulfur_trioxide         = new Chemical("sulfur_trioxide");
        phosphorus_pentoxide    = new Chemical("phosphorus_pentoxide");
        phosphorus_trioxide     = new Chemical("phosphorus_trioxide");
        chlorine_dioxide        = new Chemical("chlorine_dioxide");
        chlorine_trioxide       = new Chemical("chlorine_trioxide");

        lithium_chloride        = new Chemical("lithium_chloride");
        sodium_chloride         = new Chemical("sodium_chloride");
        beryllium_chloride      = new Chemical("beryllium_chloride");
        magnesium_chloride      = new Chemical("magnesium_chloride");
        boron_chloride          = new Chemical("boron_chloride");
        aluminum_chloride       = new Chemical("aluminum_chloride");

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
