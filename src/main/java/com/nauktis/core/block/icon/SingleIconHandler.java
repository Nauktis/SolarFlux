package com.nauktis.core.block.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import static java.util.Objects.requireNonNull;

public class SingleIconHandler implements IBlockIconHandler {
    private final String mTextureName;
    private IIcon mBlockIcon;

    /**
     * @param pModId       The Id of the Mod.
     * @param pTextureName The basename (filename without extension) of the texture.
     */
    public SingleIconHandler(String pModId, String pTextureName) {
        mTextureName = String.format("%s:%s", requireNonNull(pModId).toLowerCase(), requireNonNull(pTextureName));
    }

    @Override
    public IIcon getIcon(int pSide, int pMetadata) {
        return mBlockIcon;
    }

    @Override
    public IIcon getIcon(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pSide) {
        return getIcon(pSide, pBlockAccess.getBlockMetadata(pX, pY, pZ));
    }

    @Override
    public void registerBlockIcons(IIconRegister pIconRegister) {
        mBlockIcon = pIconRegister.registerIcon(mTextureName);
    }
}
