package net.scottnotfound.merculab.test;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.init.IInitializer;

public class TestItem extends Item implements IInitializer {

    public static TestItem t = new TestItem("test_item");
    public static TestItem a = new TestItem("testA");
    public static TestItem b = new TestItem("testB");

    private String name;

    public TestItem(String name) {
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,
                0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void init() {

        this.setRegistryName(this.name);
        ForgeRegistries.ITEMS.register(this);

    }
}
