package com.nauktis.solarflux.blocks;

import com.nauktis.core.block.BaseModBlockWithTileEntity;
import com.nauktis.core.block.icon.IBlockIconHandler;
import com.nauktis.core.utility.Utils;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.NBTConstants;
import com.nauktis.solarflux.reference.Reference;
import com.nauktis.solarflux.utility.Lang;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SolarPanelBlock extends BaseModBlockWithTileEntity {
    private final int mTierIndex;
    private IIcon mBlockSideIcon;

    public SolarPanelBlock(String pName, IBlockIconHandler pBlockIconHandler, int pTierIndex) {
        super(Reference.MOD_ID, pName, pBlockIconHandler);
        mTierIndex = pTierIndex;
        setCreativeTab(ModCreativeTab.MOD_TAB);
        setHardness(3.0F);
        setResistance(5.0F);
        setStepSound(soundTypeMetal);
        if (!ModConfiguration.isSolarPanelFullBlock()) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, ModConfiguration.getSolarPanelHeight(), 1.0F);
            setLightOpacity(255);
            useNeighborBrightness = true;
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return ModConfiguration.isSolarPanelFullBlock() && super.renderAsNormalBlock();
    }

    @Override
    public boolean isOpaqueCube() {
        return ModConfiguration.isSolarPanelFullBlock() && super.isOpaqueCube();
    }

    @Override
    public TileEntity createNewTileEntity(World pWorld, int pMetadata) {
        return new SolarPanelTileEntity(mTierIndex);
    }

    @Override
    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pEntity, ItemStack pItemStack) {
        super.onBlockPlacedBy(pWorld, pX, pY, pZ, pEntity, pItemStack);
        // When the solar panel is placed, we restore its energy from the Item.
        if (pItemStack.getTagCompound() != null) {
            // TODO Consider moving this logic to the Tile Entity class. (could prevent exposing internals of the tile entity) (e.g. readFromItemStack/writeToItemStack)
            SolarPanelTileEntity localTileCell = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
            localTileCell.getInventory().readFromNBT(pItemStack.getTagCompound());
            // Force update to refresh the upgrade cache before restoring the energy.
            localTileCell.markDirty();
            localTileCell.setEnergyStored(pItemStack.getTagCompound().getInteger(NBTConstants.ENERGY));
        }
    }

    @Override
    public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pSide, float pdx, float pdy, float pdz) {
        if (Utils.isServer(pWorld)) {
            if (pPlayer.isSneaking()) {
                if (Utils.hasUsableWrench(pPlayer, pX, pY, pZ)) {
                    dismantleBlock(pWorld, pX, pY, pZ);
                    return true;
                }
                if (ModConfiguration.infoOnSneakClick()) {
                    displayChatInformation(pWorld, pX, pY, pZ, pPlayer);
                }
            } else {
                if (pWorld.getTileEntity(pX, pY, pZ) instanceof SolarPanelTileEntity) {
                    pPlayer.openGui(SolarFluxMod.mInstance, 0, pWorld, pX, pY, pZ);
                }
            }
        }
        return true;
    }

    private void displayChatInformation(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer) {
        SolarPanelTileEntity tile = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
        String message = String.format(
                "%s: [%d%%] %,d / %,d %s: %,d",
                Lang.localise("energy.stored"),
                tile.getPercentageEnergyStored(),
                tile.getEnergyStored(),
                tile.getMaxEnergyStored(),
                Lang.localise("energy.generation"),
                tile.getCurrentEnergyGeneration());
        pPlayer.addChatMessage(new ChatComponentText(message));
    }

    /**
     * Dismantles the block and drops it in the air.
     * Used when wrenched.
     */
    private void dismantleBlock(World pWorld, int pX, int pY, int pZ) {
        // TODO Consider moving this logic to the Tile Entity class. (could prevent exposing internals of the tile entity) (e.g. readFromItemStack/writeToItemStack)
        ItemStack itemStack = new ItemStack(this);

        // Store the energy in the ItemStack (from the TileEntity)
        if (ModConfiguration.doesKeepEnergyWhenDismantled()) {
            SolarPanelTileEntity localTileCell = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
            int internalEnergy = localTileCell.getEnergyStored();
            if (internalEnergy > 0) {
                if (itemStack.getTagCompound() == null) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                itemStack.getTagCompound().setInteger(NBTConstants.ENERGY, internalEnergy);
            }
        }

        // Store the inventory in the ItemStack (from the TileEntity)
        if (ModConfiguration.doesKeepInventoryWhenDismantled()) {
            SolarPanelTileEntity localTileCell = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
            int upgradeInstalled = localTileCell.getTotalUpgradeInstalled();

            if (upgradeInstalled > 0) {
                if (itemStack.getTagCompound() == null) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                localTileCell.getInventory().writeToNBT(itemStack.getTagCompound());

                // Adding info for tooltip.
                itemStack.getTagCompound().setInteger(NBTConstants.TOOLTIP_UPGRADE_COUNT, upgradeInstalled);
                itemStack.getTagCompound().setInteger(NBTConstants.TOOLTIP_CAPACITY, localTileCell.getEnergyStorage().getMaxEnergyStored());
                itemStack.getTagCompound().setInteger(NBTConstants.TOOLTIP_TRANSFER_RATE, localTileCell.getEnergyStorage().getMaxTransferExtract());

                // We remove the tile entity to avoid having the inventory content dropped into the world.
                pWorld.removeTileEntity(pX, pY, pZ);
            }
        }

        pWorld.setBlockToAir(pX, pY, pZ);
        Utils.spawnItemStack(pWorld, pX, pY, pZ, itemStack);
    }

    public int getTierIndex() {
        return mTierIndex;
    }
}
