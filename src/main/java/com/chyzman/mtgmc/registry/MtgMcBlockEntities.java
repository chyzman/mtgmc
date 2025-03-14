package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.blockentity.CardBlockEntity;
import com.chyzman.mtgmc.item.CardItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

public class MtgMcBlockEntities {

    public static final BlockEntityType<CardBlockEntity> CARD = register(
            "card",
            CardBlockEntity::new,
            MtgMcBlocks.CARD
    );

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, MtgMc.id(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void init() {
    }
}
