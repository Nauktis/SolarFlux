package com.nauktis.solarflux.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.nauktis.solarflux.reference.Reference;

public class ModCreativeTab {
	public static final CreativeTabs MOD_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
		@Override
		public Item getTabIconItem() {
			return Items.baked_potato;
		}
	};

}
