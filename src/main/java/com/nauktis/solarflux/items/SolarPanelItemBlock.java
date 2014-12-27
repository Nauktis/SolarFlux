package com.nauktis.solarflux.items;

import com.nauktis.core.utility.Color;
import com.nauktis.solarflux.blocks.SolarPanelBlock;
import com.nauktis.solarflux.blocks.StatefulEnergyStorage;
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

        if (pItemStack.getTagCompound() != null) {
            pList.add(String.format("%s%s:%s %,d",
                    Color.GREEN,
                    Lang.localise("energy.stored"),
                    Color.GREY,
                    pItemStack.getTagCompound().getInteger(StatefulEnergyStorage.NBT_ENERGY)));
        }
        pList.add(String.format("%s%s:%s %,d", Color.AQUA, Lang.localise("energy.capacity"), Color.GREY, solar.getEnergyCapacity()));
        pList.add(String.format("%s%s:%s %,d",
                Color.AQUA,
                Lang.localise("energy.generation"),
                Color.GREY,
                solar.getMaximumEnergyGeneration()));
        pList.add(String.format("%s%s:%s %,d", Color.AQUA, Lang.localise("energy.transfer"), Color.GREY, solar.getMaximumEnergyTransfer()));
    }
}
