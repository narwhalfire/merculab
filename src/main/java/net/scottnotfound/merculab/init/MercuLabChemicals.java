package net.scottnotfound.merculab.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.scottnotfound.merculab.chemical.Chemical;

public class MercuLabChemicals
{

    @GameRegistry.ObjectHolder("merculab:silicon_dioxide")
    public static Chemical silicon_dioxide = new Chemical("silicon_dioxide",
                                                          new ResourceLocation("silicon_dioxide_item"),
                                                          new ResourceLocation("silicon_dioxide_structure"));


}
