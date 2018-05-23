package net.scottnotfound.merculab.block.glassware;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.scottnotfound.merculab.chemical.ChemicalContainer;
import net.scottnotfound.merculab.chemical.capability.CapabilityChemicalHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityVial extends TileEntity {

    protected ChemicalContainer container = new ChemicalContainer(0);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        container.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        container.writeToNBT(compound);
        return compound;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY) {
            return (T) container;
        }
        return super.getCapability(capability, facing);
    }
}
