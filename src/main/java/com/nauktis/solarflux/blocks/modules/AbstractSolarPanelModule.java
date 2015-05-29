package com.nauktis.solarflux.blocks.modules;

import com.nauktis.solarflux.blocks.SolarPanelTileEntity;

public abstract class AbstractSolarPanelModule extends AbstractTileEntityModule<SolarPanelTileEntity> {
    protected AbstractSolarPanelModule(SolarPanelTileEntity pSolarPanelTileEntity) {
        super(pSolarPanelTileEntity);
    }

    protected boolean atRate(int pDesiredTickRate) {
        return getTileEntity().atTickRate(pDesiredTickRate);
    }
}
