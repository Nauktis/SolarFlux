package com.nauktis.core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.nauktis.solarflux.SolarFluxMod;

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

	/**
	 * Prepare a Packet to send the state of the {@link TileEntity} to the Client
	 */
	@Override
	public Packet getDescriptionPacket() {
		SolarFluxMod.log.info("BaseModTileEntity.getDescriptionPacket");
		NBTTagCompound nbt = new NBTTagCompound();
		addDataToNBT(nbt);
		return new S35PacketUpdateTileEntity(x(), y(), z(), 1, nbt);
	}

	/**
	 * Process a Packet to update the state of the {@link TileEntity} on the Client
	 */
	@Override
	public void onDataPacket(NetworkManager pNet, S35PacketUpdateTileEntity pPacket) {
		SolarFluxMod.log.info("BaseModTileEntity.onDataPacket");
		super.onDataPacket(pNet, pPacket);
		NBTTagCompound nbt = pPacket.func_148857_g();
		loadDataFromNBT(nbt);
	}
}
