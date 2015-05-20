package com.nauktis.core.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import java.util.Collections;
import java.util.List;

public abstract class BaseModGui extends GuiContainer {
    private List<String> mMouseHover = Lists.newArrayList();

    public BaseModGui(Container pContainer) {
        super(pContainer);
    }

    @Override
    public void drawScreen(int pMouseX, int pMouseY, float pOpacity) {
        clearMouseHoverCache();
        super.drawScreen(pMouseX, pMouseY, pOpacity);
        drawHoveringText(mMouseHover, pMouseX, pMouseY, fontRendererObj);
    }

    /**
     * Sets some text to be drawn later in a tooltip.
     */
    public void drawMouseOver(String pText) {
        if (pText != null) {
            clearMouseHoverCache();
            Collections.addAll(mMouseHover, pText.split("\n"));
        }
    }

    private void clearMouseHoverCache() {
        mMouseHover.clear();
    }

    public boolean inBounds(int pLeft, int pTop, int pWidth, int pHeight, int pMouseX, int pMouseY) {
        return (pLeft <= pMouseX) && (pMouseX < pLeft + pWidth) && (pTop <= pMouseY) && (pMouseY < pTop + pHeight);
    }
}
