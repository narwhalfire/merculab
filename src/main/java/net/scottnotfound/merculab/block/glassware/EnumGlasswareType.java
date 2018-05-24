package net.scottnotfound.merculab.block.glassware;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumGlasswareType implements IStringSerializable {


    GLASS_VIAL(0, "glass_vial", "glassVial", TileEntityVial.class),
    GLASS_JAR(1, "glass_jar", "glassJar", TileEntityJar.class),
    GLASS_BOTTLE(2, "glass_bottle", "glassBottle", TileEntityBottle.class),
    GLASS_BEAKER(3, "glass_beaker", "glassBeaker", TileEntityBeaker.class),
    GLASS_FLASK(4, "glass_flask", "glassFlask", TileEntityFlask.class),
    ;


    private static final EnumGlasswareType[] META_LOOKUP = new EnumGlasswareType[values().length];
    private final int meta;
    private final String name;
    private final String unlocalizedName;
    private final Class<? extends TileEntity> teClass;


    EnumGlasswareType(int metaIn, String nameIn, String unlocalizdNameIn, Class<? extends TileEntity> teClassIn) {
        this.meta = metaIn;
        this.name = nameIn;
        this.unlocalizedName = unlocalizdNameIn;
        this.teClass = teClassIn;
    }

    public int getMetadata() {
        return this.meta;
    }

    @SideOnly(Side.CLIENT)
    public String getGlasswareName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    public Class<? extends TileEntity> getTeClass() {
        return teClass;
    }

    public static  EnumGlasswareType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public String toString() {
        return this.unlocalizedName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    static {
        for (EnumGlasswareType enumGlasswareType : values()) {
            META_LOOKUP[enumGlasswareType.getMetadata()] = enumGlasswareType;
        }
    }
}
