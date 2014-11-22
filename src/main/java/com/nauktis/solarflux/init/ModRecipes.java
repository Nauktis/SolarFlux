package com.nauktis.solarflux.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.google.common.base.Preconditions;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	private ModRecipes() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering recipes");
		registerCommonRecipes();
		if (!(ModConfiguration.useThermalExpansionRecipes() && Loader.isModLoaded(Reference.THERMAL_EXPANSION_MOD_ID) && registerThermalExpansion())) {
			registerVanilla();
		}
	}

	public static void registerCommonRecipes() {
		// Mirror
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.mMirror, 2), "ggg", " i ", 'g', "blockGlass", 'i', "ingotIron"));
		// Cells
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell1, "ggg", "lll", "mmm", 'g', "blockGlass", 'l', "gemLapis", 'm', ModItems.mMirror));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell2, "clc", "lcl", "msm", 'c', Items.clay_ball, 'l', "gemLapis", 'm', ModItems.mMirror, 's', ModItems.mSolarCell1));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell3, "ggg", "lll", "oco", 'g', "blockGlass", 'l', "dustGlowstone", 'o', Blocks.obsidian, 'c', ModItems.mSolarCell2));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCell4, "bbb", "gdg", "qcq", 'b', Items.blaze_rod, 'g', "dustGlowstone", 'd', "blockDiamond", 'q', "blockQuartz", 'c',
				ModItems.mSolarCell3));
	}

	public static void registerVanilla() {
		SolarFluxMod.log.info("Registering Vanilla recipes");
		String ingotCopper = getOreNameWithFallback("ingotCopper", "ingotIron");
		String ingotSilver = getOreNameWithFallback("ingotSilver", "ingotIron");
		String ingotTin = getOreNameWithFallback("ingotTin", "ingotIron");
		String blockCopper = getOreNameWithFallback("blockCopper", "blockIron");

		// Cores
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore1, " m ", "crc", " m ", 'm', ModItems.mMirror, 'c', ingotCopper, 'r', "dustRedstone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore2, "ici", "crc", "ici", 'i', "ingotIron", 'c', ModItems.mSolarCore1, 'r', "blockRedstone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore3, "mcm", "crc", "tct", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore2, 'r', "blockRedstone", 't', ingotTin));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore4, "mcm", "cgc", "scs", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore3, 'g', "blockGold", 's', ingotSilver));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.mSolarCore5, "mcm", "cdc", "gcg", 'm', ModItems.mMirror, 'c', ModItems.mSolarCore4, 'd', "blockDiamond", 'g', "ingotGold"));
		// Solar Panels
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar1, "mmm", "prp", "ppp", 'm', ModItems.mMirror, 'p', "plankWood", 'r', "dustRedstone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar2, "sss", "scs", "sss", 's', ModBlocks.mSolar1, 'c', ModItems.mSolarCore1));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar3, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell1, 's', ModBlocks.mSolar2, 'c', ModItems.mSolarCore2, 'b', blockCopper));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar4, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell2, 's', ModBlocks.mSolar3, 'c', ModItems.mSolarCore3, 'b', "blockIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar5, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell3, 's', ModBlocks.mSolar4, 'c', ModItems.mSolarCore4, 'b', "blockGold"));
		GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar6, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell4, 's', ModBlocks.mSolar5, 'c', ModItems.mSolarCore5, 'b', "blockDiamond"));
	}

	public static boolean registerThermalExpansion() {
		SolarFluxMod.log.info("Registering %s recipes", Reference.THERMAL_EXPANSION_MOD_ID);
		try {
			// Coil
			ItemStack powerCoilGold = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "powerCoilGold", 1));
			// Frames
			ItemStack frameMachineBasic = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineBasic", 1));
			ItemStack frameMachineHardened = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineHardened", 1));
			ItemStack frameMachineReinforced = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineReinforced", 1));
			ItemStack frameMachineResonant = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "frameMachineResonant", 1));
			// Cells
			ItemStack cellBasic = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "cellBasic", 1));
			ItemStack cellHardened = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "cellHardened", 1));
			ItemStack cellReinforced = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "cellReinforced", 1));
			ItemStack cellResonant = Preconditions.checkNotNull(GameRegistry.findItemStack(Reference.THERMAL_EXPANSION_MOD_ID, "cellResonant", 1));

			// Solar Panels
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar1, "mmm", "prp", "ppp", 'm', ModItems.mMirror, 'p', "plankWood", 'r', "dustRedstone"));
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar2, "sss", "scs", "sss", 's', ModBlocks.mSolar1, 'c', powerCoilGold));
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar3, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell1, 's', ModBlocks.mSolar2, 'c', frameMachineBasic, 'b', cellBasic));
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar4, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell2, 's', ModBlocks.mSolar3, 'c', frameMachineHardened, 'b', cellHardened));
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar5, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell3, 's', ModBlocks.mSolar4, 'c', frameMachineReinforced, 'b', cellReinforced));
			GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.mSolar6, "ppp", "scs", "sbs", 'p', ModItems.mSolarCell4, 's', ModBlocks.mSolar5, 'c', frameMachineResonant, 'b', cellResonant));
			return true;
		} catch (NullPointerException e) {
			SolarFluxMod.log.info("Failed to register %s recipes. Cause: %s", Reference.THERMAL_EXPANSION_MOD_ID, e);
		}
		return false;
	}

	private static boolean knowsOre(String pName) {
		if (OreDictionary.getOres(pName).isEmpty()) {
			return false;
		}
		return true;
	}

	private static String getOreNameWithFallback(String pName, String pFallback) {
		if (knowsOre(pName)) {
			return pName;
		}
		SolarFluxMod.log.info("Unable to find %s, falling back on %s", pName, pFallback);
		return pFallback;
	}
}
