package com.nauktis.solarflux.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.nauktis.solarflux.SolarFluxMod;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	private ModRecipes() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering recipes");
		// Mirror
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mMirror, "ggg", " i ", 'g', "blockGlass", 'i', "ingotIron"));
		// Core 1
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore1, " m ", "crc", " m ", 'm', new ItemStack(ModItems.mMirror), 'c', "ingotCopper", 'r', "dustRedstone"));
		// Solar 1
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar, "mmm", "prp", "ppp", 'm', new ItemStack(ModItems.mMirror), 'p', "plankWood", 'r', "dustRedstone"));
	}
}
