package com.nauktis.core.block;

import com.nauktis.core.block.icon.IBlockIconHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseModBlock extends Block {
    private final String mModId;
    private final IBlockIconHandler mBlockIconHandler;

    public BaseModBlock(String pModId, String pName, IBlockIconHandler pBlockIconHandler) {
        super(Material.rock);
        mModId = checkNotNull(pModId).toLowerCase();
        mBlockIconHandler = checkNotNull(pBlockIconHandler);
        setBlockName(checkNotNull(pName));
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s:%s", mModId, unwrapUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String unwrapUnlocalizedName(String pUnlocalizedName) {
        return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
    }

    @Override
    public IIcon getIcon(int pSide, int pMetadata) {
        return mBlockIconHandler.getIcon(pSide, pMetadata);
    }

    @Override
    public IIcon getIcon(IBlockAccess pBlockAccess, int pX, int pY, int pZ, int pSide) {
        return mBlockIconHandler.getIcon(pBlockAccess, pX, pY, pZ, pSide);
    }

    @Override
    public void registerBlockIcons(IIconRegister pIconRegister) {
        mBlockIconHandler.registerBlockIcons(pIconRegister);
    }
}
