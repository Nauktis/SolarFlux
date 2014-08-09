package com.nauktis.solarflux.init;

import net.minecraft.block.Block;

import com.nauktis.solarflux.core.block.BaseBlock;
import com.nauktis.solarflux.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	public static final Block mSolar = new BaseBlock().setBlockName("solar");

	private ModBlocks() {}

	public static void initialize() {
		GameRegistry.registerBlock(mSolar, "solar");
	}
}
