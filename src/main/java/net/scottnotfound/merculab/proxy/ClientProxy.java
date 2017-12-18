package net.scottnotfound.merculab.proxy;

// imports
import net.minecraft.init.Items;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.scottnotfound.merculab.MercuLabBlocks;
import net.scottnotfound.merculab.MercuLabCompounds;
import net.scottnotfound.merculab.MercuLabItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        MercuLabBlocks.initModels();
        MercuLabItems.initModels();
        MercuLabCompounds.initModels();
    }
}
