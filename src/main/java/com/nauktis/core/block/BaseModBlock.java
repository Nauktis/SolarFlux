package com.nauktis.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.google.common.base.Preconditions;

public class BaseModBlock extends Block {
	private final String mModId;

	public BaseModBlock(String pModId) {
		super(Material.rock);
		mModId = Preconditions.checkNotNull(pModId).toLowerCase();
	}

	public BaseModBlock(String pModId, String pName) {
		this(pModId);
		setBlockName(Preconditions.checkNotNull(pName));
	}

	public String getModId() {
		return mModId;
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s:%s", getModId(), unwrapUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	protected String getTextureName() {
		return unwrapUnlocalizedName(getUnlocalizedName());
	}

	protected String unwrapUnlocalizedName(String pUnlocalizedName) {
		return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
	}
}
