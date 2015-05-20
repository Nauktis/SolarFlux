package com.nauktis.core.gui;

import com.google.common.collect.Maps;
import com.nauktis.core.utility.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Base class for any Container.
 * Parts of the code inspired by code from Pahinar, VSWE and Bdew
 */
public class BaseModContainer extends Container {
    protected static final int MOUSE_LEFT_CLICK = 0;
    protected static final int MOUSE_RIGHT_CLICK = 1;
    protected static final int FAKE_SLOT_ID = -999;
    protected static final int CLICK_MODE_NORMAL = 0;
    protected static final int CLICK_MODE_SHIFT = 1;
    protected static final int CLICK_MODE_KEY = 2;
    protected static final int CLICK_MODE_PICK_ITEM = 3;
    protected static final int CLICK_MODE_OUTSIDE = 4;
    protected static final int CLICK_DRAG_RELEASE = 5;
    protected static final int CLICK_MODE_DOUBLE_CLICK = 6;
    protected static final int CLICK_DRAG_MODE_PRE = 0;
    protected static final int CLICK_DRAG_MODE_SLOT = 1;
    protected static final int CLICK_DRAG_MODE_POST = 2;
    protected static final int PLAYER_INVENTORY_ROWS = 3;
    protected static final int PLAYER_INVENTORY_COLUMNS = 9;
    private final Map<Integer, Integer> mProgressBarValues = Maps.newHashMap();

    /**
     * Sends the value to the client only if it has changed or if the force flag is set.
     */
    protected void sendProgressBarUpdateIfChanged(int pType, int pValue, boolean pForce) {
        if (pForce || !(mProgressBarValues.containsKey(pType) && mProgressBarValues.get(pType).equals(pValue))) {
            for (ICrafting crafting : getCraftings()) {
                crafting.sendProgressBarUpdate(this, pType, pValue);
            }
            mProgressBarValues.put(pType, pValue);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<ICrafting> getCraftings() {
        return crafters;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer pPlayer, int pSlotIndex) {
        // Shift clicking does nothing by default.
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer pPlayer) {
        return true;
    }

    protected void addPlayerInventorySlotsToContainer(InventoryPlayer pInventoryPlayer, int pLeft, int pTop) {
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                addSlotToContainer(
                        new Slot(
                                pInventoryPlayer,
                                inventoryColumnIndex + inventoryRowIndex * 9 + 9,
                                pLeft + inventoryColumnIndex * 18,
                                pTop + inventoryRowIndex * 18));
            }
        }
    }

    protected void addPlayerActionSlotsToContainer(InventoryPlayer pInventoryPlayer, int pLeft, int pTop) {
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(pInventoryPlayer, actionBarSlotIndex, pLeft + actionBarSlotIndex * 18, pTop));
        }
    }

    /**
     * Tries to add an ItemStack to slots in the range specified. It will first try to merge to an existing stack before using an empty one.
     *
     * @returns true if a slot was found.
     */
    @Override
    protected boolean mergeItemStack(ItemStack pItemStack, int pSlotMin, int pSlotMax, boolean pAscending) {

        boolean slotFound = false;
        int currentSlotIndex = pAscending ? pSlotMax - 1 : pSlotMin;

        Slot slot;
        ItemStack stackInSlot;

        // Try to merge to an existing ItemStack
        if (pItemStack.isStackable()) {
            while (pItemStack.stackSize > 0 && (!pAscending && currentSlotIndex < pSlotMax || pAscending && currentSlotIndex >= pSlotMin)) {
                slot = getSlot(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(pItemStack) && Utils.itemStacksEqualIgnoreStackSize(pItemStack, stackInSlot)) {
                    int combinedStackSize = stackInSlot.stackSize + pItemStack.stackSize;
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());

                    if (combinedStackSize <= slotStackSizeLimit) {
                        pItemStack.stackSize = 0;
                        stackInSlot.stackSize = combinedStackSize;
                        slot.onSlotChanged();
                        slotFound = true;
                    } else if (stackInSlot.stackSize < slotStackSizeLimit) {
                        pItemStack.stackSize -= slotStackSizeLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotStackSizeLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }

                currentSlotIndex += pAscending ? -1 : 1;
            }
        }

        // Try to add to an empty slot.
        if (pItemStack.stackSize > 0) {
            currentSlotIndex = pAscending ? pSlotMax - 1 : pSlotMin;

            while (!pAscending && currentSlotIndex < pSlotMax || pAscending && currentSlotIndex >= pSlotMin) {
                slot = getSlot(currentSlotIndex);
                stackInSlot = slot.getStack();

                if (slot.isItemValid(pItemStack) && stackInSlot == null) {
                    ItemStack placedItemStack = pItemStack.copy();
                    placedItemStack.stackSize = Math.min(pItemStack.stackSize, slot.getSlotStackLimit());
                    slot.putStack(placedItemStack);
                    slot.onSlotChanged();

                    if (slot.getStack() != null) {
                        pItemStack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }
                    break;
                }

                currentSlotIndex += pAscending ? -1 : 1;
            }
        }

        return slotFound;
    }
}
