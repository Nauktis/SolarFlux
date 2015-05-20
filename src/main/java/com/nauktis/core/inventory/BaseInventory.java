package com.nauktis.core.inventory;

import com.nauktis.solarflux.reference.NBTConstants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static com.google.common.base.Preconditions.checkArgument;

public class BaseInventory implements IInventory {
    private final String mInventoryName;
    private ItemStack[] mItemStacks;

    public BaseInventory(String pInventoryName, int pSize) {
        mInventoryName = pInventoryName;
        mItemStacks = new ItemStack[pSize];
    }

    public void writeToNBT(NBTTagCompound pNBT) {
        checkArgument(getSizeInventory() <= Byte.MAX_VALUE, "Inventory size too big.");
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); ++i) {
            ItemStack itemStack = getStackInSlot(i);
            if (itemStack != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte(NBTConstants.SLOT, (byte) i);
                itemStack.writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        pNBT.setTag(NBTConstants.ITEMS, tagList);
    }

    public void readFromNBT(NBTTagCompound pNBT) {
        NBTTagList tagList = pNBT.getTagList(NBTConstants.ITEMS, 10);
        mItemStacks = new ItemStack[getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte(NBTConstants.SLOT);
            setInventorySlotContents(slotIndex, ItemStack.loadItemStackFromNBT(tagCompound));
        }
    }

    @Override
    public int getSizeInventory() {
        return mItemStacks.length;
    }

    /**
     * Returns true if the inventory does not contain any ItemStack.
     */
    public boolean isEmpty() {
        return getOccupiedSlotsNumber() == 0;
    }

    /**
     * Returns the number of slots that are actually occupied by an ItemStack.
     */
    public int getOccupiedSlotsNumber() {
        int count = 0;
        for (int i = 0; i < getSizeInventory(); ++i) {
            if (getStackInSlot(i) != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotIndex) {
        return mItemStacks[pSlotIndex];
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrementAmount) {
        ItemStack itemStack = getStackInSlot(pSlotIndex);
        if (itemStack != null) {
            if (itemStack.stackSize <= pDecrementAmount) {
                setInventorySlotContents(pSlotIndex, null);
            } else {
                itemStack = itemStack.splitStack(pDecrementAmount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(pSlotIndex, null);
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotIndex) {
        ItemStack itemStack = getStackInSlot(pSlotIndex);
        if (itemStack != null) {
            setInventorySlotContents(pSlotIndex, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int pSlotIndex, ItemStack pItemStack) {
        mItemStacks[pSlotIndex] = pItemStack;
        if (pItemStack != null && pItemStack.stackSize > getInventoryStackLimit()) {
            pItemStack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return mInventoryName;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer pEntityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int pSlotIndex, ItemStack pItemStack) {
        return true;
    }
}
