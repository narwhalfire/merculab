package net.scottnotfound.merculab.init;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;
import net.scottnotfound.merculab.chemical.Chemical;

@Mod.EventBusSubscriber
public class Init {

    public static Configuration config;



    // initialization events

    public void preInit(FMLPreInitializationEvent event) {
        MercuLabBlocks.init();
        MercuLabItems.init();
        MercuLabChemicals.init();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {

    }

    public void serverStarted(FMLServerStartedEvent event) {

    }

    public void serverStopping(FMLServerStoppingEvent event) {

    }

    public void serverStopped(FMLServerStoppedEvent event) {

    }



    // registration events

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    @SubscribeEvent
    public static void registerChemicals(RegistryEvent.Register<Chemical> event) {
    }

    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        new RegistryBuilder().setName(new ResourceLocation("merculab:chemical_registry"))
                             .setType(Chemical.class)
                             .create();
    }

}
