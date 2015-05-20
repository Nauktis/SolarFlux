package com.nauktis.solarflux;

import com.nauktis.core.utility.LogHelper;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.gui.GuiHandler;
import com.nauktis.solarflux.init.ModBlocks;
import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.init.ModRecipes;
import com.nauktis.solarflux.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, dependencies = "after:" + Reference.THERMAL_EXPANSION_MOD_ID + ";after:" + Reference.THERMAL_FOUNDATION_MOD_ID)
public class SolarFluxMod {
    @Mod.Instance(Reference.MOD_ID)
    public static SolarFluxMod mInstance;

    public static final LogHelper log = new LogHelper(Reference.MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent pEvent) {
        log.info("Pre Initialization");
        ModConfiguration.initialize(pEvent.getSuggestedConfigurationFile());
        GameRegistry.registerTileEntity(SolarPanelTileEntity.class, "solar");
        ModBlocks.initialize();
        ModItems.initialize();
    }

    @EventHandler
    public void init(FMLInitializationEvent pEvent) {
        log.info("Initialization");
        ModRecipes.initialize();
        log.info("Registering GUI Handler");
        NetworkRegistry.INSTANCE.registerGuiHandler(mInstance, new GuiHandler());
    }
}
