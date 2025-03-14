package com.chyzman.mtgmc.cache.impl;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.CardImageStatus;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.ruling.Ruling;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCard;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCardAutoCompletions;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestRulings;
import com.chyzman.mtgmc.util.ImageUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.chyzman.mtgmc.network.MtgMcPackets.CHANNEL;

@Environment(EnvType.CLIENT)
public class ClientMtgCache extends MtgCache {

    @Override
    public LoadingCache<CardIdentifier, CompletableFuture<MtgCard>> cardCache() {
        return cardCache;
    }

    private final LoadingCache<CardIdentifier, CompletableFuture<MtgCard>> cardCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<MtgCard> load(@NotNull CardIdentifier key) {
                    CHANNEL.clientHandle().send(new C2SRequestCard(key));
                    return new CompletableFuture<>();
                }
            }
    );

    @Override
    public LoadingCache<CardIdentifier.OracleId, CompletableFuture<List<Ruling>>> rulingCache() {
        return rulingCache;
    }

    private final LoadingCache<CardIdentifier.OracleId, CompletableFuture<List<Ruling>>> rulingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<List<Ruling>> load(@NotNull CardIdentifier.OracleId key) {
                    CHANNEL.clientHandle().send(new C2SRequestRulings(key));
                    return new CompletableFuture<>();
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
                public @NotNull CompletableFuture<List<String>> load(@NotNull String query) {
                    CHANNEL.clientHandle().send(new C2SRequestCardAutoCompletions(query));
                    return new CompletableFuture<>();
                }
            }
    );

    public CompletableFuture<Identifier> getImage(MtgCard card) {
        return cardImageCache.getUnchecked(card);
    }

    public final LoadingCache<MtgCard, CompletableFuture<@Nullable Identifier>> cardImageCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull CompletableFuture<Identifier> load(@NotNull MtgCard card) {
                    if (card.imageUris() == null || card.imageStatus().equals(CardImageStatus.MISSING)) return CompletableFuture.completedFuture(null);
                    return ImageUtils.getTexture(
                            MtgMc.id(card.id().toString()),
                            Path.of(MinecraftClient.getInstance().runDirectory + "/mtgCards/" + card.id() + ".png"),
                            card.imageUris().png()
                    );
                }
            }
    );
}
