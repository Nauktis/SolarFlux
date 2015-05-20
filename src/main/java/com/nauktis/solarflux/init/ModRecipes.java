package com.nauktis.solarflux.init;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.init.recipes.AbstractRecipeRegistrar;
import com.nauktis.solarflux.init.recipes.ThermalExpansionRecipeRegistrar;
import com.nauktis.solarflux.init.recipes.VanillaRecipeRegistrar;
import com.nauktis.solarflux.reference.Reference;
import cpw.mods.fml.common.Loader;

public class ModRecipes {
    private ModRecipes() {
    }

    public static void initialize() {
        SolarFluxMod.log.info("Registering recipes");
        AbstractRecipeRegistrar registrar = new VanillaRecipeRegistrar();
        if (ModConfiguration.useThermalExpansionRecipes() && Loader.isModLoaded(Reference.THERMAL_EXPANSION_MOD_ID)) {
            registrar = new ThermalExpansionRecipeRegistrar();
        }
        registrar.registerRecipes();
    }
}
