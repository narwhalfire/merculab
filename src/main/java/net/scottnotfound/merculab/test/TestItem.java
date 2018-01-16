package net.scottnotfound.merculab.test;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.MercuLab;

public class TestItem extends Item {

    public static TestItem t = new TestItem("test_item");
    public static TestItem a = new TestItem("testA");
    public static TestItem b = new TestItem("testB");

    public TestItem(String name) {
        setRegistryName(name);
        setUnlocalizedName(MercuLab.MOD_ID + "." + name);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this,
                0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
