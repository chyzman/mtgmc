package com.chyzman.mtgmc.network;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.client.MtgMcClient;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCard;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCardAutoCompletions;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestRulings;
import com.chyzman.mtgmc.network.minecraft.S2C.S2CSupplyCard;
import com.chyzman.mtgmc.network.minecraft.S2C.S2CSupplyCardAutocompletions;
import com.chyzman.mtgmc.network.minecraft.S2C.S2CSupplyRulings;
import com.chyzman.mtgmc.network.minecraft.S2C.S2CViewDump;
import com.chyzman.mtgmc.screen.DumpScreen;
import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

import static com.chyzman.mtgmc.MtgMc.SERVER_CACHE;

public class MtgMcPackets {
    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(MtgMc.id("main"));

    public static void registerCommon() {
        CHANNEL.addEndecs(builder -> {
            builder.register(MtgCard.ENDEC, MtgCard.class);

            builder.register(CardIdentifier.ENDEC, CardIdentifier.class);
        });

        CHANNEL.registerServerbound(C2SRequestCard.class, (message, access) -> SERVER_CACHE.getCard(message.cardId()).thenAccept(card -> CHANNEL.serverHandle(access.player()).send(new S2CSupplyCard(message.cardId(), card))));
        CHANNEL.registerServerbound(C2SRequestRulings.class, (message, access) -> SERVER_CACHE.getCard(message.cardId()).thenAccept(card -> SERVER_CACHE.getRulings(card).thenAccept(rulings -> CHANNEL.serverHandle(access.player()).send(new S2CSupplyRulings(message.cardId(), rulings)))));
        CHANNEL.registerServerbound(C2SRequestCardAutoCompletions.class, (message, access) -> SERVER_CACHE.getCardAutoCompletions(message.query()).thenAccept(autoCompletions -> CHANNEL.serverHandle(access.player()).send(new S2CSupplyCardAutocompletions(message.query(), autoCompletions))));


        CHANNEL.registerClientboundDeferred(S2CViewDump.class);

        CHANNEL.registerClientboundDeferred(S2CSupplyCard.class);
        CHANNEL.registerClientboundDeferred(S2CSupplyRulings.class);
        CHANNEL.registerClientboundDeferred(S2CSupplyCardAutocompletions.class);

    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        CHANNEL.registerClientbound(S2CViewDump.class, (message, access) -> MinecraftClient.getInstance().setScreen(new DumpScreen(message.title(), message.dump())));

        CHANNEL.registerClientbound(S2CSupplyCard.class, (message, access) -> {
            var cache = MtgMcClient.CLIENT_CACHE.cardCache();
            var target = cache.getIfPresent(message.identifier());
            if (target != null) {
                target.complete(message.card());
            } else {
                cache.put(message.identifier(), CompletableFuture.completedFuture(message.card()));
            }
        });

        CHANNEL.registerClientbound(S2CSupplyRulings.class, (message, access) -> {
            var cache = MtgMcClient.CLIENT_CACHE.rulingCache();
            var target = cache.getIfPresent(message.cardId());
            if (target != null) {
                target.complete(message.rulings());
            } else {
                cache.put(message.cardId(), CompletableFuture.completedFuture(message.rulings()));
            }
        });

        CHANNEL.registerClientbound(S2CSupplyCardAutocompletions.class, (message, access) -> {
            var cache = MtgMcClient.CLIENT_CACHE.cardAutoCompletionsCache();
            var target = cache.getIfPresent(message.query());
            if (target != null) {
                target.complete(message.completions());
            } else {
                cache.put(message.query(), CompletableFuture.completedFuture(message.completions()));
            }
        });


    }
}
