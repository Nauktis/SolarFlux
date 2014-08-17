package com.nauktis.solarflux.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.reference.Reference;

public class SolarPanelGui extends GuiScreen {
	private static final ResourceLocation sBackground = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/guiBase.png");
	private SolarPanelTileEntity mSolarPanelTileEntity;

	public SolarPanelGui(SolarPanelTileEntity pSolarPanelTileEntity) {
		SolarFluxMod.log.info("Creation of client GUI SolarPanelGui");
		mSolarPanelTileEntity = pSolarPanelTileEntity;
	}

	@Override
	public void drawScreen(int pP_73863_1_, int pP_73863_2_, float pP_73863_3_) {
		drawDefaultBackground();
		super.drawScreen(pP_73863_1_, pP_73863_2_, pP_73863_3_);
		drawCenteredString(fontRendererObj, "[" + mSolarPanelTileEntity.getPercentageEnergyStored() + "%]", this.width / 2, 20, 16777215);
		String capacity = "Energy: " + String.format("%,d / %,d", mSolarPanelTileEntity.getEnergyStored(), mSolarPanelTileEntity.getMaxEnergyStored());
		drawCenteredString(fontRendererObj, capacity, this.width / 2, 40, 16777215);

		drawCenteredString(fontRendererObj, "Production: " + mSolarPanelTileEntity.getEnergyProduced(), this.width / 2, 60, 16777215);
	}
}
