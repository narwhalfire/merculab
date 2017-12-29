package net.scottnotfound.merculab.test;

import jline.internal.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TestContainer extends Container {

    private TestContainerTileEntity containerTileEntity;

    public TestContainer(IInventory playerInventory, TestContainerTileEntity containerTileEntity) {
        this.containerTileEntity = containerTileEntity;

        addOwnslots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col*18;
                int y = row*18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnslots() {
        IItemHandler itemHandler = this.containerTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                null);
        int x = 10;
        int y = 6;

        int slotIndex = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
            slotIndex++;
            x += 18;
        }
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack _itemstack = slot.getStack();
            itemstack = _itemstack.copy();

            if (index < TestContainerTileEntity.SIZE) {
                if (!this.mergeItemStack(_itemstack, TestContainerTileEntity.SIZE, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(_itemstack, 0, TestContainerTileEntity.SIZE, false)) {
                return null;
            }

            if (_itemstack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return containerTileEntity.canInteractWith(playerIn);
    }
}
