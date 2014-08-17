package com.nauktis.solarflux;

import com.nauktis.core.utility.LogHelper;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.gui.SFGuiHandler;
import com.nauktis.solarflux.init.ModBlocks;
import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.init.ModRecipes;
import com.nauktis.solarflux.proxy.IProxy;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION)
public class SolarFluxMod {
	public static LogHelper log = new LogHelper(Reference.MOD_ID);

	@Mod.Instance(Reference.MOD_ID)
	public static SolarFluxMod sInstance;

	@SidedProxy(clientSide = "com.nauktis.solarflux.proxy.ClientProxy", serverSide = "com.nauktis.solarflux.proxy.ServerProxy")
	public static IProxy sProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent pEvent) {
		log.info("Pre Initialization");
		GameRegistry.registerTileEntity(SolarPanelTileEntity.class, "solar");
		ModBlocks.initialize();
		ModItems.initialize();
		ModRecipes.initialize();
	}

	@EventHandler
	public void init(FMLInitializationEvent pEvent) {
		log.info("Registering GUI Handler");
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new SFGuiHandler());
	}
}
