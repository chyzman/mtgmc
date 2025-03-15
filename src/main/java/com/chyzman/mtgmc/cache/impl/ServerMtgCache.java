package com.chyzman.mtgmc.cache.impl;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.ruling.Ruling;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.network.http.request.CardCollectionRequest;
import com.chyzman.mtgmc.network.http.response.CardAutoCompletionsResponse;
import com.chyzman.mtgmc.network.http.response.CardCollectionResponse;
import com.chyzman.mtgmc.network.http.response.CardRulingsResponse;
import com.chyzman.mtgmc.util.Procrastinator;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonSerializer;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.chyzman.mtgmc.MtgMc.GSON;

public class ServerMtgCache extends MtgCache {

    @Override
    public LoadingCache<CardIdentifier, Procrastinator<MtgCard>> cardCache() {
        return cardCache;
    }

    private final Map<CardIdentifier, Procrastinator<MtgCard>> cardQueue = new ConcurrentHashMap<>();

    private final LoadingCache<CardIdentifier, Procrastinator<MtgCard>> cardCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<MtgCard> load(@NotNull CardIdentifier key) {
                    if (cardQueue.containsKey(key)) {
                        return cardQueue.get(key);
                    } else {
                        var future = new Procrastinator<MtgCard>();
                        cardQueue.put(key, new Procrastinator<>());
                        return future;
                    }
                }
            }
    );

    public void updateQueue() {
        if (cardQueue.isEmpty()) return;
        var targets = cardQueue.keySet().stream().limit(75).toList();
        Procrastinator.supplyAsync(() -> {
                    var request = HttpRequest.newBuilder()
                            .uri(URI.create(MtgMc.SCRYFALL + "/cards/collection"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(CardCollectionRequest.ENDEC.encodeFully(GsonSerializer::of, new CardCollectionRequest(targets)).toString()))
                            .build();

                    try {
                        return MtgMc.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e) {
                        LOGGER.error("Failed to fetch cards from scryfall", e);
                        throw new RuntimeException(e);
                    }

                }, Util.getDownloadWorkerExecutor().named("mtgmcBulkCardFetcher"))
                .thenExcept(throwable -> LOGGER.error("Failed to fetch cards from scryfall", throwable))
                .thenAccept(response -> {
                    if (response.statusCode() != 200) {
                        LOGGER.error("Failed to fetch cards from scryfall: {}", response.body());
                        return;
                    }
                    try {
                        var cards = CardCollectionResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class));
                        var index = 0;
                        for (var target : targets) {
                            var card = cards.data().size() > index ? cards.data().get(index) : null;
                            var targetFuture = cardCache.getIfPresent(target);
                            if (targetFuture != null) targetFuture.toCompletableFuture().complete(card);
                            cardQueue.remove(target);
                            if (card != null) index++;
                        }
                    } catch (Exception e) {
                        LOGGER.error("Failed to parse cards from scryfall", e);
                    }
                });
    }


    @Override
    public LoadingCache<CardIdentifier.OracleId, Procrastinator<List<Ruling>>> rulingCache() {
        return rulingCache;
    }

    private final LoadingCache<CardIdentifier.OracleId, Procrastinator<List<Ruling>>> rulingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<List<Ruling>> load(@NotNull CardIdentifier.OracleId key) {
                    return Procrastinator.supplyAsync(() -> {
                                var request = HttpRequest.newBuilder()
                                        .uri(URI.create(MtgMc.SCRYFALL + "/cards/" + key + "/rulings"))
                                        .build();

                                try {
                                    return MtgMc.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                                } catch (IOException | InterruptedException e) {
                                    LOGGER.error("Failed to fetch rulings from scryfall", e);
                                    throw new RuntimeException(e);
                                }

                            }, Util.getDownloadWorkerExecutor().named("mtgmcCardRulingFether"))
                            .thenExcept(throwable -> LOGGER.error("Failed to fetch rulings from scryfall", throwable))
                            .thenApply(response -> {
                                if (response.statusCode() != 200) {
                                    LOGGER.error("Failed to fetch rulings from scryfall: {}", response.body());
                                    return List.of();
                                }

                                return CardRulingsResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)).data();
                            });
                }
            }
    );

    @Override
    public LoadingCache<String, Procrastinator<List<String>>> cardAutoCompletionsCache() {
        return cardAutoCompletionsCache;
    }

    private final LoadingCache<String, Procrastinator<List<String>>> cardAutoCompletionsCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<List<String>> load(@NotNull String key) {
                    return Procrastinator.supplyAsync(() -> {
                                var request = HttpRequest.newBuilder()
                                        .uri(URI.create(MtgMc.SCRYFALL + "/cards/autocomplete?q=" + URLEncoder.encode(key, StandardCharsets.UTF_8) + "&include_extras=true"))
                                        .build();

                                try {
                                    return MtgMc.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                                } catch (IOException | InterruptedException e) {
                                    LOGGER.error("Failed to fetch card auto completions from scryfall", e);
                                    throw new RuntimeException(e);
                                }

                            }, Util.getDownloadWorkerExecutor().named("mtgmcCardAutoCompletionsFetcher"))
                            .thenExcept(throwable -> LOGGER.error("Failed to fetch card auto completions from scryfall", throwable))
                            .thenApply(response -> {
                                if (response.statusCode() != 200) {
                                    LOGGER.error("Failed to fetch card auto completions from scryfall: {}", response.body());
                                    return List.of();
                                }

                                return CardAutoCompletionsResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)).data();
                            });
                }
            }
    );
}
