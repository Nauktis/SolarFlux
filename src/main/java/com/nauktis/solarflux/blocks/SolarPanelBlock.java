package com.nauktis.solarflux.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.nauktis.core.block.BaseModBlockWithTileEntity;
import com.nauktis.solarflux.config.ModConfiguration;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;

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
		if (!ModConfiguration.isSolarPanelFullBlock()) {
			return false;
		}
		return super.renderAsNormalBlock();
	}

	@Override
	public boolean isOpaqueCube() {
		if (!ModConfiguration.isSolarPanelFullBlock()) {
			return false;
		}
		return super.isOpaqueCube();
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
	public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pSide, float pdx, float pdy, float pdz) {
		if (!pWorld.isRemote && pPlayer.isSneaking()) {
			displayChatInformation(pWorld, pX, pY, pZ, pPlayer);
			return true;
		}
		return false;
	}

	private void displayChatInformation(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer) {
		SolarPanelTileEntity tile = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
		String message = String.format("Energy: [%d%%] %,d / %,d Generating: %,d", tile.getPercentageEnergyStored(), tile.getEnergyStored(), tile.getMaxEnergyStored(), tile.getEnergyProduced());
		pPlayer.addChatMessage(new ChatComponentText(message));
	}

	@Override
	public void registerBlockIcons(IIconRegister pIconRegister) {
		super.registerBlockIcons(pIconRegister);
		mBlockSideIcon = pIconRegister.registerIcon(this.getTextureName() + "_side");
	}
}
