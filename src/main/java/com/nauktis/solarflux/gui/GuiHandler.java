package com.nauktis.solarflux.gui;

import com.nauktis.solarflux.blocks.SolarPanelTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            if (tileEntity instanceof SolarPanelTileEntity) {
                return new ContainerSolarPanel(player.inventory, (SolarPanelTileEntity) tileEntity);
            }

        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            if (tileEntity instanceof SolarPanelTileEntity) {
                return new GuiSolarPanel(player.inventory, (SolarPanelTileEntity) tileEntity);
            }
        }
        return null;
    }
}
