package com.nauktis.solarflux.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.nauktis.core.block.BaseModBlockWithTileEntity;
import com.nauktis.solarflux.SolarFluxMod;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;

public class SolarPanelBlock extends BaseModBlockWithTileEntity {
	public SolarPanelBlock(String pName) {
		super(Reference.MOD_ID, pName);
		setCreativeTab(ModCreativeTab.MOD_TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World pWorld, int pMetadata) {
		// TODO make args coming from attributes got from constructor to create
		// several Solar panels
		SolarFluxMod.log.info("SolarPanelBlock.createNewTileEntity");
		return new SolarPanelTileEntity(4, 20000);
	}

	@Override
	public IIcon getIcon(int pSide, int pMetadata) {
		if (ForgeDirection.UP == ForgeDirection.getOrientation(pSide)) {
			return super.getIcon(pSide, pMetadata);
		}
		return Blocks.planks.getIcon(pSide, 0);
	}

	@Override
	public boolean onBlockActivated(World pWorld, int pX, int pY, int pZ, EntityPlayer pPlayer, int pSide, float pdx, float pdy, float pdz) {
		// TODO remove debug stuff
		// Shows that tile entity is desynchronized between server and client.
		if (pPlayer.isSneaking()) {
			SolarPanelTileEntity tile = (SolarPanelTileEntity) pWorld.getTileEntity(pX, pY, pZ);
			if (pWorld.isRemote) {
				pPlayer.addChatMessage(new ChatComponentText("Remote: " + tile.getEnergyStored()));
				pPlayer.addChatMessage(new ChatComponentText("Remote isDaytime: " + pWorld.isDaytime()));
			} else {
				pPlayer.addChatMessage(new ChatComponentText("NotRemote: " + tile.getEnergyStored()));
				pPlayer.addChatMessage(new ChatComponentText("NotRemote isDaytime: " + pWorld.isDaytime()));
			}
		}

		if (!pPlayer.isSneaking()) {
			if (pWorld.getTileEntity(pX, pY, pZ) instanceof SolarPanelTileEntity) {
				pPlayer.openGui(SolarFluxMod.sInstance, 0, pWorld, pX, pY, pZ);
				return true;
			}
		}
		return false;
	}
}
