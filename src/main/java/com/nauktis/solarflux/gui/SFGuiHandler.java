package com.nauktis.solarflux.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.nauktis.solarflux.blocks.SolarPanelTileEntity;

import cpw.mods.fml.common.network.IGuiHandler;

public class SFGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
		SolarPanelTileEntity tile = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
		return new SolarPanelGui(tile);
	}
}
