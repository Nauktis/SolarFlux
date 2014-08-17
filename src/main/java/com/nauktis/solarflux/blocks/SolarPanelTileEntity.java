package com.nauktis.solarflux.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.google.common.base.Objects;
import com.nauktis.solarflux.SolarFluxMod;

public class SolarPanelTileEntity extends TileEntity implements IEnergyHandler {
	private StatefulEnergyStorage mEnergyStorage;
	private int mEnergyGeneration;

	public SolarPanelTileEntity() {
		// This empty constructor is used when loading a TileEntity from NBT.
		this(0, 0);
	}

	public SolarPanelTileEntity(int pEnergyGeneration, int pCapacity) {
		SolarFluxMod.log.info("SolarPanelTileEntity(%d, %d)", pEnergyGeneration, pCapacity);
		mEnergyGeneration = pEnergyGeneration;
		mEnergyStorage = new StatefulEnergyStorage(pCapacity, 10000);
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
		return worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord);
	}

	protected void generateEnergy() {
		if (worldObj.isDaytime()) {
			// TODO Quantity proportional to sun level. 0 at night. Sinus
			// function
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
		SolarFluxMod.log.info("Reading NBT %s", this);

		super.readFromNBT(pNBT);
		mEnergyGeneration = pNBT.getInteger("Production");
		mEnergyStorage.readFromNBT(pNBT);

		SolarFluxMod.log.info("Loaded: %s", mEnergyStorage);
	}

	@Override
	public void writeToNBT(NBTTagCompound pNBT) {
		SolarFluxMod.log.info("Writing to NBT: %s", this);

		super.writeToNBT(pNBT);
		pNBT.setInteger("Production", mEnergyGeneration);
		mEnergyStorage.writeToNBT(pNBT);
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

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("hash", hashCode()).add("MaxProduction", mEnergyGeneration).add("energyStorage", mEnergyStorage).toString();
	}
}
