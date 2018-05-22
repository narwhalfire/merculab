package net.scottnotfound.merculab.chemical;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.scottnotfound.merculab.chemical.capability.ChemicalContainerPropertiesWrapper;
import net.scottnotfound.merculab.chemical.capability.IChemicalContainerProperties;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandler;

import javax.annotation.Nullable;

public class ChemicalContainer implements IChemicalContainer, IChemicalHandler {
    @Nullable
    protected ChemicalStack chemical;
    protected int capacity;
    protected TileEntity tileEntity;
    protected boolean canInsert = true;
    protected boolean canExtract = true;
    protected IChemicalContainerProperties[] containerProperties;

    public ChemicalContainer(int capacity) {
        this(null, capacity);
    }

    public ChemicalContainer(@Nullable ChemicalStack chemicalStack, int capacity) {
        this.chemical = chemicalStack;
        this.capacity = capacity;
    }

    public ChemicalContainer(Chemical chemical, int amount, int capacity) {
        this(new ChemicalStack(chemical, amount), capacity);
    }

    public ChemicalContainer readFromNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey("Empty")) {
            ChemicalStack chemical = ChemicalStack.loadChemicalStackFromNBT(nbt);
            setChemical(chemical);
        } else {
            setChemical(null);
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (chemical != null) {
            chemical.writeToNBT(nbt);
        } else {
            nbt.setString("Empty", "");
        }
        return nbt;
    }

    public void setChemical(@Nullable ChemicalStack chemical) {
        this.chemical = chemical;
    }

    public void setTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Nullable
    @Override
    public ChemicalStack getChemical() {
        return chemical;
    }

    @Override
    public int getChemicalAmount() {
        if (chemical == null) {
            return 0;
        }
        return chemical.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public ChemicalContainerInfo getInfo() {
        return new ChemicalContainerInfo(this);
    }

    @Override
    public IChemicalContainerProperties[] getContainerProperties() {
        if (this.containerProperties == null) {
            this.containerProperties = new IChemicalContainerProperties[] { new ChemicalContainerPropertiesWrapper(this) };
        }
        return this.containerProperties;
    }

    @Override
    public int insert(ChemicalStack resource, boolean doInsert) {
        if (!canInsertChemicalType(resource)) {
            return 0;
        }
        return insertInternal(resource, doInsert);
    }

    @Nullable
    @Override
    public ChemicalStack extract(ChemicalStack resource, boolean doExtract) {
        if (!canExtractChemicalType(getChemical())) {
            return null;
        }
        return extractInternal(resource, doExtract);
    }

    @Nullable
    @Override
    public ChemicalStack extract(int maxExtract, boolean doExtract) {
        if (!canExtractChemicalType(getChemical())) {
            return null;
        }
        return extractInternal(maxExtract, doExtract);
    }

    public int insertInternal(ChemicalStack resource, boolean doInsert) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }

        if (!doInsert) {
            if (chemical == null) {
                return Math.min(capacity, resource.amount);
            }

            if (!chemical.isChemicalEqual(resource)) {
                return 0;
            }

            return Math.min(capacity - chemical.amount, resource.amount);
        }

        if (chemical == null) {
            chemical = new ChemicalStack(resource, Math.min(capacity, resource.amount));

            onContentsChanged();

            if (tileEntity != null) {
                ChemicalEvent.fireEvent(new ChemicalEvent.ChemicalInsertionEvent(chemical, tileEntity.getWorld(), tileEntity.getPos(), this, chemical.amount));
            }
            return chemical.amount;
        }

        if (!chemical.isChemicalEqual(resource)) {
            return 0;
        }
        int inserted = capacity - chemical.amount;

        if (resource.amount < inserted) {
            chemical.amount += resource.amount;
            inserted = resource.amount;
        } else {
            chemical.amount = capacity;
        }

        onContentsChanged();

        if (tileEntity != null) {
            ChemicalEvent.fireEvent(new ChemicalEvent.ChemicalInsertionEvent(chemical, tileEntity.getWorld(), tileEntity.getPos(), this, chemical.amount));
        }
        return inserted;
    }

    @Nullable
    public ChemicalStack extractInternal(ChemicalStack resource, boolean doExtract) {
        if (resource == null || !resource.isChemicalEqual(getChemical())) {
            return null;
        }
        return extractInternal(resource, doExtract);
    }

    @Nullable
    public ChemicalStack extractInternal(int maxExtract, boolean doExtract) {
        if (chemical == null || maxExtract <= 0) {
            return null;
        }

        int extracted = maxExtract;
        if (chemical.amount < extracted) {
            extracted = chemical.amount;
        }

        ChemicalStack stack = new ChemicalStack(chemical, extracted);
        if (doExtract) {
            chemical.amount -= extracted;
            if (chemical.amount <= 0) {
                chemical = null;
            }

            onContentsChanged();

            if (tileEntity != null) {
                ChemicalEvent.fireEvent(new ChemicalEvent.ChemicalExtractionEvent(chemical, tileEntity.getWorld(), tileEntity.getPos(), this, chemical.amount));
            }
        }
        return stack;
    }

    public boolean canInsert() {
        return canInsert;
    }

    public boolean canExtract() {
        return canExtract;
    }

    public void setCanInsert(boolean canInsert) {
        this.canInsert = canInsert;
    }

    public void setCanExtract(boolean canExtract) {
        this.canExtract = canExtract;
    }

    public boolean canInsertChemicalType(ChemicalStack chemical) {
        return canInsert();
    }

    public boolean canExtractChemicalType(@Nullable ChemicalStack chemical) {
        return chemical != null && canExtract();
    }

    protected void onContentsChanged() {

    }

}
