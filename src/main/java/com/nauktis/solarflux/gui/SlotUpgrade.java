package com.nauktis.solarflux.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot {
    public SlotUpgrade(IInventory pInventory, int pSlotIndex, int pXDisplayPosition, int pYDisplayPosition) {
        super(pInventory, pSlotIndex, pXDisplayPosition, pYDisplayPosition);
    }

    @Override
    public boolean isItemValid(ItemStack pItemStack) {
        // Delegate to the inventory.
        return inventory.isItemValidForSlot(getSlotIndex(), pItemStack);
    }
}
