package com.chyzman.mtgmc.item;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.card.cache.CardCache;
import com.chyzman.mtgmc.client.MtgMcClient;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import io.wispforest.owo.Owo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class CardItem extends Item {
    public static final Text UNKNOWN_CARD = Text.translatable("item.mtgmc.card.unknown");

    public CardItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        var server = Owo.currentServer();
        if (server == null) return UNKNOWN_CARD;

        CardCache cache = server.getOverworld().isClient() ? MtgMcClient.CLIENT_CARD_CACHE : MtgMc.SERVER_CARD_CACHE;

        if (!stack.contains(MtgMcComponents.CARD)) return UNKNOWN_CARD;

        var card = cache.getCard(stack.get(MtgMcComponents.CARD)).getNow(null);
        if (card != null) return Text.literal(card.getDisplayName());

        return UNKNOWN_CARD;
    }
}
