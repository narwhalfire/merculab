package net.scottnotfound.merculab.proxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.scottnotfound.merculab.chemical.capability.TileChemicalHandler;
import net.scottnotfound.merculab.init.Config;
import net.scottnotfound.merculab.init.MercuLab;
import net.scottnotfound.merculab.test.TestContainerTileEntity;
import net.scottnotfound.merculab.tileentity.TestTileEntityProcessAB;

import java.io.File;


@Mod.EventBusSubscriber
public class Proxy implements IProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "merculab.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent event) {
        registerOthers();
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public void serverLoad(FMLServerStartingEvent event) {

    }

    public static void registerOthers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MercuLab.instance, new GuiProxy());

        // tile entities
        GameRegistry.registerTileEntity(TestContainerTileEntity.class, MercuLab.MOD_ID + "_testcontainerblock");
        GameRegistry.registerTileEntity(TestTileEntityProcessAB.class, MercuLab.MOD_ID + "_processab");
        GameRegistry.registerTileEntity(TileChemicalHandler.class, MercuLab.MOD_ID + "_vial_block");
    }




}
