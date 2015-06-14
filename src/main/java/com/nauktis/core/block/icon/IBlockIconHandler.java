package com.nauktis.core.block.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

/**
 * Implementations of this interface are used to handle IIcons of a Block.
 */
public interface IBlockIconHandler {
    IIcon getIcon(int pSide, int pMetadata);

    IIcon getIcon(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pSide);

    void registerBlockIcons(IIconRegister pIconRegister);
}
