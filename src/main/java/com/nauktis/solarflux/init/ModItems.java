package com.nauktis.solarflux.init;

import net.minecraft.item.Item;

import com.nauktis.solarflux.items.SFItem;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	public static final Item mMirror = new SFItem("mirror");
	public static final Item mSolarCore1 = new SFItem("solarCore1");
	public static final Item mSolarCore2 = new SFItem("solarCore2");
	public static final Item mSolarCore3 = new SFItem("solarCore3");
	public static final Item mSolarCore4 = new SFItem("solarCore4");
	public static final Item mSolarCore5 = new SFItem("solarCore5");

	private ModItems() {}

	public static void initialize() {
		GameRegistry.registerItem(mMirror, "mirror");
	}
}
