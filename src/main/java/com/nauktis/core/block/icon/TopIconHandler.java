package com.nauktis.core.block.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation where the top of the block has a different texture than the rest.
 */
public class TopIconHandler implements IBlockIconHandler {
    private final IBlockIconHandler mTop;
    private final IBlockIconHandler mSides;

    public TopIconHandler(IBlockIconHandler pTop, IBlockIconHandler pSides) {
        mTop = checkNotNull(pTop);
        mSides = checkNotNull(pSides);
    }

    @Override
    public IIcon getIcon(int pSide, int pMetadata) {
        if (ForgeDirection.UP.equals(ForgeDirection.getOrientation(pSide))) {
            return mTop.getIcon(pSide, pMetadata);
        }
        return mSides.getIcon(pSide, pMetadata);
    }

    @Override
    public IIcon getIcon(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pSide) {
        if (ForgeDirection.UP.equals(ForgeDirection.getOrientation(pSide))) {
            return mTop.getIcon(pBlockAccess, pX, pY, pZ, pSide);
        }
        return mSides.getIcon(pBlockAccess, pX, pY, pZ, pSide);
    }

    @Override
    public void registerBlockIcons(IIconRegister pIconRegister) {
        mTop.registerBlockIcons(pIconRegister);
        mSides.registerBlockIcons(pIconRegister);
    }
}
