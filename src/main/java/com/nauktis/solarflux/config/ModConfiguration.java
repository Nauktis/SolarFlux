package com.nauktis.solarflux.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.nauktis.core.utility.MetricUnits;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.List;

public class ModConfiguration {
    public static final List<TierConfiguration> DEFAULT_TIER_CONFIGURATIONS = ImmutableList.of(
            new TierConfiguration(2, 50 * MetricUnits.KILO),
            new TierConfiguration(2 * 8, 250 * MetricUnits.KILO),
            new TierConfiguration(2 * 8 * 4, 850 * MetricUnits.KILO),
            new TierConfiguration(2 * 8 * 4 * 4, 4 * MetricUnits.MEGA),
            new TierConfiguration(2 * 8 * 4 * 4 * 4, 16 * MetricUnits.MEGA),
            new TierConfiguration(2 * 8 * 4 * 4 * 4 * 4, 60 * MetricUnits.MEGA));
    private static final String UPGRADE_CATEGORY = "upgrades";
    private static Configuration mConfiguration;
    private static float mSolarPanelHeight;
    private static boolean mAutoBalanceEnergy;
    private static boolean mUseThermalExpansionRecipes;
    private static boolean mKeepEnergyWhenDismantled;
    private static boolean mKeepInventoryWhenDismantled;
    private static float mRainGenerationFactor;
    private static float mThunderGenerationFactor;
    private static boolean mInfoOnSneakClick;
    private static List<TierConfiguration> mTierConfigurations;
    private static boolean mEfficiencyUpgradeActive;
    private static float mEfficiencyUpgradeIncrease;
    private static float mEfficiencyUpgradeReturnsToScale;
    private static int mEfficiencyUpgradeMax;
    private static boolean mLowLightUpgradeActive;
    private static int mLowLightUpgradeMax;
    private static boolean mTraversalUpgradeActive;
    private static int mTraversalUpgradeIncrease;
    private static int mTraversalUpgradeUpdateRate;
    private static int mTraversalUpgradeMax;
    private static boolean mTransferRateUpgradeActive;
    private static float mTransferRateUpgradeIncrease;
    private static float mTransferRateUpgradeReturnsToScale;
    private static int mTransferRateUpgradeMax;
    private static boolean mCapacityUpgradeActive;
    private static float mCapacityUpgradeIncrease;
    private static float mCapacityUpgradeReturnsToScale;
    private static int mCapacityUpgradeMax;

    public static void initialize(File pConfigFile) {
        SolarFluxMod.log.info("Initialization of configuration");
        if (mConfiguration == null) {
            mConfiguration = new Configuration(pConfigFile);
        }
        FMLCommonHandler.instance().bus().register(new ModConfiguration());
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Preconditions.checkNotNull(mConfiguration);
        mSolarPanelHeight = mConfiguration.getFloat(
                "SolarPanelHeight",
                Configuration.CATEGORY_GENERAL,
                0.375F,
                0.1F,
                1,
                "The height of the Solar Panel blocks.");
        mAutoBalanceEnergy = mConfiguration.getBoolean(
                "BalanceEnergy",
                Configuration.CATEGORY_GENERAL,
                true,
                "Neighbor solar panels share their energy if set to true.");
        mUseThermalExpansionRecipes = mConfiguration.getBoolean(
                "UseThermalExpansionRecipes",
                Configuration.CATEGORY_GENERAL,
                true,
                "Use Thermal Expansion recipes.");
        mKeepEnergyWhenDismantled = mConfiguration.getBoolean(
                "KeepEnergyWhenDismantled",
                Configuration.CATEGORY_GENERAL,
                true,
                "Whether or not the solar panels keep their internal energy when dismantled with a wrench.");
        mKeepInventoryWhenDismantled = mConfiguration.getBoolean(
                "KeepInventoryWhenDismantled",
                Configuration.CATEGORY_GENERAL,
                true,
                "Whether or not the solar panels keep their internal inventory when dismantled with a wrench.");
        mRainGenerationFactor = mConfiguration.getFloat(
                "RainProductionFactor",
                Configuration.CATEGORY_GENERAL,
                0.4f,
                0,
                1,
                "Factor used to reduce the energy generation during rainy weather.");
        mThunderGenerationFactor = mConfiguration.getFloat(
                "ThunderProductionFactor",
                Configuration.CATEGORY_GENERAL,
                0.4f,
                0,
                1,
                "Factor used to reduce the energy generation during stormy weather.");
        mInfoOnSneakClick = mConfiguration.getBoolean(
                "SneakClickInfo",
                Configuration.CATEGORY_GENERAL,
                false,
                "Display Solar Panel information on right click while sneaking.");

        loadTierConfigurations();
        loadUpgradesConfiguration();

        if (mConfiguration.hasChanged()) {
            SolarFluxMod.log.info("Configuration saved");
            mConfiguration.save();
        }
    }

