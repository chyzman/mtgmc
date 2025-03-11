package com.chyzman.mtgmc.card.cache;

import com.chyzman.mtgmc.api.CardCollection;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonSerializer;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.chyzman.mtgmc.MtgMc.SCRYFALL;

public class ServerCardCache extends CardCache {

    @Override
    public CompletableFuture<MtgCard> getCard(CardIdentifier identifier) {
        var future = new CompletableFuture<MtgCard>();
        if (CACHED.containsKey(identifier)) {
            future.complete(CACHED.get(identifier));
        } else {
            QUEUE.put(identifier, future::complete);
        }
        return future;
    }

    public void updateQueue() {
        if (QUEUE.isEmpty()) return;
        var targets = QUEUE.entries().stream().limit(75).toList();
        var url = HttpRequest.newBuilder()
                .uri(URI.create(SCRYFALL + "/cards/collection"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(CardCollection.Request.ENDEC.encodeFully(GsonSerializer::of, new CardCollection.Request(targets.stream().map(Map.Entry::getKey).toList())).toString()))
                .build();
        var client = HttpClient.newBuilder()
                .build()
                .sendAsync(url, HttpResponse.BodyHandlers.ofString());
        client.thenAccept(response -> {
            try {
                var cards = CardCollection.Response.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class));
                var index = 0;
                for (var target : targets) {
                    var card = cards.data().size() > index ? cards.data().get(index) : null;
                    addToCache(target.getKey(), card);
                    if (card != null) index++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
