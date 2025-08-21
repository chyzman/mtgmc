package com.chyzman.mtgmc.cache.api;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.card.MtgCard;
import com.chyzman.mtgmc.api.scryfall.error.ScryfallError;
import com.chyzman.mtgmc.api.scryfall.ruling.Ruling;
import com.chyzman.mtgmc.util.Procrastinator;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import io.wispforest.endec.format.gson.GsonDeserializer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.chyzman.mtgmc.MtgMc.GSON;

public abstract class MtgCache {
    protected static final Logger LOGGER = LogUtils.getLogger();

    public abstract LoadingCache<CardIdentifier, Procrastinator<MtgCard>> cardCache();

    public Procrastinator<@Nullable MtgCard> getCard(CardIdentifier identifier) {
        return cardCache().getUnchecked(identifier);
    }


    public abstract LoadingCache<CardIdentifier.OracleId, Procrastinator<List<Ruling>>> rulingCache();

    public Procrastinator<List<Ruling>> getRulings(@Nullable MtgCard card) {
        if (card == null || card.oracleId() == null) return Procrastinator.procrastinated(List.of());
        return rulingCache().getUnchecked(new CardIdentifier.OracleId(card.oracleId()));
    }


    public abstract LoadingCache<String, Procrastinator<List<String>>> cardAutoCompletionsCache();

    public Procrastinator<List<String>> getCardAutoCompletions(String query) {
        return cardAutoCompletionsCache().getUnchecked(query);
    }

    public Procrastinator<@Nullable MtgCard> getRandomCard(@Nullable String query) {
        return Procrastinator.supplyAsync(() -> {
                    var request = HttpRequest.newBuilder()
                            .uri(URI.create(MtgMc.SCRYFALL + "/cards/random" + (query == null || query.isBlank() ? "" : "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8))))
                            .build();
                    try {
                        return MtgMc.CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (Exception e) {
                        LOGGER.error("Failed to fetch random card", e);
                        throw new RuntimeException(e);
                    }
                })
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        LOGGER.error("Failed to fetch random card: {}", ScryfallError.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class)).details());
                        return null;
                    }
                    return MtgCard.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class));
                })
                .exceptionally(throwable -> {
                    LOGGER.error("Failed to fetch random card", throwable);
                    return null;
                });
    }

    public Procrastinator<@Nullable MtgCard> getRandomCard() {
        return getRandomCard(null);
    }
}