    private static void loadUpgradesConfiguration() {
        //@formatter:off

        // Efficiency upgrade
        mEfficiencyUpgradeActive = mConfiguration.getBoolean("EfficiencyUpgradeActive", UPGRADE_CATEGORY, true, "Whether or not efficiency upgrades should be added to the game.");
        mEfficiencyUpgradeIncrease = mConfiguration.getFloat("EfficiencyUpgradeIncrease", UPGRADE_CATEGORY, 0.05F, 0.01F, 10, "Factor by which the energy production is increased per upgrade.");
        mEfficiencyUpgradeReturnsToScale = mConfiguration.getFloat("EfficiencyUpgradeReturnsToScale", UPGRADE_CATEGORY, 0.9F, 0.1F, 2, "Returns to scale. How does the efficiency scales as you add more upgrades. 1 is linear. Below 1 reduces the efficiency as you add upgrades, Above 1 does the opposite.");
        mEfficiencyUpgradeMax = mConfiguration.getInt("EfficiencyUpgradeMax", UPGRADE_CATEGORY, 8, 1, 256, "Maximum number of efficiency upgrade that can be added to a single solar panel.");

        // Low light upgrade
        mLowLightUpgradeActive = mConfiguration.getBoolean("LowLightUpgradeActive", UPGRADE_CATEGORY, true, "Whether or not low light upgrades should be added to the game.");
        mLowLightUpgradeMax = mConfiguration.getInt("LowLightUpgradeMax", UPGRADE_CATEGORY, 8, 1, 256, "Maximum number of low light upgrade that can be added to a single solar panel.");

        // Traversal upgrade
        mTraversalUpgradeActive = mConfiguration.getBoolean("TraversalUpgradeActive", UPGRADE_CATEGORY, true, "Whether or not traversal upgrades should be added to the game.");
        mTraversalUpgradeIncrease = mConfiguration.getInt("TraversalUpgradeIncrease", UPGRADE_CATEGORY, 1, 1, 10, "How many extra machines can be traversed per extra upgrade.");
        mTraversalUpgradeUpdateRate = mConfiguration.getInt("TraversalUpgradeUpdateRate", UPGRADE_CATEGORY, 25, 1, 20 * 60, "Update rate of traversal. Increase to reduce lag. But machines will be discovered slower.");
        mTraversalUpgradeMax = mConfiguration.getInt("TraversalUpgradeMax", UPGRADE_CATEGORY, 64, 1, 256, "Maximum number of traversal upgrade that can be added to a single solar panel.");

        // Transfer upgrade
        mTransferRateUpgradeActive = mConfiguration.getBoolean("TransferRateUpgradeActive", UPGRADE_CATEGORY, true, "Whether or not transfer rate upgrades should be added to the game.");
        mTransferRateUpgradeIncrease = mConfiguration.getFloat("TransferRateUpgradeIncrease", UPGRADE_CATEGORY, 0.1F, 0.01F, 10, "Factor by which the transfer rate is increased per upgrade.");
        mTransferRateUpgradeReturnsToScale = mConfiguration.getFloat("TransferRateUpgradeReturnsToScale", UPGRADE_CATEGORY, 0.9F, 0.1F, 2, "Returns to scale. How does the transfer rate scales as you add more upgrades. 1 is linear. Below 1 reduces the efficiency as you add upgrades, Above 1 does the opposite.");
        mTransferRateUpgradeMax = mConfiguration.getInt("TransferRateUpgradeMax", UPGRADE_CATEGORY, 8, 1, 256, "Maximum number of transfer rate upgrade that can be added to a single solar panel.");

        // Capacity upgrade
        mCapacityUpgradeActive = mConfiguration.getBoolean("CapacityUpgradeActive", UPGRADE_CATEGORY, true, "Whether or not capacity upgrades should be added to the game.");
        mCapacityUpgradeIncrease = mConfiguration.getFloat("CapacityUpgradeIncrease", UPGRADE_CATEGORY, 0.1F, 0.01F, 10, "Factor by which the capacity is increased per upgrade.");
        mCapacityUpgradeReturnsToScale = mConfiguration.getFloat("CapacityUpgradeReturnsToScale", UPGRADE_CATEGORY, 1, 0.1F, 2, "Returns to scale. How does the transfer rate scales as you add more upgrades. 1 is linear. Below 1 reduces the efficiency as you add upgrades, Above 1 does the opposite.");
        mCapacityUpgradeMax = mConfiguration.getInt("CapacityUpgradeMax", UPGRADE_CATEGORY, 16, 1, 256, "Maximum number of capacity upgrade that can be added to a single solar panel.");
        //@formatter:on
    }

    private static void loadTierConfigurations() {
        mTierConfigurations = Lists.newArrayList();
        for (int tier = 0; ; ++tier) {
            TierConfiguration config = loadTierConfiguration(tier);
            if (config == null) {
                break;
            } else {
                mTierConfigurations.add(config);
            }
        }
    }

