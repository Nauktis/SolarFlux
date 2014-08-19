package com.nauktis.solarflux.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.nauktis.solarflux.SolarFluxMod;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	private ModRecipes() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering recipes");
		// Mirror
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mMirror, "ggg", " i ", 'g', "blockGlass", 'i', "ingotIron"));
		// Cells 1
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell1, "ggg", "lll", "mmm", 'g', "blockGlass", 'l', "gemLapis", 'm', ModItems.mMirror));
		// Cells 2
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell2, "clc", "lcl", "msm", 'c', Items.clay_ball, 'l', "gemLapis", 'm', ModItems.mMirror, 's', ModItems.mSolarCell1));
		// Cells 3
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell3, "ggg", "lll", "oco", 'g', "blockGlass", 'l', "dustGlowstone", 'o', Blocks.obsidian, 'c', ModItems.mSolarCell2));
		// Cells 4
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell4, "bbb", "gdg", "qcq", 'b', Items.blaze_rod, 'g', "dustGlowstone", 'd', "blockDiamond", 'q', "blockQuartz", 'c',
				ModItems.mSolarCell3));

		// Core 1
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore1, " m ", "crc", " m ", 'm', ModItems.mMirror, 'c', "ingotCopper", 'r', "dustRedstone"));
		// Core 2
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore2, "ici", "crc", "ici", 'i', "ingotIron", 'c', ModItems.mSolarCore1, 'r', "blockRedstone"));
		// Core 3
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore3, "mcm", "crc", "tct", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore2, 'r', "blockRedstone", 't', "ingotTin"));
		// Core 4
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore4, "mcm", "cgc", "scs", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore3, 'g', "blockGold", 's', "ingotSilver"));
		// Core 5
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore5, "mcm", "cdc", "gcg", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore4, 'd', "blockDiamond", 'g', "ingotGold"));

		// Solar 1
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar1, "mmm", "prp", "ppp", 'm', ModItems.mMirror, 'p', "plankWood", 'r', "dustRedstone"));
		// Solar 2
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar2, "sss", "scs", "sss", 's', ModBlocks.mSolar1, 'c', ModItems.mSolarCore1));
		// Solar 3
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar3, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell1, 's', ModBlocks.mSolar2, 'c', ModItems.mSolarCore2, 'b', "blockCopper"));
		// Solar 4
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar4, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell2, 's', ModBlocks.mSolar3, 'c', ModItems.mSolarCore3, 'b', "blockIron"));
		// Solar 5
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar5, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell3, 's', ModBlocks.mSolar4, 'c', ModItems.mSolarCore4, 'b', "blockGold"));
		// Solar 6
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar6, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell4, 's', ModBlocks.mSolar5, 'c', ModItems.mSolarCore5, 'b', "blockDiamond"));
	}
}
