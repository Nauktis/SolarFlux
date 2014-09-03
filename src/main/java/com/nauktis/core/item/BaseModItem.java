package com.nauktis.core.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Preconditions;

public class BaseModItem extends Item {
	private final String mModId;

	public BaseModItem(String pModId, String pName) {
		mModId = Preconditions.checkNotNull(pModId).toLowerCase();
		setUnlocalizedName(Preconditions.checkNotNull(pName));
	}

	public String getModId() {
		return mModId;
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("item.%s:%s", getModId(), unwrapUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public String getUnlocalizedName(ItemStack pItemStack) {
		return getUnlocalizedName();
	}

	@Override
	protected String getIconString() {
		return unwrapUnlocalizedName(getUnlocalizedName());
	}

	protected String unwrapUnlocalizedName(String pUnlocalizedName) {
		return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
	}
}
