package com.nauktis.solarflux.blocks.modules;

import net.minecraft.tileentity.TileEntity;

public abstract class AbstractTileEntityModule<T extends TileEntity> implements ITileEntityModule {
    private final T mTileEntity;

    protected AbstractTileEntityModule(T pTileEntity) {
        mTileEntity = pTileEntity;
    }

    public T getTileEntity() {
        return mTileEntity;
    }
}
