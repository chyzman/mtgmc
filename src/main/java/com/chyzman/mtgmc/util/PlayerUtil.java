package com.chyzman.mtgmc.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class PlayerUtil {

    public static void shoveStackIntoHotbarNicely(PlayerEntity player, ItemStack stack) {
        var inv = player.getInventory();
        if (player.getMainHandStack().isEmpty()) {
            player.setStackInHand(Hand.MAIN_HAND, stack);
        } else {
            inv.offerOrDrop(stack);
        }
    }
}
