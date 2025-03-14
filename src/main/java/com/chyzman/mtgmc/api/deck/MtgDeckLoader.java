package com.chyzman.mtgmc.api.deck;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.deck.api.ArchidektDeck;
import com.google.gson.JsonElement;
import io.wispforest.endec.format.gson.GsonDeserializer;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.chyzman.mtgmc.MtgMc.CLIENT;
import static com.chyzman.mtgmc.MtgMc.GSON;

public class MtgDeckLoader {

    public static CompletableFuture<List<CardIdentifier.ScryfallId>> fetchDeckList(URI uri) {
        return fetchArcidektDeckList(uri);
    }

    public static CompletableFuture<List<CardIdentifier.ScryfallId>> fetchArcidektDeckList(URI uri) {
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://archidekt.com/api/decks/" + uri.getPath().split("/")[2] + "/#"))
                .build();

        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                               var deck = ArchidektDeck.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class));
                               var cards = new ArrayList<CardIdentifier.ScryfallId>();
                               for (var archidektCard : deck.cards()) {
                                   var card = archidektCard.innerCard();
                                   var scryfallId = new CardIdentifier.ScryfallId(card.uid());
                                   for (int i = 0; i < archidektCard.quantity(); i++) {
                                       cards.add(scryfallId);
                                   }
                               }
                                 return cards;
                           }
                );
    }
}
