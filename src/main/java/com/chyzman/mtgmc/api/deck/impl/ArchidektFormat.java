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
import org.slf4j.Logger;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.chyzman.mtgmc.MtgMc.CLIENT;
import static com.chyzman.mtgmc.MtgMc.GSON;

public class ArchidektFormat extends UriDeckFormat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Pattern ARCHIDETK_URL_PATTERN = Pattern.compile("(?:https?://)?archidekt\\.com/decks/(\\d+)/([\\w_-]+)/?.*");

    @Override
    public boolean inputValid(URI input) {
        return ARCHIDETK_URL_PATTERN.matcher(input.toString()).matches();
    }

    @Override
    public Procrastinator<List<CardIdentifier.ScryfallId>> load(String input) {
        var matcher = ARCHIDETK_URL_PATTERN.matcher(input);
        if (!matcher.matches()) return Procrastinator.procrastinated(List.of());
        var deckId = matcher.group(1);
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://archidekt.com/api/decks/" + deckId + "/"))
                .build();

        return Procrastinator.supplyAsync(() -> {
                    try {
                        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (Exception e) {
                        LOGGER.error("Failed to fetch deck list from Archidekt", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchArchidektDeckList"))
                .thenExcept(throwable -> LOGGER.error("Failed to fetch deck list from Archidekt", throwable))
                .thenCompose(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Failed to fetch deck list from Archidekt: " + response.statusCode());
                    }
                    return CompletableFuture.completedFuture(parseDeckList(ArchidektResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class))));
                });
    }

    private List<CardIdentifier.ScryfallId> parseDeckList(ArchidektResponse response) {
        var categories = response.categories().stream()
                .filter(ArchidektResponse.Category::includedInDeck)
                .map(ArchidektResponse.Category::name)
                .collect(Collectors.toSet());

        return response.cards.stream()
                .filter(card -> card.categories().stream().anyMatch(categories::contains))
                .flatMap(card -> Collections.nCopies(card.quantity(), new CardIdentifier.ScryfallId(card.uid())).stream())
                .toList();
    }

    private record ArchidektResponse(
            @NotNull List<Card> cards,
            @NotNull List<Category> categories
    ) {
        public static final Endec<ArchidektResponse> ENDEC = StructEndecBuilder.of(
                Card.ENDEC.listOf().fieldOf("cards", ArchidektResponse::cards),
                Category.ENDEC.listOf().fieldOf("categories", ArchidektResponse::categories),
                ArchidektResponse::new
        );

        public record Category(
                @NotNull String name,
                boolean includedInDeck
        ) {
            public static final Endec<Category> ENDEC = StructEndecBuilder.of(
                    Endec.STRING.fieldOf("name", Category::name),
                    Endec.BOOLEAN.fieldOf("includedInDeck", Category::includedInDeck),
                    Category::new
            );
        }

        public record Card(
                @NotNull UUID uid,
                int quantity,
                @NotNull Set<String> categories
        ) {
            public static final Endec<Card> ENDEC = StructEndecBuilder.of(
                    EndecUtil.structifyEndec("uid", BuiltInEndecs.UUID).fieldOf("card", Card::uid),
                    Endec.INT.fieldOf("quantity", Card::quantity),
                    Endec.STRING.setOf().optionalFieldOf("categories", Card::categories, HashSet::new),
                    Card::new
            );
        }
    }
}
