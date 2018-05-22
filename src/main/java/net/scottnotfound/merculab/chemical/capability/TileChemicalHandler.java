package net.scottnotfound.merculab.chemical.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.scottnotfound.merculab.chemical.ChemicalContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileChemicalHandler extends TileEntity {
    protected ChemicalContainer container = new ChemicalContainer(0);

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        container.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        container.writeToNBT(tag);
        return tag;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY) {
            return (T) container;
        }
        return super.getCapability(capability, facing);
    }
}
