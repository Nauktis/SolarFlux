package com.nauktis.solarflux.blocks;

import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.EnergyStorage;

import com.google.common.base.Objects;

public class StatefulEnergyStorage extends EnergyStorage {
	private static final String NBT_CAPACITY = "SFESCapacity";
	private static final String NBT_MAX_TRANSFER = "SFESMaxTransfer";

	public StatefulEnergyStorage(int pCapacity) {
		super(pCapacity);
	}

	public StatefulEnergyStorage(int pCapacity, int pMaxTransfer) {
		super(pCapacity, pMaxTransfer);
	}

	@Override
	public EnergyStorage readFromNBT(NBTTagCompound pNbt) {
		setCapacity(pNbt.getInteger(NBT_CAPACITY));
		setMaxTransfer(pNbt.getInteger(NBT_MAX_TRANSFER));
		return super.readFromNBT(pNbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound pNbt) {
		pNbt.setInteger(NBT_CAPACITY, getMaxEnergyStored());
		pNbt.setInteger(NBT_MAX_TRANSFER, getMaxExtract());
		super.writeToNBT(pNbt);
		return pNbt;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("energy", getEnergyStored()).add("capacity", getMaxEnergyStored()).add("maxTransfer", getMaxExtract()).toString();
	}
}
