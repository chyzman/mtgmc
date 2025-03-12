package com.chyzman.mtgmc.card.cache;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CardCache {
    public static final Gson GSON = new Gson();

    protected final Map<CardIdentifier, MtgCard> CACHED = new ConcurrentHashMap<>();

    protected final Map<CardIdentifier, CompletableFuture<@Nullable MtgCard>> QUEUE = new ConcurrentHashMap<>();

    public abstract CompletableFuture<@Nullable MtgCard> getCard(CardIdentifier identifier);

    public void addToCache(CardIdentifier identifier, MtgCard card) {
        CACHED.put(identifier, card);
        for (Class<?> subclass : CardIdentifier.class.getPermittedSubclasses()) {
            if (!subclass.isInstance(identifier)) CACHED.put(identifier, card);
        }
        QUEUE.remove(identifier).complete(card);
    }
}
