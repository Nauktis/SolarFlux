package com.nauktis.solarflux.init;

import com.google.common.collect.Lists;
import com.nauktis.core.block.BaseModBlock;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.items.SolarPanelItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.List;

public class ModBlocks {
    private static final List<BaseModBlock> mSolarPanels = Lists.newArrayList();

    private ModBlocks() {
    }

    public static void initialize() {
        SolarFluxMod.log.info("Registering blocks");
        mSolarPanels.clear();
        for (int tierIndex = 0; tierIndex < ModConfiguration.getTierConfigurations().size(); tierIndex++) {
            int registeredTierName = tierIndex + 1;
            SolarPanelBlock block = new SolarPanelBlock("solar" + tierIndex, tierIndex);
            if (tierIndex >= ModConfiguration.DEFAULT_TIER_CONFIGURATIONS.size()) {
                // We need to reuse the highest texture we have available.
                block.setBlockTextureName(mSolarPanels.get(mSolarPanels.size() - 1).getBlockTextureName());
            }
            GameRegistry.registerBlock(block, SolarPanelItemBlock.class, "solar" + registeredTierName);
            mSolarPanels.add(block);
        }
    }

    public static List<BaseModBlock> getSolarPanels() {
        return mSolarPanels;
    }
}
