package com.nauktis.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseModBlock extends Block {
    private final String mModId;

    public BaseModBlock(String pModId) {
        super(Material.rock);
        mModId = checkNotNull(pModId).toLowerCase();
    }

    public BaseModBlock(String pModId, String pName) {
        this(pModId);
        setBlockName(checkNotNull(pName));
        setBlockTextureName(unwrapUnlocalizedName(getUnlocalizedName()));
    }

    public String getBlockTextureName() {
        return super.getTextureName();
    }

    @Override
    public Block setBlockTextureName(String pTextureName) {
        return super.setBlockTextureName(checkNotNull(pTextureName));
    }

    public String getModId() {
        return mModId;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s:%s", getModId(), unwrapUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String unwrapUnlocalizedName(String pUnlocalizedName) {
        return pUnlocalizedName.substring(pUnlocalizedName.indexOf(".") + 1);
    }
}
