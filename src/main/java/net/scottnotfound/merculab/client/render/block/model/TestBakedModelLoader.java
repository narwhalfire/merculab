package net.scottnotfound.merculab.client.render.block.model;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.scottnotfound.merculab.init.MercuLab;

public class TestBakedModelLoader implements ICustomModelLoader {

    public static final TestModel TEST_MODEL = new TestModel();

    @Override
    public boolean accepts(ResourceLocation location) {
        return location.getResourceDomain().equals(MercuLab.MOD_ID)
                && "bakedmodelblock".equals(location.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation location) throws Exception {
        return TEST_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
