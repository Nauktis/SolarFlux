package com.nauktis.solarflux.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.google.common.base.Preconditions;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ModConfiguration {
	private static Configuration mConfiguration;
	private static float mSolarPanelHeight;
	private static boolean mAutoBalanceEnergy;
	private static boolean mUseThermalExpansionRecipes;

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
		mSolarPanelHeight = mConfiguration.getFloat("SolarPanelHeight", Configuration.CATEGORY_GENERAL, 0.375F, 0.1F, 1.0F, "The height of the Solar Panel blocks.");
		mAutoBalanceEnergy = mConfiguration.getBoolean("BalanceEnergy", Configuration.CATEGORY_GENERAL, true, "Neighbor solar panels share their energy if set to true.");
		mUseThermalExpansionRecipes = mConfiguration.getBoolean("UseThermalExpansionRecipes", Configuration.CATEGORY_GENERAL, true, "Use Thermal Expansion recipes.");

		if (mConfiguration.hasChanged()) {
			SolarFluxMod.log.info("Configuration saved");
			mConfiguration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent pEvent) {
		if (pEvent.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
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
}
