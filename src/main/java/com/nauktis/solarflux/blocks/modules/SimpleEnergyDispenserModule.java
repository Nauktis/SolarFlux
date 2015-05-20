package com.nauktis.solarflux.blocks.modules;

import cofh.api.energy.IEnergyReceiver;
import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Simple energy dispenser that sends energy to direct neighbors of the solar panel.
 */
public class SimpleEnergyDispenserModule extends AbstractTileEntityModule<SolarPanelTileEntity> {
    private int mDirectionStartinggIndex = 0;

    public SimpleEnergyDispenserModule(SolarPanelTileEntity pTileEntity) {
        super(pTileEntity);
    }

    @Override
    public void tick() {
        if (getTileEntity().getEnergyStored() > 0) {
            transferEnergy();
        }
    }

    private void transferEnergy() {
        for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; ++i) {
            ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
            TileEntity tile = getTileEntity().getWorldObj().getTileEntity(getTileEntity().x() + direction.offsetX,
                    getTileEntity().y() + direction.offsetY,
                    getTileEntity().z() + direction.offsetZ);
            if (!(tile instanceof SolarPanelTileEntity)) {
                if (tile instanceof IEnergyReceiver) {
                    IEnergyReceiver receiver = (IEnergyReceiver) tile;
                    getTileEntity().getEnergyStorage().sendMaxTo(receiver, direction.getOpposite());
                }
            }
        }
        mDirectionStartinggIndex = (mDirectionStartinggIndex + 1) % ForgeDirection.VALID_DIRECTIONS.length;
    }
}
