package com.nauktis.solarflux.gui;

import com.nauktis.core.gui.BaseModGui;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.reference.Reference;
import com.nauktis.solarflux.utility.Lang;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSolarPanel extends BaseModGui {
    private static final ResourceLocation ELEMENTS = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/elements.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/solar.png");
    private static final int GAUGE_WIDTH = 18;
    private static final int GAUGE_HEIGHT = 50;
    private static final int GAUGE_INNER_WIDTH = GAUGE_WIDTH - 2;
    private static final int GAUGE_INNER_HEIGHT = GAUGE_HEIGHT - 2;
    private static final int GAUGE_INNER_OFFSET_X = (GAUGE_WIDTH - GAUGE_INNER_WIDTH) / 2;
    private static final int GAUGE_INNER_OFFSET_Y = (GAUGE_HEIGHT - GAUGE_INNER_HEIGHT) / 2;
    private static final int GAUGE_SRC_X = 64;
    private static final int GAUGE_SRC_Y = 62;
    private static final int GAUGE_INNER_SRC_X = 0;
    private static final int GAUGE_INNER_SRC_Y = 64;
    // How far from the border of the GUI we start to draw.
    private static final int BORDER_OFFSET = 8;

    private final SolarPanelTileEntity mSolarPanelTileEntity;
    private final InventoryPlayer mInventoryPlayer;

    public GuiSolarPanel(InventoryPlayer pInventoryPlayer, SolarPanelTileEntity pSolarPanelTileEntity) {
        super(new ContainerSolarPanel(pInventoryPlayer, pSolarPanelTileEntity));
        mSolarPanelTileEntity = pSolarPanelTileEntity;
        mInventoryPlayer = pInventoryPlayer;
        xSize = 176;
        ySize = 180;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int pMouseX, int pMouseY) {
        fontRendererObj.drawString(
                mInventoryPlayer.hasCustomInventoryName() ? mInventoryPlayer.getInventoryName() : I18n.format(mInventoryPlayer.getInventoryName()),
                BORDER_OFFSET,
                ySize - 96 + 2,
                0x404040);

        fontRendererObj.drawString(
                String.format("%s: %,d %s", Lang.localise("energy.stored"), mSolarPanelTileEntity.getEnergyStored(), Lang.localise("rf")),
                BORDER_OFFSET,
                BORDER_OFFSET,
                0x404040);

        fontRendererObj.drawString(
                String.format("%s: %,d %s", Lang.localise("energy.capacity"), mSolarPanelTileEntity.getMaxEnergyStored(), Lang.localise("rf")),
                BORDER_OFFSET,
                BORDER_OFFSET + 10,
                0x404040);

        fontRendererObj.drawString(
                String.format("%s: %,d %s", Lang.localise("energy.generation"), mSolarPanelTileEntity.getCurrentEnergyGeneration(), Lang.localise("rfPerTick")),
                BORDER_OFFSET,
                BORDER_OFFSET + 20,
                0x404040);

        fontRendererObj.drawString(
                String.format(
                        "%s: %,d%%",
                        Lang.localise("energy.efficiency"),
                        Math.round(100D * mSolarPanelTileEntity.getCurrentEnergyGeneration() / mSolarPanelTileEntity.getMaximumEnergyGeneration())),
                BORDER_OFFSET,
                BORDER_OFFSET + 30,
                0x404040);
        super.drawGuiContainerForegroundLayer(pMouseX, pMouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float pOpacity, int pMouseX, int pMouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        // Prepare
        this.mc.getTextureManager().bindTexture(ELEMENTS);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(2896);

        // Draw slots.
        for (int i = 0; i < SolarPanelTileEntity.INVENTORY_SIZE; ++i) {
            drawTexturedModalRect(xStart + 17 + i * 18 - 1, yStart + 59 - 1, 0, 0, 18, 18);
        }

        drawPower(xStart + xSize - GAUGE_WIDTH - BORDER_OFFSET, yStart + BORDER_OFFSET, pMouseX, pMouseY);
        drawSun(xStart + xSize - 2 * GAUGE_WIDTH - BORDER_OFFSET - BORDER_OFFSET / 2, yStart + BORDER_OFFSET, pMouseX, pMouseY);
    }

    /**
     * Heavily inspired by Vswe.
     */
    private void drawPower(int pLeft, int pTop, int pMouseX, int pMouseY) {
        // Background color.
        drawTexturedModalRect(
                pLeft + GAUGE_INNER_OFFSET_X,
                pTop + GAUGE_INNER_OFFSET_Y,
                GAUGE_INNER_SRC_X + GAUGE_INNER_WIDTH,
                GAUGE_INNER_SRC_Y,
                GAUGE_INNER_WIDTH,
                GAUGE_INNER_HEIGHT);

        int height = mSolarPanelTileEntity.getScaledEnergyStoredFraction(GAUGE_INNER_HEIGHT);
        int offset = GAUGE_INNER_HEIGHT - height;
        // Foreground color (level).
        drawTexturedModalRect(
                pLeft + GAUGE_INNER_OFFSET_X,
                pTop + GAUGE_INNER_OFFSET_Y + offset,
                GAUGE_INNER_SRC_X,
                GAUGE_INNER_SRC_Y + offset,
                GAUGE_INNER_WIDTH,
                height);
        // Little bar.
        drawTexturedModalRect(pLeft, pTop + GAUGE_INNER_OFFSET_Y + offset - 1, GAUGE_SRC_X, GAUGE_SRC_Y - 1, GAUGE_WIDTH, 1);

        int srcX = GAUGE_SRC_X;
        boolean hover = inBounds(pLeft, pTop, GAUGE_WIDTH, GAUGE_HEIGHT, pMouseX, pMouseY);
        if (hover) {
            // Highlighted gauge is just on the right.
            srcX += GAUGE_WIDTH;
        }
        // Gauge
        drawTexturedModalRect(pLeft, pTop, srcX, GAUGE_SRC_Y, GAUGE_WIDTH, GAUGE_HEIGHT);

        if (hover) {
            String str = String.format(
                    "%s: %,d/%,d",
                    Lang.localise("energy.stored"),
                    mSolarPanelTileEntity.getEnergyStored(),
                    mSolarPanelTileEntity.getMaxEnergyStored());
            drawMouseOver(str);
        }
    }

    /**
     * Heavily inspired by Vswe.
     */
    private void drawSun(int pLeft, int pTop, int pMouseX, int pMouseY) {
        // TODO Refactor to remove this ugly copy/paste.
        // Background color.
        drawTexturedModalRect(
                pLeft + GAUGE_INNER_OFFSET_X,
                pTop + GAUGE_INNER_OFFSET_Y,
                32 + GAUGE_INNER_SRC_X + GAUGE_INNER_WIDTH,
                GAUGE_INNER_SRC_Y,
                GAUGE_INNER_WIDTH,
                GAUGE_INNER_HEIGHT);

        int height = (int) (GAUGE_INNER_HEIGHT * mSolarPanelTileEntity.getSunIntensity());
        int offset = GAUGE_INNER_HEIGHT - height;
        // Foreground color (level).
        drawTexturedModalRect(
                pLeft + GAUGE_INNER_OFFSET_X,
                pTop + GAUGE_INNER_OFFSET_Y + offset,
                32 + GAUGE_INNER_SRC_X,
                GAUGE_INNER_SRC_Y + offset,
                GAUGE_INNER_WIDTH,
                height);
        // Little bar.
        drawTexturedModalRect(pLeft, pTop + GAUGE_INNER_OFFSET_Y + offset - 1, GAUGE_SRC_X + GAUGE_WIDTH, GAUGE_SRC_Y - 1, GAUGE_WIDTH, 1);

        int srcX = GAUGE_SRC_X;
        boolean hover = inBounds(pLeft, pTop, GAUGE_WIDTH, GAUGE_HEIGHT, pMouseX, pMouseY);
        if (hover) {
            // Highlighted gauge is just on the right.
            srcX += GAUGE_WIDTH;
        }
        // Gauge
        drawTexturedModalRect(pLeft, pTop, srcX, GAUGE_SRC_Y, GAUGE_WIDTH, GAUGE_HEIGHT);

        if (hover) {
            String str = String.format(
                    "%s: %d%%",
                    Lang.localise("sun.intensity"),
                    (int) (100 * mSolarPanelTileEntity.getSunIntensity()));
            drawMouseOver(str);
        }
    }
}
