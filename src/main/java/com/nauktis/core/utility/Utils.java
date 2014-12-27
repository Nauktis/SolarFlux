package com.nauktis.core.utility;

import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.common.ModAPIManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class Utils {
    /**
     * Utility class, no public constructor.
     */
    private Utils() {

    }

    /**
     * Returns true for server side world.
     */
    public static boolean isServer(World pWorld) {
        return !pWorld.isRemote;
    }

    /**
     * Returns true for client side world.
     */
    public static boolean isClient(World pWorld) {
        return pWorld.isRemote;
    }

    /**
     * Returns true if the player has a usable wrench equipped.
     */
    public static boolean hasUsableWrench(EntityPlayer pPlayer, int pX, int pY, int pZ) {
        ItemStack tool = pPlayer.getCurrentEquippedItem();
        if (tool != null) {
            if (ModAPIManager.INSTANCE.hasAPI("BuildCraftAPI|tools") && tool.getItem() instanceof IToolWrench && ((IToolWrench) tool.getItem())
                    .canWrench(pPlayer, pX, pY, pZ)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Spawns an ItemStack in the World. No motion in X and Z axis.
     */
    public static void spawnItemStack(World pWorld, int pX, int pY, int pZ, ItemStack pItemStack) {
        EntityItem entityItem = new EntityItem(pWorld, pX + 0.5, pY + 0.5, pZ + 0.5, pItemStack);
        entityItem.motionX = 0;
        entityItem.motionZ = 0;
        pWorld.spawnEntityInWorld(entityItem);
    }
}
