package com.nauktis.solarflux.items;

import com.nauktis.core.item.BaseModItem;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;

public class SFItem extends BaseModItem {
	public SFItem(String pName) {
		super(Reference.MOD_ID, pName);
		setCreativeTab(ModCreativeTab.MOD_TAB);
	}
}
