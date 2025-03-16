package com.chyzman.mtgmc.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// shamelessly stolen from affinity
public interface InteractableBlockEntity {

    ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit);

    ActionResult onAttack(World world, PlayerEntity player, BlockHitResult hit);

    static ActionResult tryHandle(World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof InteractableBlockEntity entity)) return ActionResult.PASS;
        return entity.onUse(player, hand, hit);
    }

    static ActionResult tryHandleAttack(World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof InteractableBlockEntity entity)) return ActionResult.PASS;
        return entity.onAttack(world, player, hit);
    }

}
