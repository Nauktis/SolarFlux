package com.nauktis.solarflux.init;

import net.minecraft.block.Block;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.items.SolarPanelItemBlock;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static final Block mSolar1 = new SolarPanelBlock("solar1", 2, 50000);
	public static final Block mSolar2 = new SolarPanelBlock("solar2", 2 * 8, 250000);
	public static final Block mSolar3 = new SolarPanelBlock("solar3", 2 * 8 * 4, 850000);
	public static final Block mSolar4 = new SolarPanelBlock("solar4", 2 * 8 * 4 * 4, 4000000);
	public static final Block mSolar5 = new SolarPanelBlock("solar5", 2 * 8 * 4 * 4 * 4, 16000000);
	public static final Block mSolar6 = new SolarPanelBlock("solar6", 2 * 8 * 4 * 4 * 4 * 4, 60000000);

	private ModBlocks() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering blocks");
		GameRegistry.registerBlock(mSolar1, SolarPanelItemBlock.class, "solar1");
		GameRegistry.registerBlock(mSolar2, SolarPanelItemBlock.class, "solar2");
		GameRegistry.registerBlock(mSolar3, SolarPanelItemBlock.class, "solar3");
		GameRegistry.registerBlock(mSolar4, SolarPanelItemBlock.class, "solar4");
		GameRegistry.registerBlock(mSolar5, SolarPanelItemBlock.class, "solar5");
		GameRegistry.registerBlock(mSolar6, SolarPanelItemBlock.class, "solar6");
	}
}
