package com.chyzman.mtgmc.card.cache;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.CardCollection;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.network.C2S.RequestCard;
import com.chyzman.mtgmc.util.ImageUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.chyzman.mtgmc.MtgMc.SCRYFALL;
import static com.chyzman.mtgmc.network.MtgMcPackets.CHANNEL;

@Environment(EnvType.CLIENT)
public class ClientCardCache extends CardCache {
    public static final Map<MtgCard, CompletableFuture<Identifier>> IMAGE_CACHE = new HashMap<>();

    @Override
    public CompletableFuture<MtgCard> getCard(CardIdentifier identifier) {
        CompletableFuture<MtgCard> future = new CompletableFuture<>();
        if (CACHED.containsKey(identifier)) {
            future.complete(CACHED.get(identifier));
        } else if (QUEUE.containsKey(identifier)) {
            future = QUEUE.get(identifier);
        } else {
            CHANNEL.clientHandle().send(new RequestCard(identifier));
            QUEUE.put(identifier, future);
        }
        return future;
    }

    public CompletableFuture<Identifier> getImage(MtgCard card) {
        if (IMAGE_CACHE.containsKey(card)) return IMAGE_CACHE.get(card);
        var future = ImageUtils.getTexture(
                MtgMc.id(card.id().toString()),
                Path.of(MinecraftClient.getInstance().runDirectory + "/mtgCards/" + card.id() + ".png"),
                card.imageUris().png()
        );
        IMAGE_CACHE.put(card, future);
        return future;
    }
}
