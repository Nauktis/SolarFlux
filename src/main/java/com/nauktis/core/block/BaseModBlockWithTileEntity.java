package com.nauktis.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BaseModBlockWithTileEntity extends BaseModBlock implements ITileEntityProvider {
	public BaseModBlockWithTileEntity(String pModId) {
		super(pModId);
		this.isBlockContainer = true;
	}

	public BaseModBlockWithTileEntity(String pModId, String pName) {
		super(pModId, pName);
	}

	@Override
	public boolean onBlockEventReceived(World pWorld, int pX, int pY, int pZ, int pEventNumber, int pEventArgument) {
		super.onBlockEventReceived(pWorld, pX, pY, pZ, pEventNumber, pEventArgument);
		TileEntity tileentity = pWorld.getTileEntity(pX, pY, pZ);
		if (tileentity != null) {
			return tileentity.receiveClientEvent(pEventNumber, pEventArgument);
		}
		return false;
	}
}
