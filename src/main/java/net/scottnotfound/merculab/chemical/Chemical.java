

package net.scottnotfound.merculab.chemical;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Locale;

/**
 *  This is the class that will represent any kind of chemical.
 */
public class Chemical extends IForgeRegistryEntry.Impl<Chemical>
{

    /** The name of the registry for Chemicals. */
    private ResourceLocation registry = new ResourceLocation("merculab:chemical_registry");

    /** The unique identification name for this chemical. Should be a common name. */
    protected final String chemicalName;

    /** The unlocalized name of this chemical. */
    protected String unlocalizedName;

    protected final ResourceLocation asAtomicStructure;
    protected final ResourceLocation asItem;

    /** Density of the chemical. Default is that of water in kg/m^3. */
    protected int density = 1000;

    /** Rarity of chemical. Used in tool tips. */
    protected EnumRarity rarity = EnumRarity.COMMON;

    /** Block implementation of chemical. */
    protected Block block = null;


    public Chemical(String name) {
        this.chemicalName = name.toLowerCase(Locale.ENGLISH);
        this.unlocalizedName = chemicalName;
        this.asAtomicStructure = new ResourceLocation(chemicalName + "_atomic");
        this.asItem = new ResourceLocation(chemicalName + "_item");
    }

    public Chemical(String chemicalName, ResourceLocation asItem, ResourceLocation asAtomicStructure) {
        this.chemicalName = chemicalName.toLowerCase(Locale.ENGLISH);
        this.unlocalizedName = chemicalName;
        this.asAtomicStructure = asAtomicStructure;
        this.asItem = asItem;
    }

    public Chemical setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    public Chemical setBlock(Block block) {
        if (this.block == null || this.block == block) {
            this.block = block;
        } else {
            FMLLog.log.warn("A mod has attempted to assign Block {} to the Chemical '{}' but this Chemical has already been linked to the Block {}. "
                            + "You may have duplicate Chemical Blocks as a result. It *may* be possible to configure your mods to avoid this.", block, chemicalName, this.block);
        }
        return this;
    }

    public Chemical setDensity(int density) {
        this.density = density;
        return this;
    }

    public Chemical setRarity(EnumRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public final String getName() {
        return this.chemicalName;
    }

    public final Block getBlock() {
        return block;
    }

    public final boolean canBePlacedInWorld() {
        return block != null;
    }

    public String getLocalizedName(ChemicalStack stack) {
        String s = this.getUnlocalizedName();
        return s == null ? "" : I18n.translateToLocal(s);
    }

    public String getUnlocalizedName(ChemicalStack stack) {
        return this.getUnlocalizedName();
    }

    public String getUnlocalizedName() {
        return "chemical." + this.unlocalizedName;
    }

    public final int getDensity() {
        return density;
    }

    public EnumRarity getRarity() {
        return rarity;
    }

    public ResourceLocation getAsAtomicStructure() {
        return asAtomicStructure;
    }

    public ResourceLocation getAsItem() {
        return asItem;
    }


    /* Stack-based Accessors */
    public int              getDensity          (ChemicalStack stack) {return getDensity();}
    public EnumRarity       getRarity           (ChemicalStack stack) {return getRarity();}
    public ResourceLocation getAsAtomicStructure(ChemicalStack stack) {return getAsAtomicStructure();}
    public ResourceLocation getAsItem           (ChemicalStack stack) {return getAsItem();}

    /* World-based Accessors */
    public int              getDensity          (World world, BlockPos pos) {return getDensity();}
    public EnumRarity       getRarity           (World world, BlockPos pos) {return getRarity();}
    public ResourceLocation getAsAtomicStructure(World world, BlockPos pos) {return getAsAtomicStructure();}
    public ResourceLocation getAsItem           (World world, BlockPos pos) {return getAsItem();}

}
