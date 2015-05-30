package com.nauktis.solarflux.items;

import com.google.common.collect.Lists;
import com.nauktis.core.utility.Color;
import com.nauktis.core.utility.Utils;
import com.nauktis.solarflux.utility.Lang;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class UpgradeItem extends SFItem {
    private final int mMaximumPerSolarPanel;
    private final List<String> mUpgradeInfos = Lists.newArrayList();

    public UpgradeItem(String pName, int pMaximumPerSolarPanel, List<String> pUpgradeInfos) {
        super(pName);
        mMaximumPerSolarPanel = pMaximumPerSolarPanel;
        mUpgradeInfos.addAll(pUpgradeInfos);
    }

    @Override
    public void addInformation(ItemStack pItemStack, EntityPlayer pEntityPlayer, List pList, boolean pBoolean) {
        super.addInformation(pItemStack, pEntityPlayer, pList, pBoolean);
        if (Utils.isShiftKeyDown()) {
            pList.add(Color.AQUA + Lang.localise("solar.panel.upgrade") + Color.GREY);
            pList.addAll(mUpgradeInfos);
            pList.add(Lang.localise("maximum") + " " + getMaximumPerSolarPanel());
        } else {
            pList.add(String.format(Lang.localise("hold.for.info"), Color.YELLOW + Lang.localise("shift") + Color.GREY));
        }
    }

    /**
     * The maximum number of this upgrade that stacked in one solar panel.
     */
    public int getMaximumPerSolarPanel() {
        return mMaximumPerSolarPanel;
    }
}
