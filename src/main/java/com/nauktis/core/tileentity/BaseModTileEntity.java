package com.nauktis.core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BaseModTileEntity extends TileEntity {

	public int x() {
		return xCoord;
	}

	public int y() {
		return yCoord;
	}

	public int z() {
		return zCoord;
	}

	@Override
	public void readFromNBT(NBTTagCompound pNBT) {
		super.readFromNBT(pNBT);
		loadDataFromNBT(pNBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound pNBT) {
		super.writeToNBT(pNBT);
		addDataToNBT(pNBT);
	}

	/**
	 * Override to save {@link TileEntity} specific data to the NBT tag
	 */
	protected void addDataToNBT(NBTTagCompound pNBT) {

	}

	/**
	 * Override to load {@link TileEntity} specific data from the NBT tag
	 */
	protected void loadDataFromNBT(NBTTagCompound pNBT) {

	}
}
