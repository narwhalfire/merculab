package net.scottnotfound.merculab.chemical.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.scottnotfound.merculab.chemical.ChemicalContainer;
import net.scottnotfound.merculab.chemical.ChemicalStack;
import net.scottnotfound.merculab.chemical.IChemicalContainer;
import net.scottnotfound.merculab.chemical.capability.template.ChemicalHandlerItemStack;
import net.scottnotfound.merculab.init.MercuLabBlocks;

public class CapabilityChemicalHandler
{
    @CapabilityInject(IChemicalHandler.class)
    public static Capability<IChemicalHandler> CHEMICAL_HANDLER_CAPABILITY = null;

    @CapabilityInject(IChemicalHandlerItem.class)
    public static Capability<IChemicalHandlerItem> CHEMICAL_HANDLER_ITEM_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IChemicalHandler.class,
                                            new DefaultChemicalHandlerStorage<>(),
                                            () -> new ChemicalContainer(0));
        CapabilityManager.INSTANCE.register(IChemicalHandlerItem.class,
                                            new DefaultChemicalHandlerStorage<>(),
                                            () -> new ChemicalHandlerItemStack(new ItemStack(MercuLabBlocks.vial), 0));
    }

    private static class DefaultChemicalHandlerStorage<T extends IChemicalHandler> implements Capability.IStorage<T> {
        @Override
        public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {

            if (!(instance instanceof IChemicalContainer)) {
                throw new RuntimeException("IChemicalHandler instance does not implement IChemicalContainer");
            }

            NBTTagCompound nbt = new NBTTagCompound();
            IChemicalContainer container = (IChemicalContainer) instance;
            ChemicalStack chemicalStack = container.getChemical();

            if (chemicalStack != null) {
                chemicalStack.writeToNBT(nbt);
            } else {
                nbt.setString("Empty", "");
            }

            nbt.setInteger("Capacity", container.getCapacity());
            return nbt;
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {

            if (!(instance instanceof ChemicalContainer)) {
                throw new RuntimeException("IChemicalHandler instance is not instance of ChemicalContainer");
            }

            NBTTagCompound tags = (NBTTagCompound) nbt;
            ChemicalContainer container = (ChemicalContainer) instance;
            container.setCapacity(tags.getInteger("Capacity"));
            container.readFromNBT(tags);
        }
    }
}
