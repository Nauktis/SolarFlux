package com.nauktis.solarflux.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CraftingItem extends SFItem {
    public CraftingItem(String pName) {
        super(pName);
    }

    @Override
    public void addInformation(ItemStack pItemStack, EntityPlayer pEntityPlayer, List pList, boolean pBoolean) {
        super.addInformation(pItemStack, pEntityPlayer, pList, pBoolean);
        pList.add("Crafting component");
    }
}
