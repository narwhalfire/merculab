package net.scottnotfound.merculab.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.scottnotfound.merculab.proxy.Proxy;

@Mod(modid = MercuLab.MOD_ID, name = MercuLab.MOD_NAME, version = MercuLab.MOD_VERSION)
public class MercuLab {

    public static final String MOD_ID = "merculab";
    public static final String MOD_NAME = "MercuLab";
    public static final String MOD_VERSION = "0.0.1";

    @SidedProxy(
            clientSide = "net.scottnotfound.merculab.proxy.ClientProxy",
            serverSide = "net.scottnotfound.merculab.proxy.ServerProxy"
    )
    public static Proxy proxy;

    public static Init init;

    @Mod.Instance
    public static MercuLab instance;

    public static org.apache.logging.log4j.Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverLoad(event);
    }
}
