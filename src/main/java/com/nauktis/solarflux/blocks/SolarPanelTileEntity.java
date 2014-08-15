package com.nauktis.solarflux.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class SolarPanelTileEntity extends TileEntity implements IEnergyHandler {
	private EnergyStorage mEnergyStorage;
	private int mEnergyGeneration;

	public SolarPanelTileEntity(int pEnergyGeneration, int pCapacity) {
		mEnergyGeneration = pEnergyGeneration;
		mEnergyStorage = new EnergyStorage(pCapacity);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (shouldGenerateEnergy()) {
			generateEnergy();
		}
		if (shouldTransferEnergy()) {
			transferEnergy();
		}
	}

	protected boolean shouldGenerateEnergy() {
		return worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord);
	}

	protected void generateEnergy() {
		if (worldObj.isDaytime()) {
			// TODO Quantity proportional to sun level. 0 at night. Sinus function
			// TODO use worldObj.skylightSubtracted
			mEnergyStorage.receiveEnergy(4, false);
		}
	}

	protected boolean shouldTransferEnergy() {
		return mEnergyStorage.getEnergyStored() > 0;
	}

	protected void transferEnergy() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity tile = getWorldObj().getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if (!(tile instanceof SolarPanelTileEntity)) {
				if (tile instanceof IEnergyHandler) {
					IEnergyHandler receiver = (IEnergyHandler) tile;
					int energyToTransfer = mEnergyStorage.extractEnergy(mEnergyStorage.getMaxExtract(), true);
					mEnergyStorage.extractEnergy(receiver.receiveEnergy(direction.getOpposite(), energyToTransfer, false), false);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound pNBT) {
		super.readFromNBT(pNBT);
		mEnergyStorage.readFromNBT(pNBT);
	}

	@Override
	public void writeToNBT(NBTTagCompound pNBT) {
		mEnergyStorage.writeToNBT(pNBT);
		super.writeToNBT(pNBT);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection pFrom) {
		if (pFrom != ForgeDirection.UP) {
			return true;
		}
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection pFrom, int pMaxReceive, boolean pSimulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection pFrom, int pMaxExtract, boolean pSimulate) {
		return mEnergyStorage.extractEnergy(mEnergyStorage.getMaxExtract(), pSimulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection pFrom) {
		return mEnergyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection pFrom) {
		return mEnergyStorage.getMaxEnergyStored();
	}

}
