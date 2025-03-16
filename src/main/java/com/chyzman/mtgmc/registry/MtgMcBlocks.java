package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.block.CardBlock;
import com.chyzman.mtgmc.block.CounterBlock;
import com.chyzman.mtgmc.blockentity.CounterBlockEntity;
import com.chyzman.mtgmc.item.CardItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.function.Function;

public class MtgMcBlocks {

    public static final Block CARD = register(
            "card",
            AbstractBlock.Settings.create()
                    .noCollision()
                    .dynamicBounds(),
            CardBlock::new
    );

    public static final Block COUNTER = register(
            "counter",
            AbstractBlock.Settings.create()
                    .dynamicBounds(),
            CounterBlock::new
    );

    private static Block register(String id, Block.Settings settings, Function<AbstractBlock.Settings, Block> factory) {
        return Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, MtgMc.id(id)), factory, settings);
    }

    public static void init() {
    }
}
