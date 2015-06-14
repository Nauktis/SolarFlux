package com.nauktis.core.block;

import com.nauktis.core.block.icon.IBlockIconHandler;
import com.nauktis.core.utility.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BaseModBlockWithTileEntity extends BaseModBlock implements ITileEntityProvider {
    public BaseModBlockWithTileEntity(String pModId, String pName, IBlockIconHandler pBlockIconHandler) {
        super(pModId, pName, pBlockIconHandler);
        isBlockContainer = true;
    }

    @Override
    public void breakBlock(World pWorld, int pX, int pY, int pZ, Block pBlock, int pMeta) {
        dropInventory(pWorld, pX, pY, pZ);
        super.breakBlock(pWorld, pX, pY, pZ, pBlock, pMeta);
    }

    @Override
    public boolean onBlockEventReceived(World pWorld, int pX, int pY, int pZ, int pEventNumber, int pEventArgument) {
        super.onBlockEventReceived(pWorld, pX, pY, pZ, pEventNumber, pEventArgument);
        TileEntity tileentity = pWorld.getTileEntity(pX, pY, pZ);
        if (tileentity != null) {
            return tileentity.receiveClientEvent(pEventNumber, pEventArgument);
        }
        return false;
    }

    /**
     * If the block has a TileEntity that has an inventory, this method will drop all of its content.
     */
    protected void dropInventory(World pWorld, int pX, int pY, int pZ) {
        TileEntity tileEntity = pWorld.getTileEntity(pX, pY, pZ);
        if (tileEntity instanceof IInventory) {
            IInventory inventory = (IInventory) tileEntity;
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack itemStack = inventory.getStackInSlot(i);
                if (itemStack != null && itemStack.stackSize > 0) {
                    Utils.spawnItemStack(pWorld, pX, pY, pZ, itemStack.copy());
                    itemStack.stackSize = 0;
                }
            }
        }
    }
}
