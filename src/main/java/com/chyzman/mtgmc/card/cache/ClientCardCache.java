package com.chyzman.mtgmc.card.cache;

import com.chyzman.mtgmc.api.CardCollection;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.network.C2S.RequestCard;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.chyzman.mtgmc.MtgMc.SCRYFALL;
import static com.chyzman.mtgmc.network.MtgMcPackets.CHANNEL;

@Environment(EnvType.CLIENT)
public class ClientCardCache extends CardCache {

    @Override
    public CompletableFuture<MtgCard> getCard(CardIdentifier identifier) {
        CompletableFuture<MtgCard> future = new CompletableFuture<>();
        if (CACHED.containsKey(identifier)) {
            future.complete(CACHED.get(identifier));
        } else {
            CHANNEL.clientHandle().send(new RequestCard(identifier));
            QUEUE.put(identifier, future::complete);
        }
        return future;
    }
}
