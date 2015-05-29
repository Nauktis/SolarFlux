package com.nauktis.solarflux.blocks.modules;

import cofh.api.energy.IEnergyReceiver;
import com.google.common.collect.Lists;
import com.nauktis.core.utility.BlockPosition;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.init.ModItems;
import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Module used to distribute energy to neighbor blocks.
 */
public class SimpleEnergyDispenserModule extends AbstractSolarPanelModule {
    private final List<BlockPosition> mTargets = Lists.newArrayList();
    private int mTargetStartingIndex;
    private int mFurnaceEnergyBuffer;

    public SimpleEnergyDispenserModule(SolarPanelTileEntity pSolarPanelTileEntity) {
        super(pSolarPanelTileEntity);
    }

    @Override
    public void tick() {
        sendEnergyToTargets();
        if (atRate(getTargetRefreshRate())) {
            searchTargets();
        }
    }

    protected int getTargetRefreshRate() {
        //TODO Should this be a config option?
        return 2 * 20;
    }

    protected void searchTargets() {
        mTargets.clear();
        BlockPosition position = new BlockPosition(getTileEntity().x(), getTileEntity().y(), getTileEntity().z());
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            BlockPosition neighbor = position.move(direction);
            if (isValidTarget(neighbor)) {
                mTargets.add(neighbor);
            }
        }
    }

    protected List<BlockPosition> getTargets() {
        return mTargets;
    }

    protected void sendEnergyToTargets() {
        if (mTargets.size() > 0 && getTileEntity().getEnergyStored() > 0) {
            for (int i = 0; i < mTargets.size(); ++i) {
                sendEnergyTo(mTargets.get((mTargetStartingIndex + i) % mTargets.size()));
            }
            mTargetStartingIndex = (mTargetStartingIndex + 1) % mTargets.size();
        }
    }

    protected boolean isValidTarget(BlockPosition pPosition) {
        TileEntity tile = getTileEntity().getWorldObj().getTileEntity(pPosition.x(), pPosition.y(), pPosition.z());
        return tile instanceof IEnergyReceiver || (getTileEntity().getUpgradeCount(ModItems.mUpgradeFurnace) > 0 && tile instanceof TileEntityFurnace);
    }

    protected void sendEnergyTo(BlockPosition pBlockPosition) {
        TileEntity tile = getTileEntity().getWorldObj().getTileEntity(pBlockPosition.x(), pBlockPosition.y(), pBlockPosition.z());
        if (tile instanceof IEnergyReceiver) {
            sendEnergyToReceiver((IEnergyReceiver) tile, pBlockPosition.getDirection().getOpposite());
        } else if (getTileEntity().getUpgradeCount(ModItems.mUpgradeFurnace) > 0 && tile instanceof TileEntityFurnace) {
            sendEnergyToFurnace((TileEntityFurnace) tile);
        }
    }

    protected void sendEnergyToReceiver(IEnergyReceiver pEnergyReceiver, ForgeDirection pFrom) {
        getTileEntity().getEnergyStorage().sendMaxTo(pEnergyReceiver, pFrom);
    }

    protected void sendEnergyToFurnace(TileEntityFurnace pFurnace) {
        final int FURNACE_COOKING_TICKS = 200;
        final int FURNACE_COOKING_ENERGY = FURNACE_COOKING_TICKS * ModConfiguration.getFurnaceUpgradeHeatingConsumption();

        if (mFurnaceEnergyBuffer < FURNACE_COOKING_ENERGY) {
            mFurnaceEnergyBuffer += getTileEntity().getEnergyStorage().extractEnergy(FURNACE_COOKING_ENERGY - mFurnaceEnergyBuffer, false);
        }

        // Is there anything to smell?
        if (pFurnace.getStackInSlot(0) != null && pFurnace.furnaceBurnTime < FURNACE_COOKING_TICKS) {
            int burnTicksAvailable = mFurnaceEnergyBuffer / ModConfiguration.getFurnaceUpgradeHeatingConsumption();
            if (burnTicksAvailable >= FURNACE_COOKING_TICKS) {
                if (pFurnace.furnaceBurnTime == 0) {
                    // Add 1 as first tick is not counted in the burning process.
                    pFurnace.furnaceBurnTime += 1;
                    BlockFurnace.updateFurnaceBlockState(pFurnace.furnaceBurnTime > 0, pFurnace.getWorldObj(), pFurnace.xCoord, pFurnace.yCoord, pFurnace.zCoord);
                }
                pFurnace.furnaceBurnTime += FURNACE_COOKING_TICKS;
                mFurnaceEnergyBuffer -= FURNACE_COOKING_TICKS * ModConfiguration.getFurnaceUpgradeHeatingConsumption();
            }
        }
    }
}
