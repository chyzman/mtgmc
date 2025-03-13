package com.chyzman.mtgmc.cache.api;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.ruling.Ruling;
import com.chyzman.mtgmc.network.http.response.CardAutoCompletionsResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class MtgCache {
    public static final String SCRYFALL = "https://api.scryfall.com";

    public static final Gson GSON = new Gson();

    protected static final HttpClient CLIENT = HttpClient.newHttpClient();


    public abstract LoadingCache<CardIdentifier, CompletableFuture<MtgCard>> cardCache();

    public CompletableFuture<@Nullable MtgCard> getCard(CardIdentifier identifier) {
        return cardCache().getUnchecked(identifier);
    }


    public abstract LoadingCache<CardIdentifier.OracleId, CompletableFuture<List<Ruling>>> rulingCache();

    public CompletableFuture<List<Ruling>> getRulings(@Nullable MtgCard card) {
        if (card == null || card.oracleId() == null) return CompletableFuture.completedFuture(List.of());
        return rulingCache().getUnchecked(new CardIdentifier.OracleId(card.oracleId()));
    }


    public abstract LoadingCache<String, CompletableFuture<List<String>>> cardAutoCompletionsCache();

    public CompletableFuture<List<String>> getCardAutoCompletions(String query) {
        return cardAutoCompletionsCache().getUnchecked(query);
    }

    public CompletableFuture<MtgCard> getRandomCard(@Nullable String query) {
        return CLIENT.sendAsync(
                HttpRequest.newBuilder()
                        .uri(URI.create(SCRYFALL + "/cards/random" + (query == null || query.isBlank() ? "" : "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8))))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        ).thenApply(response -> MtgCard.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)));
    }

    public CompletableFuture<MtgCard> getRandomCard() {
        return getRandomCard(null);
    }
}
