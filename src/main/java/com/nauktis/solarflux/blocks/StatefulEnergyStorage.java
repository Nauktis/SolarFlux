package com.nauktis.solarflux.blocks;

import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import com.google.common.base.Objects;
import com.nauktis.solarflux.reference.NBTConstants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class StatefulEnergyStorage implements IEnergyStorage {
    protected int mEnergy;
    protected int mCapacity;
    protected int mMaxTransferReceive;
    protected int mMaxTransferExtract;

    public StatefulEnergyStorage(int pCapacity) {
        this(pCapacity, pCapacity, pCapacity);
    }

    public StatefulEnergyStorage(int pCapacity, int pMaxTransfer) {
        this(pCapacity, pMaxTransfer, pMaxTransfer);
    }

    public StatefulEnergyStorage(int pCapacity, int pMaxTransferReceive, int pMaxTransferExtract) {
        mCapacity = pCapacity;
        mMaxTransferReceive = pMaxTransferReceive;
        mMaxTransferExtract = pMaxTransferExtract;
    }

    public void readFromNBT(NBTTagCompound pNbt) {
        setEnergyStored(pNbt.getInteger(NBTConstants.ENERGY));
    }

    public void writeToNBT(NBTTagCompound pNbt) {
        pNbt.setInteger(NBTConstants.ENERGY, getEnergyStored());
    }

    /**
     * Returns the maximum amount of energy the storage can receive.
     */
    public int getMaxReceive() {
        return Math.min(mCapacity - mEnergy, mMaxTransferReceive);
    }

    /**
     * Returns the maximum amount of energy that can be extracted from the storage.
     */
    public int getMaxExtract() {
        return Math.min(mEnergy, mMaxTransferExtract);
    }

    @Override
    public int receiveEnergy(int pMaxReceive, boolean pSimulate) {
        int energyReceived = Math.min(getMaxReceive(), Math.max(pMaxReceive, 0));
        if (!pSimulate) {
            mEnergy += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int pMaxExtract, boolean pSimulate) {
        int energyExtracted = Math.min(getMaxExtract(), Math.max(pMaxExtract, 0));
        if (!pSimulate) {
            mEnergy -= energyExtracted;
        }
        return energyExtracted;
    }

    /**
     * Sends the maximum amount of energy possible to the {@link IEnergyReceiver}
     */
    public int sendMaxTo(IEnergyReceiver pEnergyReceiver, ForgeDirection pFrom) {
        return extractEnergy(pEnergyReceiver.receiveEnergy(pFrom, getMaxExtract(), false), false);
    }

    /**
     * Tries to balance the amount of energy between the two {@link StatefulEnergyStorage}
     */
    public int autoBalanceEnergy(StatefulEnergyStorage pOtherEnergyStorage) {
        int delta = getEnergyStored() - pOtherEnergyStorage.getEnergyStored();
        if (delta < 0) {
            return pOtherEnergyStorage.autoBalanceEnergy(this);
        } else if (delta > 0 && !pOtherEnergyStorage.isFull()) {
            return extractEnergy(pOtherEnergyStorage.receiveEnergy(delta / 2, false), false);
        }
        return 0;
    }

    public int autoBalanceEnergy(StatefulEnergyStorage pOtherEnergyStorage, int pTransferSpeed) {
        mMaxTransferExtract *= pTransferSpeed;
        mMaxTransferReceive *= pTransferSpeed;
        pOtherEnergyStorage.mMaxTransferExtract *= pTransferSpeed;
        pOtherEnergyStorage.mMaxTransferReceive *= pTransferSpeed;
        int result = autoBalanceEnergy(pOtherEnergyStorage);
        mMaxTransferExtract /= pTransferSpeed;
        mMaxTransferReceive /= pTransferSpeed;
        pOtherEnergyStorage.mMaxTransferExtract /= pTransferSpeed;
        pOtherEnergyStorage.mMaxTransferReceive /= pTransferSpeed;
        return result;
    }

    @Override
    public int getEnergyStored() {
        return mEnergy;
    }

    public void setEnergyStored(int pEnergy) {
        mEnergy = pEnergy;
        if (mEnergy > mCapacity) {
            mEnergy = mCapacity;
        } else if (mEnergy < 0) {
            mEnergy = 0;
        }
    }

    @Override
    public int getMaxEnergyStored() {
        return mCapacity;
    }

    public void setMaxEnergyStored(int pCapacity) {
        mCapacity = pCapacity;
        if (mEnergy > mCapacity) {
            mEnergy = mCapacity;
        }
    }

    public boolean isFull() {
        return getEnergyStored() == getMaxEnergyStored();
    }

    public int getMaxTransferReceive() {
        return mMaxTransferReceive;
    }

    public void setMaxTransferReceive(int pMaxTransferReceive) {
        mMaxTransferReceive = pMaxTransferReceive;
    }

    public int getMaxTransferExtract() {
        return mMaxTransferExtract;
    }

    public void setMaxTransferExtract(int pMaxTransferExtract) {
        mMaxTransferExtract = pMaxTransferExtract;
    }

    public void setMaxTransfer(int pMaxTransfer) {
        setMaxTransferReceive(pMaxTransfer);
        setMaxTransferExtract(pMaxTransfer);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("energy", getEnergyStored())
                .add("capacity", getMaxEnergyStored())
                .add("maxTransferReceive", getMaxTransferReceive())
                .add("maxTransferExtract", getMaxTransferExtract())
                .toString();
    }
}
