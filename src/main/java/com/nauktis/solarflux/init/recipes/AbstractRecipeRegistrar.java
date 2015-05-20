package com.nauktis.solarflux.init.recipes;

import com.nauktis.solarflux.SolarFluxMod;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public abstract class AbstractRecipeRegistrar {
    /**
     * Returns true if the name provided is know by the ore dictionary.
     */
    protected static boolean knowsOre(String pName) {
        return !OreDictionary.getOres(pName).isEmpty();
    }

    protected String getFirstOreAvailable(String... pNames) {
        for (String name : pNames) {
            if (knowsOre(name)) {
                return name;
            }
            SolarFluxMod.log.info("Unable to find %s for recipe.", name);
        }
        throw new RuntimeException(String.format("Error while registering recipes. None of the following are known %s", Arrays.toString(pNames)));
    }

    public abstract void registerRecipes();
}
