package net.rom.exoplanets.internal.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public interface ILeftClickItem {
	  /**
     * Networked left-click handler. Called in EventHandlers on both the client- and
     * server-side (via packet) when a player left-clicks on nothing (in the air).
     *
     * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
     */
    default ActionResult<ItemStack> onItemLeftClick(World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    /**
     * Called when the player left-clicks on a block. Defaults to the same behavior as an empty
     * click (onItemLeftClick).
     *
     * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
     */
    default ActionResult<ItemStack> onItemLeftClickBlock(World world, EntityPlayer player, EnumHand hand) {
        return onItemLeftClick(world, player, hand);
    }
}