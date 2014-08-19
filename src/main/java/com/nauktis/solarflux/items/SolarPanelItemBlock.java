package com.nauktis.solarflux.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.nauktis.solarflux.blocks.SolarPanelBlock;

public class SolarPanelItemBlock extends ItemBlock {
	public SolarPanelItemBlock(Block pBlock) {
		super(pBlock);
	}

	@Override
	public void addInformation(ItemStack pItemStack, EntityPlayer pPlayer, List pList, boolean pBoolean) {
		super.addInformation(pItemStack, pPlayer, pList, pBoolean);
		SolarPanelBlock solar = (SolarPanelBlock) field_150939_a;
		pList.add(String.format("Generation: %,d", solar.getMaximumEnergyGeneration()));
		pList.add(String.format("Transfer: %,d", solar.getMaximumEnergyTransfer()));
		pList.add(String.format("Capacity: %,d", solar.getEnergyCapacity()));
	}
}
