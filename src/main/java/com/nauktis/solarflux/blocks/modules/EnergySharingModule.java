package com.nauktis.solarflux.blocks.modules;

import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import net.minecraft.tileentity.TileEntity;

public class EnergySharingModule extends IntervalTileEntityModule<SolarPanelTileEntity> {
    private static final short DISTRIBUTION_TICK_RATE = 5 * 20;

    public EnergySharingModule(SolarPanelTileEntity pTileEntity) {
        super(pTileEntity, DISTRIBUTION_TICK_RATE);
    }

    @Override
    protected void doTick() {
        SolarPanelTileEntity me = getTileEntity();
        tryAutoBalanceEnergyAt(me.x() + 1, me.y(), me.z());
        tryAutoBalanceEnergyAt(me.x(), me.y(), me.z() + 1);
    }

    private void tryAutoBalanceEnergyAt(int pX, int pY, int pZ) {
        TileEntity tile = getTileEntity().getWorldObj().getTileEntity(pX, pY, pZ);
        if (tile instanceof SolarPanelTileEntity) {
            SolarPanelTileEntity neighbor = (SolarPanelTileEntity) tile;
            getTileEntity().getEnergyStorage().autoBalanceEnergy(neighbor.getEnergyStorage(), DISTRIBUTION_TICK_RATE);
        }
    }
}
