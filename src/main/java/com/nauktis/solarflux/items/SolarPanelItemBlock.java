package com.nauktis.solarflux.items;

import com.nauktis.core.utility.Color;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.reference.NBTConstants;
import com.nauktis.solarflux.utility.Lang;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SolarPanelItemBlock extends ItemBlock {
    public SolarPanelItemBlock(Block pBlock) {
        super(pBlock);
    }

    @Override
    public void addInformation(ItemStack pItemStack, EntityPlayer pPlayer, List pList, boolean pBoolean) {
        super.addInformation(pItemStack, pPlayer, pList, pBoolean);
        SolarPanelBlock solar = (SolarPanelBlock) field_150939_a;
        addChargeTooltip(pList, pItemStack);
        addUpgradeCount(pList, pItemStack);
        addCapacityTooltip(pList, pItemStack, solar);
        addGenerationTooltip(pList, pItemStack, solar);
        addTransferTooltip(pList, pItemStack, solar);
    }

    private void addChargeTooltip(List pList, ItemStack pItemStack) {
        if (hasNbtTag(pItemStack, NBTConstants.ENERGY)) {
            pList.add(String.format("%s%s:%s %,d", Color.GREEN, Lang.localise("energy.stored"), Color.GREY, pItemStack.getTagCompound().getInteger(NBTConstants.ENERGY)));
        }
    }

    private void addUpgradeCount(List pList, ItemStack pItemStack) {
        if (hasNbtTag(pItemStack, NBTConstants.TOOLTIP_UPGRADE_COUNT)) {
            pList.add(
                    String.format(
                            "%s%s:%s %,d",
                            Color.GREEN,
                            Lang.localise("upgrades.installed"),
                            Color.GREY,
                            pItemStack.getTagCompound().getInteger(NBTConstants.TOOLTIP_UPGRADE_COUNT)));
        }
    }

    private void addCapacityTooltip(List pList, ItemStack pItemStack, SolarPanelBlock pSolar) {
        int value = ModConfiguration.getTierConfiguration(pSolar.getTierIndex()).getCapacity();
        if (hasNbtTag(pItemStack, NBTConstants.TOOLTIP_CAPACITY)) {
            int itemValue = pItemStack.getTagCompound().getInteger(NBTConstants.TOOLTIP_CAPACITY);
            if (itemValue != value) {
                value = itemValue;
            }
        }
        pList.add(String.format("%s%s:%s %,d", Color.AQUA, Lang.localise("energy.capacity"), Color.GREY, value));
    }

    private void addGenerationTooltip(List pList, ItemStack pItemStack, SolarPanelBlock pSolar) {
        final int value = ModConfiguration.getTierConfiguration(pSolar.getTierIndex()).getMaximumEnergyGeneration();
        pList.add(String.format("%s%s:%s %,d", Color.AQUA, Lang.localise("energy.generation"), Color.GREY, value));
    }

    private void addTransferTooltip(List pList, ItemStack pItemStack, SolarPanelBlock pSolar) {
        int value = ModConfiguration.getTierConfiguration(pSolar.getTierIndex()).getMaximumEnergyTransfer();
        if (hasNbtTag(pItemStack, NBTConstants.TOOLTIP_TRANSFER_RATE)) {
            int itemValue = pItemStack.getTagCompound().getInteger(NBTConstants.TOOLTIP_TRANSFER_RATE);
            if (itemValue != value) {
                value = itemValue;
            }
        }
        pList.add(String.format("%s%s:%s %,d", Color.AQUA, Lang.localise("energy.transfer"), Color.GREY, value));
    }

    private boolean hasNbtTag(ItemStack pItemStack, String pNbtTag) {
        return pItemStack.hasTagCompound() && pItemStack.getTagCompound().hasKey(pNbtTag);
    }
}
