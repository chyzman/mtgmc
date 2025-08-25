package com.chyzman.mtgmc.api.scryfall.deck.impl;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.deck.LoadedDeck;
import com.chyzman.mtgmc.api.scryfall.deck.api.UriDeckFormat;
import com.chyzman.mtgmc.util.Procrastinator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.impl.StructEndecBuilder;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Pattern;

import static com.chyzman.mtgmc.MtgMc.*;

public class AetherhubFormat extends UriDeckFormat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Pattern AETHERHUB_URL_PATTERN = Pattern.compile("(?:https?://)?aetherhub\\.com/Deck/([\\w_-]+)/?.*");

    @Override
    public boolean inputValid(URI input) {
        return AETHERHUB_URL_PATTERN.matcher(input.toString()).matches();
    }

    @Override
    public Procrastinator<LoadedDeck> load(String input) {
        var matcher = AETHERHUB_URL_PATTERN.matcher(input);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid Aetherhub URL: " + input);

        return Procrastinator.supplyAsync(
                () -> {
                    try {
                        var doc = Jsoup.connect("https://aetherhub.com/Deck/" + matcher.group(1) + "/").get();
                        var id = doc.select("[data-deckid]").attr("data-deckid");
                        var script = doc.selectFirst("script[type=\"application/ld+json\"]");
                        var name = "Aetherhub Deck";
                        if (script != null) {
                            String jsonContent = script.html();

                            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
                            JsonArray graphArray = root.getAsJsonArray("@graph");

                            for (int i = 0; i < graphArray.size(); i++) {
                                JsonObject item = graphArray.get(i).getAsJsonObject();
                                if (item.has("@type") && "WebPage".equals(item.get("@type").getAsString())) {
                                    name = item.get("name").getAsString();
                                }
                            }
                        } else {
                            LOGGER.warn("unable to find script tag for deck name, falling back to id");
                            var title = doc.selectFirst("meta[property=\"og:title\"]");
                            if (title != null) name = title.attr("content");
                        }
                        return new Pair<>(id, name);
                    } catch (IOException | NumberFormatException e) {
                        LOGGER.error("Failed to fetch deck ID and name from AetherHub", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchAetherhubDeckId")
            )
            .thenExcept(throwable -> LOGGER.error("Failed to fetch deck ID from Aetherhub", throwable))
            .thenApplyAsync(
                deckIdDeckNamePair -> {
                    var request = HttpRequest
                        .newBuilder()
                        .uri(URI.create("https://aetherhub.com/Deck/FetchMtgaDeckJson?deckId=" + deckIdDeckNamePair.getLeft() + "&langId=0&simple=False"))
                        .build();
                    try {
                        return new Pair<>(
                            CLIENT.send(request, HttpResponse.BodyHandlers.ofString()),
                            deckIdDeckNamePair.getRight()
                        );
                    } catch (IOException | InterruptedException e) {
                        LOGGER.error("Failed to fetch deck list from Aetherhub", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchAetherhubDeckList")
            )
            .thenExcept(throwable -> LOGGER.error("Failed to fetch deck list from Aetherhub", throwable))
            .thenCompose(responseDeckNamePair -> {
                var response = responseDeckNamePair.getLeft();
                var deckName = responseDeckNamePair.getRight();
                if (response.statusCode() != 200) {
                    LOGGER.error("Failed to fetch deck list from Aetherhub: {}", response.body());
                    throw new RuntimeException("Failed to fetch deck list from Aetherhub: " + response.statusCode());
                }
                try {
                    return parseDeckList(
                        deckName,
                        AetherhubResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class))
                    );
                } catch (Exception e) {
                    LOGGER.error("Failed to parse deck list from Aetherhub", e);
                    throw new RuntimeException(e);
                }
            });
    }

    private Procrastinator<LoadedDeck> parseDeckList(String name, AetherhubResponse response) {
        var sections = new ArrayList<LoadedDeck.Section>();
        String currentSectionName = null;
        List<LoadedDeck.DeckCard> currentSectionCards = new ArrayList<>();

        List<Procrastinator<?>> cardFetchers = new ArrayList<>();

        for (var card : response.cards) {
            if (card.quantity == null) {
                if (currentSectionName != null) sections.add(LoadedDeck.Section.guess(currentSectionName, currentSectionCards));
                currentSectionName = card.name;
                currentSectionCards = new ArrayList<>();
            } else if (currentSectionName != null) {
                var targetSection = currentSectionCards;
                cardFetchers.add(
                    SERVER_CACHE.getCard(new CardIdentifier.NameSet(card.name, card.set))
                        .thenExcept(throwable -> LOGGER.error("Failed to parse card from Aetherhub deck list", throwable))
                        .thenAccept(mtgCard -> {
                            if (mtgCard == null) {
                                LOGGER.warn("Failed to find card with name {} and set {}", card.name, card.set);
                            } else {
                                targetSection.add(new LoadedDeck.DeckCard(new CardIdentifier.ScryfallId(mtgCard.id()), card.quantity));
                            }
                        })
                );
            } else {
                LOGGER.warn("Found card '{}' outside of a section in Aetherhub deck list", card.name);
            }
        }

        if (currentSectionName != null) sections.add(LoadedDeck.Section.guess(currentSectionName, currentSectionCards));

        return Procrastinator.allOf(cardFetchers)
            .thenApply(ignored -> new LoadedDeck(name, sections));
    }

    private record AetherhubResponse(
        @NotNull List<Card> cards
    ) {
        public static final Endec<AetherhubResponse> ENDEC = StructEndecBuilder.of(
            Card.ENDEC.listOf().fieldOf("convertedDeck", AetherhubResponse::cards),
            AetherhubResponse::new
        );

        private record Card(
            int cardId,
            @Nullable Integer quantity,
            @NotNull String name,
            @NotNull String set,
            @Nullable String number
        ) {
            public static final Endec<Card> ENDEC = StructEndecBuilder.of(
                Endec.INT.fieldOf("cardId", Card::cardId),
                Endec.INT.optionalFieldOf("quantity", Card::quantity, (Integer) null),
                Endec.STRING.fieldOf("name", Card::name),
                Endec.STRING.fieldOf("set", Card::set),
                Endec.STRING.optionalFieldOf("number", Card::number, (String) null),
                Card::new
            );
        }
    }
}
