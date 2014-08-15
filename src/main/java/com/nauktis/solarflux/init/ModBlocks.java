package com.nauktis.solarflux.init;

import net.minecraft.block.Block;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	public static final Block mSolar = new SolarPanelBlock("solar");

	private ModBlocks() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering blocks");
		GameRegistry.registerBlock(mSolar, "solar");
	}
}
