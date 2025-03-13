package com.chyzman.mtgmc.cache.impl;

import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.ruling.Ruling;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.network.http.request.CardCollectionRequest;
import com.chyzman.mtgmc.network.http.response.CardAutoCompletionsResponse;
import com.chyzman.mtgmc.network.http.response.CardCollectionResponse;
import com.chyzman.mtgmc.network.http.response.CardRulingsResponse;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonSerializer;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ServerMtgCache extends MtgCache {

    @Override
    public LoadingCache<CardIdentifier, CompletableFuture<MtgCard>> cardCache() {
        return cardCache;
    }

    private final Map<CardIdentifier, CompletableFuture<MtgCard>> cardQueue = new HashMap<>();

    private final LoadingCache<CardIdentifier, CompletableFuture<MtgCard>> cardCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<MtgCard> load(@NotNull CardIdentifier key) {
                    if (cardQueue.containsKey(key)) {
                        return cardQueue.get(key);
                    } else {
                        var future = new CompletableFuture<MtgCard>();
                        cardQueue.put(key, new CompletableFuture<>());
                        return future;
                    }
                }
            }
    );

    public void updateQueue() {
        if (cardQueue.isEmpty()) return;
        var targets = cardQueue.keySet().stream().limit(75).toList();
        var url = HttpRequest.newBuilder()
                .uri(URI.create(SCRYFALL + "/cards/collection"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(CardCollectionRequest.ENDEC.encodeFully(GsonSerializer::of, new CardCollectionRequest(targets)).toString()))
                .build();
        CLIENT.sendAsync(url, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    var cards = CardCollectionResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class));
                    var index = 0;
                    for (var target : targets) {
                        var card = cards.data().size() > index ? cards.data().get(index) : null;
                        var targetFuture = cardCache.getIfPresent(target);
                        if (targetFuture != null) targetFuture.complete(card);
                        cardQueue.remove(target);
                        if (card != null) index++;
                    }
                });
    }


    @Override
    public LoadingCache<CardIdentifier.OracleId, CompletableFuture<List<Ruling>>> rulingCache() {
        return rulingCache;
    }

    private final LoadingCache<CardIdentifier.OracleId, CompletableFuture<List<Ruling>>> rulingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<List<Ruling>> load(@NotNull CardIdentifier.OracleId key) {
                    return CLIENT.sendAsync(
                            HttpRequest.newBuilder()
                                    .uri(URI.create(SCRYFALL + "/cards/" + key + "/rulings"))
                                    .build(),
                            HttpResponse.BodyHandlers.ofString()
                    ).thenApply(response -> CardRulingsResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)).data());
                }
            }
    );

    @Override
    public LoadingCache<String, CompletableFuture<List<String>>> cardAutoCompletionsCache() {
        return cardAutoCompletionsCache;
    }

    private final LoadingCache<String, CompletableFuture<List<String>>> cardAutoCompletionsCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<List<String>> load(@NotNull String key) {
                    return CLIENT.sendAsync(
                            HttpRequest.newBuilder()
                                    .uri(URI.create(SCRYFALL + "/cards/autocomplete?q=" + URLEncoder.encode(key, StandardCharsets.UTF_8) + "&include_extras=true"))
                                    .build(),
                            HttpResponse.BodyHandlers.ofString()
                    ).thenApply(response -> CardAutoCompletionsResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)).data());
                }
            }
    );
}
