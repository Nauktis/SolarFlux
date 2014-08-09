package com.nauktis.solarflux.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaseBlock extends Block {
	public BaseBlock() {
		this(Material.rock);
	}

	public BaseBlock(Material pMaterial) {
		super(pMaterial);
		setCreativeTab(ModCreativeTab.MOD_TAB);
	}

	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(), unwrapUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister pIconRegister) {
		blockIcon = pIconRegister.registerIcon(unwrapUnlocalizedName(this.getUnlocalizedName()));
	}

	protected String unwrapUnlocalizedName(String pUnlocalizedName) {
		return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
	}
}
