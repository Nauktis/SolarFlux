package com.nauktis.solarflux.init;

import net.minecraft.item.Item;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.items.SFItem;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	public static final Item mMirror = new SFItem("mirror");
	public static final Item mSolarCore1 = new SFItem("solarCore1");
	public static final Item mSolarCore2 = new SFItem("solarCore2");
	public static final Item mSolarCore3 = new SFItem("solarCore3");
	public static final Item mSolarCore4 = new SFItem("solarCore4");
	public static final Item mSolarCore5 = new SFItem("solarCore5");
	public static final Item mSolarCell1 = new SFItem("solarCell1");
	public static final Item mSolarCell2 = new SFItem("solarCell2");
	public static final Item mSolarCell3 = new SFItem("solarCell3");
	public static final Item mSolarCell4 = new SFItem("solarCell4");

	private ModItems() {}

	public static void initialize() {
		SolarFluxMod.log.info("Registering items");
		GameRegistry.registerItem(mMirror, "mirror");
		GameRegistry.registerItem(mSolarCore1, "solarCore1");
		GameRegistry.registerItem(mSolarCore2, "solarCore2");
		GameRegistry.registerItem(mSolarCore3, "solarCore3");
		GameRegistry.registerItem(mSolarCore4, "solarCore4");
		GameRegistry.registerItem(mSolarCore5, "solarCore5");
		GameRegistry.registerItem(mSolarCell1, "solarCell1");
		GameRegistry.registerItem(mSolarCell2, "solarCell2");
		GameRegistry.registerItem(mSolarCell3, "solarCell3");
		GameRegistry.registerItem(mSolarCell4, "solarCell4");
	}
}
