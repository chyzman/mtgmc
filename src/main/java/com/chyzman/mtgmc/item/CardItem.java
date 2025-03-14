package com.chyzman.mtgmc.item;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.client.MtgMcClient;
import com.chyzman.mtgmc.registry.MtgMcBlocks;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import io.wispforest.owo.Owo;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class CardItem extends BlockItem {
    public static final Text UNKNOWN_CARD = Text.translatable("item.mtgmc.card.unknown");

    public CardItem(Settings settings) {
        super(MtgMcBlocks.CARD, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        var server = Owo.currentServer();
        if (server == null) return UNKNOWN_CARD;

        MtgCache cache = server.getOverworld().isClient() ? MtgMcClient.CLIENT_CACHE : MtgMc.SERVER_CACHE;

        if (!stack.contains(MtgMcComponents.CARD)) return UNKNOWN_CARD;

        var card = cache.getCard(stack.get(MtgMcComponents.CARD)).getNow(null);
        if (card != null) return Text.literal(card.getDisplayName());

        return UNKNOWN_CARD;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var server = Owo.currentServer();
        if (server == null) return;

        MtgCache cache = server.getOverworld().isClient() ? MtgMcClient.CLIENT_CACHE : MtgMc.SERVER_CACHE;

        if (!stack.contains(MtgMcComponents.CARD)) return;


        var card = cache.getCard(stack.get(MtgMcComponents.CARD)).getNow(null);
        if (card == null) return;
        tooltip.add(Text.literal(card.typeLine()));
//        if (card.oracleText() != null) tooltip.add(Text.literal(card.oracleText()));
//        if (card.flavorText() != null) tooltip.add(Text.literal(card.flavorText()));

    }
}
