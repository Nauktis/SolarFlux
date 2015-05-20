package com.nauktis.solarflux.config;

public final class TierConfiguration {
    private final int mMaximumEnergyGeneration;
    private final int mMaximumEnergyTransfer;
    private final int mCapacity;

    TierConfiguration(int pMaximumEnergyGeneration, int pMaximumEnergyTransfer, int pCapacity) {
        mMaximumEnergyGeneration = pMaximumEnergyGeneration;
        mMaximumEnergyTransfer = pMaximumEnergyTransfer;
        mCapacity = pCapacity;
    }

    TierConfiguration(int pMaximumEnergyGeneration, int pCapacity) {
        mMaximumEnergyGeneration = pMaximumEnergyGeneration;
        mMaximumEnergyTransfer = pMaximumEnergyGeneration * 8;
        mCapacity = pCapacity;
    }

    /**
     * The maximum amount of RF generated per tick.
     */
    public int getMaximumEnergyGeneration() {
        return mMaximumEnergyGeneration;
    }

    /**
     * The maximum amount of RF transferred to adjacent machines per tick.
     */
    public int getMaximumEnergyTransfer() {
        return mMaximumEnergyTransfer;
    }

    /**
     * The maximum amount of RF that can be stored internally.
     */
    public int getCapacity() {
        return mCapacity;
    }
}
