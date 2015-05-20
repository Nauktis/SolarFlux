package com.nauktis.solarflux.gui;

import com.nauktis.core.gui.BaseModContainer;
import com.nauktis.core.utility.Utils;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.items.UpgradeItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSolarPanel extends BaseModContainer {
    private final SolarPanelTileEntity mSolarPanelTileEntity;

    public ContainerSolarPanel(InventoryPlayer pInventoryPlayer, SolarPanelTileEntity pSolarPanelTileEntity) {
        mSolarPanelTileEntity = pSolarPanelTileEntity;

        for (int i = 0; i < SolarPanelTileEntity.INVENTORY_SIZE; ++i) {
            addSlotToContainer(new SlotUpgrade(mSolarPanelTileEntity, i, 17 + i * 18, 59));
        }

        addPlayerInventorySlotsToContainer(pInventoryPlayer, 8, 98);
        addPlayerActionSlotsToContainer(pInventoryPlayer, 8, 156);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        boolean forceUpdate = (mSolarPanelTileEntity.getWorldObj().getWorldTime() % 40) == 0;
        sendProgressBarUpdateIfChanged(0, mSolarPanelTileEntity.getEnergyStored() & 0xFFFF, forceUpdate);
        sendProgressBarUpdateIfChanged(1, mSolarPanelTileEntity.getEnergyStored() >>> 16, forceUpdate);
        sendProgressBarUpdateIfChanged(2, mSolarPanelTileEntity.getCurrentEnergyGeneration() & 0xFFFF, false);
        sendProgressBarUpdateIfChanged(3, mSolarPanelTileEntity.getCurrentEnergyGeneration() >>> 16, false);
        sendProgressBarUpdateIfChanged(4, (int) (100 * mSolarPanelTileEntity.getSunIntensity()) & 0xFFFF, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int pIndex, int pValue) {
        super.updateProgressBar(pIndex, pValue);
        if (pIndex == 0) {
            mSolarPanelTileEntity.setEnergyStored((mSolarPanelTileEntity.getEnergyStored() & 0xFFFF0000) | pValue);
        }
        if (pIndex == 1) {
            mSolarPanelTileEntity.setEnergyStored(mSolarPanelTileEntity.getEnergyStored() & 0xFFFF | (pValue << 16));
        }
        if (pIndex == 2) {
            mSolarPanelTileEntity.setCurrentEnergyGeneration((mSolarPanelTileEntity.getCurrentEnergyGeneration() & 0xFFFF0000) | pValue);
        }
        if (pIndex == 3) {
            mSolarPanelTileEntity.setCurrentEnergyGeneration(mSolarPanelTileEntity.getCurrentEnergyGeneration() & 0xFFFF | (pValue << 16));
        }
        if (pIndex == 4) {
            mSolarPanelTileEntity.setSunIntensity(pValue / 100.0f);
        }
    }

    @Override
    public boolean canDragIntoSlot(Slot pSlot) {
        return pSlot.getSlotIndex() >= SolarPanelTileEntity.INVENTORY_SIZE;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer pPlayer, int pSlotIndex) {
        ItemStack itemStack = null;
        Slot slot = getSlot(pSlotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            if (pSlotIndex < SolarPanelTileEntity.INVENTORY_SIZE) {
                // From container to player's inventory.
                if (!mergeItemStack(slotItemStack, SolarPanelTileEntity.INVENTORY_SIZE, inventorySlots.size(), false)) {
                    return null;
                }
            } else {
                // From player's inventory to container.

                // Special treatment for upgrades
                if (slotItemStack.getItem() instanceof UpgradeItem) {
                    int canAdd = Math.min(mSolarPanelTileEntity.additionalUpgradeAllowed(slotItemStack), slotItemStack.stackSize);
                    if (canAdd > 0) {
                        ItemStack merging = slotItemStack.splitStack(canAdd);
                        if (mergeItemStack(merging, 0, SolarPanelTileEntity.INVENTORY_SIZE, false)) {
                            slotItemStack.stackSize += merging.stackSize;
                        }
                        if (slotItemStack.stackSize > 0) {
                            slot.putStack(slotItemStack);
                        } else {
                            slot.putStack(null);
                        }
                    }
                }

                // Normal behaviour.
                if (!mergeItemStack(slotItemStack, 0, SolarPanelTileEntity.INVENTORY_SIZE, false)) {
                    return null;
                }
            }

            if (slotItemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack slotClick(int pSlotIndex, int pMouseButton, int pClickMode, EntityPlayer pPlayer) {
        ItemStack oldItemStack = pPlayer.inventory.getItemStack();
        if (SolarPanelTileEntity.UPGRADE_SLOTS.contains(pSlotIndex) && oldItemStack != null && oldItemStack.getItem() instanceof UpgradeItem) {
            if (pClickMode == CLICK_MODE_NORMAL && (pMouseButton == MOUSE_LEFT_CLICK || pMouseButton == MOUSE_RIGHT_CLICK)) {
                // TODO add a check for slot -999 (had a crash before line just below)
                ItemStack currentItemInSlot = mSolarPanelTileEntity.getStackInSlot(pSlotIndex);
                if (currentItemInSlot == null || Utils.itemStacksEqualIgnoreStackSize(oldItemStack, currentItemInSlot)) {
                    int canAdd = mSolarPanelTileEntity.additionalUpgradeAllowed(oldItemStack);
                    if (canAdd > 0) {
                        if (pMouseButton == MOUSE_RIGHT_CLICK) {
                            canAdd = 1;
                        }
                        ItemStack newStack;
                        if (canAdd >= oldItemStack.stackSize) {
                            newStack = oldItemStack;
                            oldItemStack = null;
                        } else {
                            newStack = oldItemStack.splitStack(canAdd);
                        }
                        pPlayer.inventory.setItemStack(newStack);
                        ItemStack result = super.slotClick(pSlotIndex, pMouseButton, pClickMode, pPlayer);
                        pPlayer.inventory.setItemStack(oldItemStack);
                        return result;
                    }
                }
            }
        }
        return super.slotClick(pSlotIndex, pMouseButton, pClickMode, pPlayer);
    }
}