    private static TierConfiguration loadTierConfiguration(int pTierNumber) {
        String category = "solar_panel_tier" + pTierNumber;
        boolean active = mConfiguration.getBoolean(
                "Active",
                category,
                pTierNumber < DEFAULT_TIER_CONFIGURATIONS.size(),
                "Whether or not this tier of Solar Panel should be added to the game.");
        if (active) {
            // Find a default config for the default values
            TierConfiguration defaultConfig;
            if (pTierNumber < DEFAULT_TIER_CONFIGURATIONS.size()) {
                defaultConfig = DEFAULT_TIER_CONFIGURATIONS.get(pTierNumber);
            } else {
                int deltaTier = pTierNumber - DEFAULT_TIER_CONFIGURATIONS.size() + 1;
                TierConfiguration lastConfig = DEFAULT_TIER_CONFIGURATIONS.get(DEFAULT_TIER_CONFIGURATIONS.size() - 1);
                defaultConfig = new TierConfiguration(
                        (int) (lastConfig.getMaximumEnergyGeneration() * Math.pow(2, deltaTier)),
                        (int) (lastConfig.getMaximumEnergyTransfer() * Math.pow(2, deltaTier)),
                        (int) (lastConfig.getCapacity() * Math.pow(1.2, deltaTier)));
            }

            return new TierConfiguration(
                    mConfiguration.getInt(
                            "MaximumEnergyGeneration",
                            category,
                            defaultConfig.getMaximumEnergyGeneration(),
                            1,
                            2 * MetricUnits.GIGA,
                            "Maximum amount of RF generated per tick."),
                    mConfiguration.getInt(
                            "MaximumEnergyTransfer",
                            category,
                            defaultConfig.getMaximumEnergyTransfer(),
                            1,
                            2 * MetricUnits.GIGA,
                            "Maximum amount of RF transferred per tick."),
                    mConfiguration.getInt(
                            "Capacity",
                            category,
                            defaultConfig.getCapacity(),
                            1,
                            2 * MetricUnits.GIGA,
                            "Amount of RF that can be stored."));
        }
        return null;
    }

    public static Configuration getConfiguration() {
        return mConfiguration;
    }

    public static float getSolarPanelHeight() {
        return mSolarPanelHeight;
    }

    public static boolean isSolarPanelFullBlock() {
        return mSolarPanelHeight == 1.0F;
    }

    public static boolean doesAutoBalanceEnergy() {
        return mAutoBalanceEnergy;
    }

    public static boolean useThermalExpansionRecipes() {
        return mUseThermalExpansionRecipes;
    }

    public static boolean doesKeepEnergyWhenDismantled() {
        return mKeepEnergyWhenDismantled;
    }

    public static boolean doesKeepInventoryWhenDismantled() {
        return mKeepInventoryWhenDismantled;
    }

    public static float getRainGenerationFactor() {
        return mRainGenerationFactor;
    }

    public static float getThunderGenerationFactor() {
        return mThunderGenerationFactor;
    }

    public static boolean infoOnSneakClick() {
        return mInfoOnSneakClick;
    }

    public static List<TierConfiguration> getTierConfigurations() {
        return mTierConfigurations;
    }

    public static TierConfiguration getTierConfiguration(int pTierIndex) {
        return mTierConfigurations.get(pTierIndex);
    }

    public static boolean isEfficiencyUpgradeActive() {
        return mEfficiencyUpgradeActive;
    }

    public static float getEfficiencyUpgradeIncrease() {
        return mEfficiencyUpgradeIncrease;
    }

    public static int getEfficiencyUpgradeMax() {
        return mEfficiencyUpgradeMax;
    }

    public static float getEfficiencyUpgradeReturnsToScale() {
        return mEfficiencyUpgradeReturnsToScale;
    }

    public static boolean isLowLightUpgradeActive() {
        return mLowLightUpgradeActive;
    }

    public static int getLowLightUpgradeMax() {
        return mLowLightUpgradeMax;
    }

    public static boolean isTraversalUpgradeActive() {
        return mTraversalUpgradeActive;
    }

    public static int getTraversalUpgradeIncrease() {
        return mTraversalUpgradeIncrease;
    }

    public static int getTraversalUpgradeMax() {
        return mTraversalUpgradeMax;
    }

    public static int getTraversalUpgradeUpdateRate() {
        return mTraversalUpgradeUpdateRate;
    }

    public static boolean isTransferRateUpgradeActive() {
        return mTransferRateUpgradeActive;
    }

    public static float getTransferRateUpgradeIncrease() {
        return mTransferRateUpgradeIncrease;
    }

    public static int getTransferRateUpgradeMax() {
        return mTransferRateUpgradeMax;
    }

    public static float getTransferRateUpgradeReturnsToScale() {
        return mTransferRateUpgradeReturnsToScale;
    }

    public static boolean isCapacityUpgradeActive() {
        return mCapacityUpgradeActive;
    }

    public static float getCapacityUpgradeIncrease() {
        return mCapacityUpgradeIncrease;
    }

    public static int getCapacityUpgradeMax() {
        return mCapacityUpgradeMax;
    }

    public static float getCapacityUpgradeReturnsToScale() {
        return mCapacityUpgradeReturnsToScale;
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent pEvent) {
        if (pEvent.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}
