package com.nauktis.solarflux.init;

import com.google.common.collect.Lists;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.items.CraftingItem;
import com.nauktis.solarflux.items.UpgradeItem;
import com.nauktis.solarflux.utility.Lang;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.List;

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
    public static Item mUpgradeFurnace;

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
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.efficiency"), ModConfiguration.getEfficiencyUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getEfficiencyUpgradeReturnsToScale()));
            mUpgradeEfficiency = new UpgradeItem("upgradeEfficiency", ModConfiguration.getEfficiencyUpgradeMax(), infos);
            GameRegistry.registerItem(mUpgradeEfficiency, "upgradeEfficiency");
            anyUpgrade = true;
        }
        if (ModConfiguration.isLowLightUpgradeActive()) {
            mUpgradeLowLight = new UpgradeItem("upgradeLowLight", ModConfiguration.getLowLightUpgradeMax(), Lists.newArrayList(Lang.localise("upgrade.low.light")));
            GameRegistry.registerItem(mUpgradeLowLight, "upgradeLowLight");
            anyUpgrade = true;
        }
        if (ModConfiguration.isTraversalUpgradeActive()) {
            mUpgradeTraversal = new UpgradeItem(
                    "upgradeTraversal",
                    ModConfiguration.getTraversalUpgradeMax(),
                    Lists.newArrayList(String.format(Lang.localise("upgrade.traversal"), ModConfiguration.getTraversalUpgradeIncrease())));
            GameRegistry.registerItem(mUpgradeTraversal, "upgradeTraversal");
            anyUpgrade = true;
        }
        if (ModConfiguration.isTransferRateUpgradeActive()) {
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.transfer"), ModConfiguration.getTransferRateUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getTransferRateUpgradeReturnsToScale()));
            mUpgradeTransferRate = new UpgradeItem("upgradeTransferRate", ModConfiguration.getTransferRateUpgradeMax(), infos);
            GameRegistry.registerItem(mUpgradeTransferRate, "upgradeTransferRate");
            anyUpgrade = true;
        }
        if (ModConfiguration.isCapacityUpgradeActive()) {
            List<String> infos = Lists.newArrayList();
            infos.add(String.format(Lang.localise("upgrade.capacity"), ModConfiguration.getCapacityUpgradeIncrease() * 100));
            infos.add(localiseReturnsToScale(ModConfiguration.getCapacityUpgradeReturnsToScale()));
            mUpgradeCapacity = new UpgradeItem("upgradeCapacity", ModConfiguration.getCapacityUpgradeMax(), infos);
            GameRegistry.registerItem(mUpgradeCapacity, "upgradeCapacity");
            anyUpgrade = true;
        }
        if (ModConfiguration.isFurnaceUpgradeActive()) {
            mUpgradeFurnace = new UpgradeItem("upgradeFurnace", 1, Lists.newArrayList(Lang.localise("upgrade.furnace")));
            GameRegistry.registerItem(mUpgradeFurnace, "upgradeFurnace");
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
