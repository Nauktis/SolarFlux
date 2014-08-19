package com.nauktis.solarflux.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.nauktis.solarflux.init.ModItems;
import com.nauktis.solarflux.reference.Reference;

public class ModCreativeTab {
	public static final CreativeTabs MOD_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
		@Override
		public Item getTabIconItem() {
			return ModItems.mSolarCell3;
		}
	};
}
