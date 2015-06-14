package com.nauktis.core.block.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import static java.util.Objects.requireNonNull;

public class ConnectedIconHandler implements IBlockIconHandler {
    private final ForgeDirection[] SIDE_UP = {ForgeDirection.EAST, ForgeDirection.NORTH, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP, ForgeDirection.UP};
    private final ForgeDirection[] SIDE_RIGHT = {ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.EAST, ForgeDirection.EAST};
    private final ForgeDirection[] SIDE_DOWN = {ForgeDirection.WEST, ForgeDirection.SOUTH, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN, ForgeDirection.DOWN};
    private final ForgeDirection[] SIDE_LEFT = {ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST, ForgeDirection.WEST};
    private final String mTextureBaseName;
    private final IIcon[] mIcons = new IIcon[16];

    /**
     * @param pModId       The Id of the Mod.
     * @param pTextureName The basename (filename without extension and index) of the texture.
     */
    public ConnectedIconHandler(String pModId, String pTextureName) {
        mTextureBaseName = String.format("%s:%s", requireNonNull(pModId).toLowerCase(), requireNonNull(pTextureName));
    }

    @Override
    public IIcon getIcon(int pSide, int pMetadata) {
        return mIcons[0];
    }

    @Override
    public IIcon getIcon(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pSide) {
        int textureIndex = 0;

        ForgeDirection neighborDirection = SIDE_UP[pSide];
        if (shouldConnect(pBlockAccess, pX, pY, pZ, pX + neighborDirection.offsetX, pY + neighborDirection.offsetY, pZ + neighborDirection.offsetZ)) {
            textureIndex += 1;
        }

        neighborDirection = SIDE_RIGHT[pSide];
        if (shouldConnect(pBlockAccess, pX, pY, pZ, pX + neighborDirection.offsetX, pY + neighborDirection.offsetY, pZ + neighborDirection.offsetZ)) {
            textureIndex += 2;
        }

        neighborDirection = SIDE_DOWN[pSide];
        if (shouldConnect(pBlockAccess, pX, pY, pZ, pX + neighborDirection.offsetX, pY + neighborDirection.offsetY, pZ + neighborDirection.offsetZ)) {
            textureIndex += 4;
        }

        neighborDirection = SIDE_LEFT[pSide];
        if (shouldConnect(pBlockAccess, pX, pY, pZ, pX + neighborDirection.offsetX, pY + neighborDirection.offsetY, pZ + neighborDirection.offsetZ)) {
            textureIndex += 8;
        }

        return mIcons[textureIndex];
    }

    private boolean shouldConnect(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pCandidateX, int pCandidateY, int pCandidateZ) {
        return pBlockAccess.getBlock(pX, pY, pZ).equals(pBlockAccess.getBlock(pCandidateX, pCandidateY, pCandidateZ));
    }

    @Override
    public void registerBlockIcons(IIconRegister pIconRegister) {
        for (int i = 0; i < mIcons.length; i++) {
            mIcons[i] = pIconRegister.registerIcon(mTextureBaseName + i);
        }
    }
}
