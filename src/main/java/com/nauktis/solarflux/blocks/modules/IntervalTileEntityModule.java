package com.nauktis.solarflux.blocks.modules;

import com.nauktis.core.utility.Utils;
import net.minecraft.tileentity.TileEntity;

public abstract class IntervalTileEntityModule<T extends TileEntity> extends AbstractTileEntityModule<T> {
    private final short mTickRate;
    private final short mTickShift;

    public IntervalTileEntityModule(T pTileEntity, short pTickRate) {
        super(pTileEntity);
        mTickRate = pTickRate;
        mTickShift = (short) Utils.RANDOM.nextInt(pTickRate);
    }

    @Override
    public void tick() {
        if ((getTileEntity().getWorldObj().getTotalWorldTime() + mTickShift) % mTickRate == 0) {
            doTick();
        }
    }

    protected abstract void doTick();
}
