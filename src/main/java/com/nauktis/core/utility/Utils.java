package com.nauktis.core.utility;

import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.common.ModAPIManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public final class Utils {
    public static final Random RANDOM = new Random();

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

    /**
     * Returns true if the two ItemStacks contains exactly the same item without taking stack size into account.
     * Credit to Pahimar.
     */
    public static boolean itemStacksEqualIgnoreStackSize(ItemStack pItemStack1, ItemStack pItemStack2) {
        if (pItemStack1 != null && pItemStack2 != null) {
            // Compare itemID
            if (Item.getIdFromItem(pItemStack1.getItem()) - Item.getIdFromItem(pItemStack2.getItem()) == 0) {
                // Compare item
                if (pItemStack1.getItem() == pItemStack2.getItem()) {
                    // Compare meta
                    if (pItemStack1.getItemDamage() == pItemStack2.getItemDamage()) {
                        // Compare NBT presence
                        if (pItemStack1.hasTagCompound() && pItemStack2.hasTagCompound()) {
                            // Compare NBT
                            if (ItemStack.areItemStackTagsEqual(pItemStack1, pItemStack2)) {
                                return true;
                            }
                        } else if (!pItemStack1.hasTagCompound() && !pItemStack2.hasTagCompound()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
