package net.scottnotfound.merculab.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.client.render.block.model.TestBakedModelLoader;
import net.scottnotfound.merculab.init.MercuLabBlocks;
import net.scottnotfound.merculab.init.MercuLabCompounds;
import net.scottnotfound.merculab.init.MercuLabItems;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModelLoaderRegistry.registerLoader(new TestBakedModelLoader());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        MercuLabBlocks.initItemModels();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        MercuLabBlocks.initModels();
        MercuLabItems.initModels();
        MercuLabCompounds.initModels();
    }
}
