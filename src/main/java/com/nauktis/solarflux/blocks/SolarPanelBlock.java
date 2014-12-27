package com.nauktis.solarflux.blocks;

import com.nauktis.core.block.BaseModBlockWithTileEntity;
import com.nauktis.core.utility.Utils;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;
import com.nauktis.solarflux.utility.Lang;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SolarPanelBlock extends BaseModBlockWithTileEntity {
    protected final int mMaximumEnergyGeneration;
    protected final int mMaximumEnergyTransfer;
    protected final int mEnergyCapacity;
    private IIcon mBlockSideIcon;

    public SolarPanelBlock(String pName, int pMaximumEnergyGeneration, int pEnergyCapacity) {
        super(Reference.MOD_ID, pName);
        mMaximumEnergyGeneration = pMaximumEnergyGeneration;
        mMaximumEnergyTransfer = mMaximumEnergyGeneration * 8;
        mEnergyCapacity = pEnergyCapacity;
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

    public int getMaximumEnergyGeneration() {
        return mMaximumEnergyGeneration;
    }

    public int getMaximumEnergyTransfer() {
        return mMaximumEnergyTransfer;
    }

    public int getEnergyCapacity() {
        return mEnergyCapacity;
    }

    @Override
    public TileEntity createNewTileEntity(World pWorld, int pMetadata) {
        return new SolarPanelTileEntity(mMaximumEnergyGeneration, mMaximumEnergyTransfer, mEnergyCapacity);
    }

    @Override
    public IIcon getIcon(int pSide, int pMetadata) {
        if (ForgeDirection.UP == ForgeDirection.getOrientation(pSide)) {
            return super.getIcon(pSide, pMetadata);
        }
        return mBlockSideIcon;
    }

    @Override
    public void onBlockPlacedBy(World pWorld, int pX, int pY, int pZ, EntityLivingBase pEntity, ItemStack pItemStack) {
        // When the solar panel is placed, we restore its energy from the Item.
        if (pItemStack.stackTagCompound != null) {
            SolarPanelTileEntity localTileCell = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
            localTileCell.setEnergyStored(pItemStack.stackTagCompound.getInteger(StatefulEnergyStorage.NBT_ENERGY));
        }
        super.onBlockPlacedBy(pWorld, pX, pY, pZ, pEntity, pItemStack);
    }

    @Override
    public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pSide, float pdx, float pdy, float pdz) {
        if (Utils.isServer(pWorld) && pPlayer.isSneaking()) {
            if (Utils.hasUsableWrench(pPlayer, pX, pY, pZ)) {
                dismantleBlock(pWorld, pX, pY, pZ);
                return true;
            }
            displayChatInformation(pWorld, pX, pY, pZ, pPlayer);
            return true;
        }
        return false;
    }

    private void displayChatInformation(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer) {
        SolarPanelTileEntity tile = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
        String message = String.format("%s: [%d%%] %,d / %,d %s: %,d",
                Lang.localise("energy.stored"),
                tile.getPercentageEnergyStored(),
                tile.getEnergyStored(),
                tile.getMaxEnergyStored(),
                Lang.localise("energy.generation"),
                tile.getEnergyProduced());
        pPlayer.addChatMessage(new ChatComponentText(message));
    }

    /**
     * Dismantles the block and drops it in the air.
     * Used when wrenched.
     */
    private void dismantleBlock(World pWorld, int pX, int pY, int pZ) {
        ItemStack itemStack = new ItemStack(this);

        // Store the energy in the ItemStack (from the TileEntity)
        if (ModConfiguration.doesKeepEnergyWhenDismantled()) {
            SolarPanelTileEntity localTileCell = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
            int internalEnergy = localTileCell.getEnergyStored();
            if (internalEnergy > 0) {
                if (itemStack.getTagCompound() == null) {
                    itemStack.setTagCompound(new NBTTagCompound());
                }
                itemStack.getTagCompound().setInteger(StatefulEnergyStorage.NBT_ENERGY, internalEnergy);
            }
            SolarFluxMod.log.trace("Dismantled solar panel with %,d RF.", internalEnergy);
        }

        pWorld.setBlockToAir(pX, pY, pZ);
        Utils.spawnItemStack(pWorld, pX, pY, pZ, itemStack);
    }

    @Override
    public void registerBlockIcons(IIconRegister pIconRegister) {
        super.registerBlockIcons(pIconRegister);
        mBlockSideIcon = pIconRegister.registerIcon(this.getTextureName() + "_side");
    }
}
