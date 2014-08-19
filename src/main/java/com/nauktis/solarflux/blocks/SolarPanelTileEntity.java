package com.nauktis.solarflux.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

import com.google.common.base.Objects;
import com.nauktis.core.tileentity.BaseModTileEntity;
import com.nauktis.solarflux.SolarFluxMod;

public class SolarPanelTileEntity extends BaseModTileEntity implements IEnergyHandler {
	private StatefulEnergyStorage mEnergyStorage;
	private int mMaximumEnergyGeneration;

	public SolarPanelTileEntity() {
		// This empty constructor is used when loading a TileEntity from NBT.
		this(0, 0, 0);
	}

	public SolarPanelTileEntity(int pMaximumEnergyGeneration, int pMaximumEnergyTransfer, int pCapacity) {
		SolarFluxMod.log.info("SolarPanelTileEntity(%d, %d)", pMaximumEnergyGeneration, pCapacity);
		mMaximumEnergyGeneration = pMaximumEnergyGeneration;
		mEnergyStorage = new StatefulEnergyStorage(pCapacity, pMaximumEnergyTransfer);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		generateEnergy();
		if (shouldTransferEnergy()) {
			transferEnergy();
		}
	}

	public int getEnergyProduced() {
		if (worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
			float multiplicator = 1.5f;
			float displacement = 1.2f;
			float celestialAngleRadians = worldObj.getCelestialAngleRadians(1.0f);

			if (celestialAngleRadians > Math.PI) {
				celestialAngleRadians = (2 * 3.141592f - celestialAngleRadians);
			}

			int c = Math.min(mMaximumEnergyGeneration, Math.round(mMaximumEnergyGeneration * multiplicator * MathHelper.cos(celestialAngleRadians / displacement)));
			return c > 0 ? c : 0;
		}
		return 0;
	}

	protected void generateEnergy() {
		int produced = getEnergyProduced();
		if (produced > 0) {
			mEnergyStorage.receiveEnergy(produced, false);
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
	protected void loadDataFromNBT(NBTTagCompound pNBT) {
		SolarFluxMod.log.info("SolarPanelTileEntity.loadDataFromNBT");
		super.loadDataFromNBT(pNBT);
		mMaximumEnergyGeneration = pNBT.getInteger("Production");
		mEnergyStorage.readFromNBT(pNBT);
		// TODO remove debug statement
		SolarFluxMod.log.info("Loaded: %s", this);
	}

	@Override
	protected void addDataToNBT(NBTTagCompound pNBT) {
		SolarFluxMod.log.info("SolarPanelTileEntity.addDataToNBT");
		super.addDataToNBT(pNBT);
		pNBT.setInteger("Production", mMaximumEnergyGeneration);
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

	public int getEnergyStored() {
		return getEnergyStored(ForgeDirection.DOWN);
	}

	public int getPercentageEnergyStored() {
		return 100 * getEnergyStored() / getMaxEnergyStored();
	}

	@Override
	public int getEnergyStored(ForgeDirection pFrom) {
		return mEnergyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored() {
		return getMaxEnergyStored(ForgeDirection.DOWN);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection pFrom) {
		return mEnergyStorage.getMaxEnergyStored();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("hash", hashCode()).add("MaxProduction", mMaximumEnergyGeneration).add("energyStorage", mEnergyStorage).toString();
	}
}
