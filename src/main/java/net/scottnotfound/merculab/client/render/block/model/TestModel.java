package net.scottnotfound.merculab.client.render.block.model;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.scottnotfound.merculab.MercuLab;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class TestModel implements IModel {

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new TestBakedModel(state, format, bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(new ResourceLocation(MercuLab.MOD_ID, "blocks/bakedmodeltexture"));
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
