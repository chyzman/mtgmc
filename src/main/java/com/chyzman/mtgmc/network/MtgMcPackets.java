package com.chyzman.mtgmc.network;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.card.cache.ClientCardCache;
import com.chyzman.mtgmc.client.MtgMcClient;
import com.chyzman.mtgmc.network.S2C.SupplyCard;
import com.chyzman.mtgmc.network.S2C.ViewDump;
import com.chyzman.mtgmc.screen.DumpScreen;
import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

public class MtgMcPackets {
    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(MtgMc.id("main"));

    public static void registerCommon() {
        CHANNEL.addEndecs(builder -> {
            builder.register(MtgCard.ENDEC, MtgCard.class);

            builder.register(CardIdentifier.ENDEC, CardIdentifier.class);
        });

        CHANNEL.registerServerbound(SupplyCard.class, (supply, access) -> MtgMc.SERVER_CARD_CACHE.getCard(supply.identifier()).thenAccept(card -> CHANNEL.serverHandle(access.player()).send(new SupplyCard(supply.identifier(), card))));

        CHANNEL.registerClientboundDeferred(SupplyCard.class);

        CHANNEL.registerClientboundDeferred(ViewDump.class);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        CHANNEL.registerClientbound(SupplyCard.class, (supply, access) -> MtgMcClient.CLIENT_CARD_CACHE.addToCache(supply.identifier(), supply.card()));

        CHANNEL.registerClientbound(ViewDump.class, (message, access) -> MinecraftClient.getInstance().setScreen(new DumpScreen(message.title(), message.dump())));

    }
}
