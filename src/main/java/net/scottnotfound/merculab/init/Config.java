package net.scottnotfound.merculab.init;


import net.minecraftforge.common.config.Configuration;
import net.scottnotfound.merculab.proxy.Proxy;
import org.apache.logging.log4j.Level;

public abstract class Config {

    private static final String CATEGORY_GENERAL = "general";


    public static void readConfig() {
        Configuration cfg = Proxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        }
        catch (Exception exception) {
            MercuLab.logger.log(Level.ERROR, "Error loading config file!", exception);
        }
        finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration");
    }
}
