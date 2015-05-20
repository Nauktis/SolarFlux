package com.nauktis.solarflux.init;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.items.CraftingItem;
import com.nauktis.solarflux.items.UpgradeItem;
import com.nauktis.solarflux.utility.Lang;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {
    public static final Item mMirror = new CraftingItem("mirror");
    public static final Item mSolarCell1 = new CraftingItem("solarCell1");
    public static final Item mSolarCell2 = new CraftingItem("solarCell2");
    public static final Item mSolarCell3 = new CraftingItem("solarCell3");
    public static final Item mSolarCell4 = new CraftingItem("solarCell4");

    // Upgrades
    public static Item mUpgradeBlank;
    public static Item mUpgradeEfficiency;
    public static Item mUpgradeLowLight;
    public static Item mUpgradeTraversal;
    public static Item mUpgradeTransferRate;
    public static Item mUpgradeCapacity;

    private ModItems() {
    }

    public static void initialize() {
        SolarFluxMod.log.info("Registering items");
        GameRegistry.registerItem(mMirror, "mirror");
        GameRegistry.registerItem(mSolarCell1, "solarCell1");
        GameRegistry.registerItem(mSolarCell2, "solarCell2");
        GameRegistry.registerItem(mSolarCell3, "solarCell3");
        GameRegistry.registerItem(mSolarCell4, "solarCell4");

        boolean anyUpgrade = false;
        if (ModConfiguration.isEfficiencyUpgradeActive()) {
            String info = String.format(Lang.localise("upgrade.efficiency"), ModConfiguration.getEfficiencyUpgradeIncrease() * 100);
            info += "\n" + localiseReturnsToScale(ModConfiguration.getEfficiencyUpgradeReturnsToScale());

            mUpgradeEfficiency = new UpgradeItem("upgradeEfficiency", ModConfiguration.getEfficiencyUpgradeMax(), info);
            GameRegistry.registerItem(mUpgradeEfficiency, "upgradeEfficiency");
            anyUpgrade = true;
        }
        if (ModConfiguration.isLowLightUpgradeActive()) {
            mUpgradeLowLight = new UpgradeItem("upgradeLowLight", ModConfiguration.getLowLightUpgradeMax(), Lang.localise("upgrade.low.light"));
            GameRegistry.registerItem(mUpgradeLowLight, "upgradeLowLight");
            anyUpgrade = true;
        }
        if (ModConfiguration.isTraversalUpgradeActive()) {
            mUpgradeTraversal = new UpgradeItem(
                    "upgradeTraversal",
                    ModConfiguration.getTraversalUpgradeMax(),
                    String.format(Lang.localise("upgrade.traversal"), ModConfiguration.getTraversalUpgradeIncrease()));
            GameRegistry.registerItem(mUpgradeTraversal, "upgradeTraversal");
            anyUpgrade = true;
        }
        if (ModConfiguration.isTransferRateUpgradeActive()) {
            String info = String.format(Lang.localise("upgrade.transfer"), ModConfiguration.getTransferRateUpgradeIncrease() * 100);
            info += "\n" + localiseReturnsToScale(ModConfiguration.getTransferRateUpgradeReturnsToScale());

            mUpgradeTransferRate = new UpgradeItem("upgradeTransferRate", ModConfiguration.getTransferRateUpgradeMax(), info);
            GameRegistry.registerItem(mUpgradeTransferRate, "upgradeTransferRate");
            anyUpgrade = true;
        }
        if (ModConfiguration.isCapacityUpgradeActive()) {
            String info = String.format(Lang.localise("upgrade.capacity"), ModConfiguration.getCapacityUpgradeIncrease() * 100);
            info += "\n" + localiseReturnsToScale(ModConfiguration.getCapacityUpgradeReturnsToScale());

            mUpgradeCapacity = new UpgradeItem("upgradeCapacity", ModConfiguration.getCapacityUpgradeMax(), info);
            GameRegistry.registerItem(mUpgradeCapacity, "upgradeCapacity");
            anyUpgrade = true;
        }
        if (anyUpgrade) {
            mUpgradeBlank = new CraftingItem("upgradeBlank");
            GameRegistry.registerItem(mUpgradeBlank, "upgradeBlank");
        }
    }

    private static String localiseReturnsToScale(float pValue) {
        if (pValue < 1) {
            return Lang.localise("decreasingReturnsToScale");
        } else if (pValue > 1) {
            return Lang.localise("increasingReturnsToScale");
        }
        return Lang.localise("constantReturnsToScale");
    }
}
