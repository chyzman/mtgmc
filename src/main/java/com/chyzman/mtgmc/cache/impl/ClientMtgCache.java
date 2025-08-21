package com.chyzman.mtgmc.cache.impl;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.card.CardImageStatus;
import com.chyzman.mtgmc.api.scryfall.card.MtgCard;
import com.chyzman.mtgmc.api.scryfall.ruling.Ruling;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCard;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestCardAutoCompletions;
import com.chyzman.mtgmc.network.minecraft.C2S.C2SRequestRulings;
import com.chyzman.mtgmc.util.ImageUtils;
import com.chyzman.mtgmc.util.Procrastinator;
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
import java.util.List;

import static com.chyzman.mtgmc.network.MtgMcPackets.CHANNEL;

@Environment(EnvType.CLIENT)
public class ClientMtgCache extends MtgCache {

    @Override
    public LoadingCache<CardIdentifier, Procrastinator<MtgCard>> cardCache() {
        return cardCache;
    }

    private final LoadingCache<CardIdentifier, Procrastinator<MtgCard>> cardCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<MtgCard> load(@NotNull CardIdentifier key) {
                    CHANNEL.clientHandle().send(new C2SRequestCard(key));
                    return new Procrastinator<>();
                }
            }
    );

    @Override
    public LoadingCache<CardIdentifier.OracleId, Procrastinator<List<Ruling>>> rulingCache() {
        return rulingCache;
    }

    private final LoadingCache<CardIdentifier.OracleId, Procrastinator<List<Ruling>>> rulingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<List<Ruling>> load(@NotNull CardIdentifier.OracleId key) {
                    CHANNEL.clientHandle().send(new C2SRequestRulings(key));
                    return new Procrastinator<>();
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
                public @NotNull Procrastinator<List<String>> load(@NotNull String query) {
                    CHANNEL.clientHandle().send(new C2SRequestCardAutoCompletions(query));
                    return new Procrastinator<>();
                }
            }
    );

    public Procrastinator<Identifier> getImage(MtgCard card) {
        return cardImageCache.getUnchecked(card);
    }

    public final LoadingCache<MtgCard, Procrastinator<@Nullable Identifier>> cardImageCache = CacheBuilder.newBuilder().build(
            new CacheLoader<>() {
                @Override
                public @NotNull Procrastinator<Identifier> load(@NotNull MtgCard card) {
                    if (card.imageUris() == null || card.imageStatus().equals(CardImageStatus.MISSING)) return Procrastinator.procrastinated(null);
                    return ImageUtils.getTexture(
                            MtgMc.id(card.id().toString()),
                            Path.of(MinecraftClient.getInstance().runDirectory + "/mtgCards/" + card.id() + ".png"),
                            card.imageUris().png()
                    );
                }
            }
    );
}
