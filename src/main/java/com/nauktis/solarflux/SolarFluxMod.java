package com.nauktis.solarflux;

import com.nauktis.solarflux.init.ModBlocks;
import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.init.ModRecipes;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION)
public class SolarFluxMod {

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		ModBlocks.initialize();
		ModItems.initialize();
		ModRecipes.initialize();
	}
}
