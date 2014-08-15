package com.nauktis.solarflux.blocks;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.nauktis.core.block.BaseModBlockWithTileEntity;
import com.nauktis.solarflux.creativetab.ModCreativeTab;
import com.nauktis.solarflux.reference.Reference;

public class SolarPanelBlock extends BaseModBlockWithTileEntity {
	public SolarPanelBlock(String pName) {
		super(Reference.MOD_ID, pName);
		setCreativeTab(ModCreativeTab.MOD_TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World pWorld, int pMetadata) {
		// TODO make args coming from attributes got from constructor to create several Solar panels
		return new SolarPanelTileEntity(4, 20000);
	}

	@Override
	public IIcon getIcon(int pSide, int pMetadata) {
		if (ForgeDirection.EAST == ForgeDirection.getOrientation(pSide)) {
			return Blocks.planks.getIcon(pSide, 0);
		}
		return super.getIcon(pSide, pMetadata);
	}
}
