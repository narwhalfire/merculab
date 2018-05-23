package net.scottnotfound.merculab.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.scottnotfound.merculab.Config;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.chemical.Chemical;
import net.scottnotfound.merculab.chemical.capability.TileChemicalHandler;
import net.scottnotfound.merculab.init.MercuLabBlocks;
import net.scottnotfound.merculab.init.MercuLabChemicals;
import net.scottnotfound.merculab.init.MercuLabItems;
import net.scottnotfound.merculab.test.TestContainerTileEntity;
import net.scottnotfound.merculab.tileentity.TestTileEntityProcessAB;

import java.io.File;


@Mod.EventBusSubscriber
public class Proxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "scottnotfound.cfg"));
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

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        MercuLabBlocks.initBlocks();
        MercuLabBlocks.registerBlocks();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        MercuLabItems.initItems();
        MercuLabItems.registerItems();
    }

    public static void registerOthers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MercuLab.instance, new GuiProxy());

        // tile entities
        GameRegistry.registerTileEntity(TestContainerTileEntity.class, MercuLab.MOD_ID + "_testcontainerblock");
        GameRegistry.registerTileEntity(TestTileEntityProcessAB.class, MercuLab.MOD_ID + "_processab");
        GameRegistry.registerTileEntity(TileChemicalHandler.class, MercuLab.MOD_ID + "_vial_block");
    }

    @SubscribeEvent
    public static void registerChemicals(RegistryEvent.Register<Chemical> event) {
        MercuLabChemicals.initChemicals();
        MercuLabChemicals.registerChemicals();
    }

    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        new RegistryBuilder().setName(new ResourceLocation("merculab:chemical_registry")).setType(Chemical.class).create();
    }
}