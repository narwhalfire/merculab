package net.scottnotfound.merculab.chemical;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityChemicalStorage {

    @CapabilityInject(IChemicalStorage.class)
    public static Capability<IChemicalStorage> CHEMICAL_STORAGE_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IChemicalStorage.class, new Capability.IStorage<IChemicalStorage>() {
            @Override
            public NBTBase writeNBT(Capability<IChemicalStorage> capability, IChemicalStorage instance, EnumFacing side) {
                return null;
            }
            @Override
            public void readNBT(Capability<IChemicalStorage> capability, IChemicalStorage instance, EnumFacing side, NBTBase nbt) {

            }
        },
        ChemicalStorage::new);
    }

    public static class ChemicalStorageCapabilityDispatcher implements ICapabilityProvider {

        private final IChemicalStorage chemicalStorage;

        public ChemicalStorageCapabilityDispatcher(IChemicalStorage chemicalStorage) {
            this.chemicalStorage = chemicalStorage;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CHEMICAL_STORAGE_CAPABILITY;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CHEMICAL_STORAGE_CAPABILITY) {
                return CHEMICAL_STORAGE_CAPABILITY.cast(chemicalStorage);
            }
            return null;
        }
    }
}
