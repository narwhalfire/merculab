package net.scottnotfound.merculab.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.scottnotfound.merculab.test.TestItem;

public class TestContainerProcessAB extends Container {

    private IInventory tileEntityProcessAB;
    private int processTime;
    private int processTimeTotal;

    public TestContainerProcessAB(IInventory playerInv, IInventory processInv) {
        this.tileEntityProcessAB = processInv;
        this.addSlotToContainer(new TestSlotProcessABin(processInv, 0, 56, 36));
        this.addSlotToContainer(new TestSlotProcessABout(processInv, 1, 116, 36));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntityProcessAB.isUsableByPlayer(player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener iContainerListener = this.listeners.get(i);

            if (this.processTime != this.tileEntityProcessAB.getField(0)) {
                iContainerListener.sendWindowProperty(this, 0, this.tileEntityProcessAB.getField(0));
            }
            if (this.processTimeTotal != this.tileEntityProcessAB.getField(1)) {
                iContainerListener.sendWindowProperty(this, 1, this.tileEntityProcessAB.getField(1));
            }
        }

        this.processTime = this.tileEntityProcessAB.getField(0);
        this.processTimeTotal = this.tileEntityProcessAB.getField(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        this.tileEntityProcessAB.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (index == 1) {
                if (!this.mergeItemStack(itemStack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack1, itemStack);
            }
            else if (index > 1) {
                if (itemStack1.getItem() == TestItem.a) {
                    if (!this.mergeItemStack(itemStack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 29) {
                    if (!this.mergeItemStack(itemStack1, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 38) {
                    if (!this.mergeItemStack(itemStack1, 2, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemStack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack1);
        }
        return itemStack;
    }

}
