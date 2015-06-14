package com.nauktis.solarflux.init;

import com.google.common.collect.Lists;
import com.nauktis.core.block.BaseModBlock;
import com.nauktis.core.block.icon.ConnectedIconHandler;
import com.nauktis.core.block.icon.IBlockIconHandler;
import com.nauktis.core.block.icon.SingleIconHandler;
import com.nauktis.core.block.icon.TopIconHandler;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.items.SolarPanelItemBlock;
import com.nauktis.solarflux.reference.Reference;
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

            // Texture
            // We need to reuse the highest texture we have available if tier is too high.
            int textureTierIndex = Math.min(tierIndex, ModConfiguration.DEFAULT_TIER_CONFIGURATIONS.size());
            IBlockIconHandler topHandler = new SingleIconHandler(Reference.MOD_ID, "solar" + textureTierIndex + "_0");

            if (ModConfiguration.useConnectedTextures()) {
                topHandler = new ConnectedIconHandler(Reference.MOD_ID, "solar" + textureTierIndex + "_");
            }

            SingleIconHandler sideHandler = new SingleIconHandler(Reference.MOD_ID, "solar" + textureTierIndex + "_side");
            TopIconHandler iconHandler = new TopIconHandler(topHandler, sideHandler);

            SolarPanelBlock block = new SolarPanelBlock("solar" + tierIndex, iconHandler, tierIndex);
            GameRegistry.registerBlock(block, SolarPanelItemBlock.class, "solar" + registeredTierName);
            mSolarPanels.add(block);
        }
    }

    public static List<BaseModBlock> getSolarPanels() {
        return mSolarPanels;
    }
}
