package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.item.CardItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class MtgMcItems {

    public static final Item CARD = register(
            "card",
            new Item.Settings()
                    .maxCount(64)
                    .component(MtgMcComponents.CARD, null),
            CardItem::new
    );

    private static Item register(String id, Item.Settings settings, Function<Item.Settings, Item> factory) {
        return Items.register(RegistryKey.of(RegistryKeys.ITEM, MtgMc.id(id)), factory, settings);
    }

    public static void init() {
    }
}
