package com.chyzman.mtgmc.api.deck.impl;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.deck.api.UriDeckFormat;
import com.chyzman.mtgmc.util.EndecUtil;
import com.chyzman.mtgmc.util.Procrastinator;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.chyzman.mtgmc.MtgMc.*;

public class AetherhubFormat extends UriDeckFormat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Pattern AETHERHUB_URL_PATTERN = Pattern.compile("(?:https?://)?aetherhub\\.com/Deck/([\\w_-]+)/?.*");

    @Override
    public boolean inputValid(URI input) {
        return AETHERHUB_URL_PATTERN.matcher(input.toString()).matches();
    }

    @Override
    public Procrastinator<List<CardIdentifier.ScryfallId>> load(String input) {
        var matcher = AETHERHUB_URL_PATTERN.matcher(input);
        if (!matcher.matches()) return Procrastinator.procrastinated(List.of());

        return Procrastinator.supplyAsync(() -> {
                    try {
                        var doc = Jsoup.connect("https://aetherhub.com/Deck/" + matcher.group(1) + "/").get();
                        return doc.select("[data-deckid]").attr("data-deckid");
                    } catch (IOException | NumberFormatException e) {
                        LOGGER.error("Failed to fetch deck ID from Aetherhub", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchAetherhubDeckId"))
                .thenExcept(throwable -> LOGGER.error("Failed to fetch deck ID from Aetherhub", throwable))
                .thenApplyAsync(deckId -> {
                    var request = HttpRequest
                            .newBuilder()
                            .uri(URI.create("https://aetherhub.com/Deck/FetchMtgaDeckJson?deckId=" + deckId + "&langId=0&simple=False"))
                            .build();
                    try {
                        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e) {
                        LOGGER.error("Failed to fetch deck list from Aetherhub", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchAetherhubDeckList"))
                .thenExcept(throwable -> LOGGER.error("Failed to fetch deck list from Aetherhub", throwable))
                .thenCompose(response -> {
                    if (response.statusCode() != 200) {
                        LOGGER.error("Failed to fetch deck list from Aetherhub: {}", response.body());
                        throw new RuntimeException("Failed to fetch deck list from Aetherhub: " + response.statusCode());
                    }
                    try {
                        return parseDeckList(AetherHubResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)));
                    } catch (Exception e) {
                        LOGGER.error("Failed to parse deck list from Aetherhub", e);
                        throw new RuntimeException(e);
                    }
                });
    }

    private Procrastinator<List<CardIdentifier.ScryfallId>> parseDeckList(AetherHubResponse response) {
        var cards = response.cards.stream()
                .filter(card -> card.quantity != null)
                .flatMap(card -> Collections.nCopies(card.quantity, card).stream())
                .map(card -> new CardIdentifier.NameSet(card.name, card.set))
                .map(SERVER_CACHE::getCard)
                .toList();

        return new Procrastinator<>(CompletableFuture.allOf(cards.stream().map(Procrastinator::toCompletableFuture).toArray(CompletableFuture[]::new)))
                .thenExcept(throwable -> LOGGER.error("Failed to parse deck list from Aetherhub", throwable))
                .thenApply(thisIsVoidSoFuckYouInParticular -> cards.stream().map(mtgCardProcrastinator -> new CardIdentifier.ScryfallId(mtgCardProcrastinator.toCompletableFuture().resultNow().id())).toList());
    }

    private record AetherHubResponse(
            @NotNull List<Card> cards
    ) {
        public static final Endec<AetherHubResponse> ENDEC = StructEndecBuilder.of(
                Card.ENDEC.listOf().fieldOf("convertedDeck", AetherHubResponse::cards),
                AetherHubResponse::new
        );

        private record Card(
                int cardId,
                @Nullable Integer quantity,
                @NotNull String name,
                @NotNull String set,
                @Nullable String number
        ) {
            public static final Endec<Card> ENDEC = StructEndecBuilder.of(
                    Endec.INT.fieldOf("cardId", Card::cardId),
                    Endec.INT.optionalFieldOf("quantity", Card::quantity, (Integer) null),
                    Endec.STRING.fieldOf("name", Card::name),
                    Endec.STRING.fieldOf("set", Card::set),
                    Endec.STRING.optionalFieldOf("number", Card::number, (String) null),
                    Card::new
            );
        }
    }
}
