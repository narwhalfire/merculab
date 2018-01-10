package net.scottnotfound.merculab.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import net.scottnotfound.merculab.Config;
import net.scottnotfound.merculab.MercuLab;
import net.scottnotfound.merculab.init.MercuLabBlocks;
import net.scottnotfound.merculab.init.MercuLabItems;
import net.scottnotfound.merculab.test.TestContainerTileEntity;
import net.scottnotfound.merculab.tileentity.TestTileEntityProcessAB;

import java.io.File;


@Mod.EventBusSubscriber
public class CommonProxy {

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
        event.getRegistry().register(MercuLabBlocks.testBlock);
        event.getRegistry().register(MercuLabBlocks.testContainerBlock);
        event.getRegistry().register(MercuLabBlocks.testBlockProcessAB);
        event.getRegistry().register(MercuLabBlocks.bakedModelBlock);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(MercuLabItems.testItem);
        event.getRegistry().register(MercuLabItems.testItemA);
        event.getRegistry().register(MercuLabItems.testItemB);
        event.getRegistry().register(MercuLabItems.Vial);
        event.getRegistry().register(new ItemBlock(MercuLabBlocks.testBlock)
                .setRegistryName(MercuLabBlocks.testBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(MercuLabBlocks.testContainerBlock)
                .setRegistryName(MercuLabBlocks.testContainerBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(MercuLabBlocks.testBlockProcessAB)
                .setRegistryName(MercuLabBlocks.testBlockProcessAB.getRegistryName()));
        event.getRegistry().register(new ItemBlock(MercuLabBlocks.bakedModelBlock)
                .setRegistryName(MercuLabBlocks.bakedModelBlock.getRegistryName()));
    }

    public static void registerOthers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MercuLab.instance, new GuiProxy());

        // tile entities
        GameRegistry.registerTileEntity(TestContainerTileEntity.class, MercuLab.MODID + "_testcontainerblock");
        GameRegistry.registerTileEntity(TestTileEntityProcessAB.class, MercuLab.MODID + "_processab");
    }
}
