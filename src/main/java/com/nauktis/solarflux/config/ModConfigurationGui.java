package com.nauktis.solarflux.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.client.config.GuiConfig;

public class ModConfigurationGui extends GuiConfig {
	public ModConfigurationGui(GuiScreen pGuiScreen) {
		super(pGuiScreen, new ConfigElement(ModConfiguration.getConfiguration().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, true, true, GuiConfig
				.getAbridgedConfigPath(ModConfiguration.getConfiguration().toString()));
	}
}
