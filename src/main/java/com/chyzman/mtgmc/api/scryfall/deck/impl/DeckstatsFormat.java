package com.chyzman.mtgmc.api.scryfall.deck.impl;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.deck.api.UriDeckFormat;
import com.chyzman.mtgmc.util.EndecUtil;
import com.chyzman.mtgmc.util.Procrastinator;
import com.mojang.logging.LogUtils;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.chyzman.mtgmc.MtgMc.CLIENT;

public class DeckstatsFormat extends UriDeckFormat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Pattern DECKSTATS_URL_PATTERN = Pattern.compile("(?:https?://)?deckstats\\.net/decks/(\\d+)/(\\d+)-.*/?.*");

    @Override
    public boolean inputValid(URI input) {
        return DECKSTATS_URL_PATTERN.matcher(input.toString()).matches();
    }

    @Override
    public Procrastinator<List<CardIdentifier.ScryfallId>> load(String input) {
        var matcher = DECKSTATS_URL_PATTERN.matcher(input);
        if (!matcher.matches()) return Procrastinator.procrastinated(List.of());
        var ownerId = matcher.group(1);
        var deckId = matcher.group(2);
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create("https://deckstats.net/api.php?action=get_deck&id_type=saved&owner_id=" + ownerId + "&id=" + deckId + "&response_type=json"))
                .build();

        return Procrastinator.supplyAsync(() -> {
                    try {
                        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (Exception e) {
                        LOGGER.error("Failed to fetch deck list from Deckstats", e);
                        throw new RuntimeException(e);
                    }
                }, Util.getDownloadWorkerExecutor().named("fetchDeckstatsDeckList"))
                .thenExcept(throwable -> LOGGER.error("Failed to fetch deck list from Deckstats", throwable))
                .thenCompose(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Failed to fetch deck list from Deckstats: " + response.statusCode());
                    }
                    LOGGER.info("Deckstats response: {}", response.body());

                    return Procrastinator.procrastinated(List.of());

//                    return CompletableFuture.completedFuture(parseDeckList(ArchidektResponse.ENDEC.decodeFully(GsonDeserializer::of, GSON.fromJson(response.body(), JsonElement.class))));
                });
    }

    private List<CardIdentifier.ScryfallId> parseDeckList(DeckstatsResponse response) {
        var categories = response.categories().stream()
                .filter(DeckstatsResponse.Category::includedInDeck)
                .map(DeckstatsResponse.Category::name)
                .collect(Collectors.toSet());

        return response.cards.stream()
                .filter(card -> card.categories().stream().anyMatch(categories::contains))
                .flatMap(card -> Collections.nCopies(card.quantity(), new CardIdentifier.ScryfallId(card.uid())).stream())
                .toList();
    }

    private record DeckstatsResponse(
            @NotNull List<Card> cards,
            @NotNull List<Category> categories
    ) {
        public static final Endec<DeckstatsResponse> ENDEC = StructEndecBuilder.of(
                Card.ENDEC.listOf().fieldOf("cards", DeckstatsResponse::cards),
                Category.ENDEC.listOf().fieldOf("categories", DeckstatsResponse::categories),
                DeckstatsResponse::new
        );

        public record Category(
                @NotNull String name,
                boolean includedInDeck
        ) {
            public static final Endec<Category> ENDEC = StructEndecBuilder.of(
                    Endec.STRING.fieldOf("name", Category::name),
                    Endec.BOOLEAN.fieldOf("includedInDeck", Category::includedInDeck),
                    Category::new
            );
        }

        public record Card(
                @NotNull UUID uid,
                int quantity,
                @NotNull Set<String> categories
        ) {
            public static final Endec<Card> ENDEC = StructEndecBuilder.of(
                    EndecUtil.structifyEndec("uid", BuiltInEndecs.UUID).fieldOf("card", Card::uid),
                    Endec.INT.fieldOf("quantity", Card::quantity),
                    Endec.STRING.setOf().optionalFieldOf("categories", Card::categories, HashSet::new),
                    Card::new
            );
        }
    }
}
