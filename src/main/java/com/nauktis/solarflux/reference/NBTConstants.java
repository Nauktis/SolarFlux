package com.nauktis.solarflux.reference;

public final class NBTConstants {
    private static final String NBT_PREFIX = "SF";
    public static final String TIER_INDEX = NBT_PREFIX + "TierIndex";
    public static final String ITEMS = NBT_PREFIX + "Items";
    public static final String SLOT = NBT_PREFIX + "Slot";
    public static final String ENERGY = NBT_PREFIX + "Energy";

    private static final String TOOLTIP_PREFIX = NBT_PREFIX + "TL";
    public static final String TOOLTIP_UPGRADE_COUNT = TOOLTIP_PREFIX + "UpgradeCount";
    public static final String TOOLTIP_TRANSFER_RATE = TOOLTIP_PREFIX + "TransferRate";
    public static final String TOOLTIP_CAPACITY = TOOLTIP_PREFIX + "Capacity";

    private NBTConstants() {

    }
}
