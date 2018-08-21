package net.scottnotfound.merculab.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.scottnotfound.merculab.chemical.Chemical;
import net.scottnotfound.merculab.init.*;

import java.io.File;


@Mod.EventBusSubscriber
public class Proxy implements IProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event) {
        MercuLab.logger = event.getModLog();
        MercuLabBlocks.init();
        MercuLabItems.init();
        MercuLabChemicals.init();

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
    }


    // registration events

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        MercuLabBlocks.register();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        MercuLabItems.register();
    }

    @SubscribeEvent
    public static void registerChemicals(RegistryEvent.Register<Chemical> event) {
        MercuLabChemicals.register();
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {

    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {

    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {

    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {

    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        new RegistryBuilder().setName(new ResourceLocation("merculab:chemical_registry"))
                             .setType(Chemical.class)
                             .create();
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {

    }

}
