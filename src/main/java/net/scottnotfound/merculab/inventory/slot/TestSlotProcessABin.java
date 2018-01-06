package net.scottnotfound.merculab.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.scottnotfound.merculab.test.TestItem;

public class TestSlotProcessABin extends Slot {

    public TestSlotProcessABin(IInventory inventory, int slotIndex, int xPosition, int yPosition) {
        super(inventory, slotIndex, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == TestItem.a;
    }

}
