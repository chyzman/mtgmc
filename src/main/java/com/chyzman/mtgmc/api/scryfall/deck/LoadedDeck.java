package com.chyzman.mtgmc.api.scryfall.deck;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;

public record LoadedDeck(
    String name,
    List<Section> sections
) {
    public static final Endec<LoadedDeck> ENDEC = StructEndecBuilder.of(
        Endec.STRING.fieldOf("name", LoadedDeck::name),
        Section.ENDEC.listOf().fieldOf("sections", LoadedDeck::sections),
        LoadedDeck::new
    );

    public record Section(
        String name,
        List<DeckCard> cards,
        boolean enabled,
        boolean auxiliary
    ) {

        public static Section guess(String name, List<DeckCard> cards) {
            return new Section(
                name,
                cards,
                guessIfEnabled(name),
                guessIfAuxiliary(name)
            );
        }

        public static final Endec<Section> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("name", Section::name),
            DeckCard.ENDEC.listOf().fieldOf("cards", Section::cards),
            Endec.BOOLEAN.fieldOf("enabled", Section::enabled),
            Endec.BOOLEAN.fieldOf("auxiliary", Section::auxiliary),
            Section::new
        );

        private static final List<String> ENABLED_KEYWORDS = List.of(
            "deck",
            "main"
        );

        private static final List<String> DISABLED_KEYWORDS = List.of(
            "maybe",
            "side"
        );

        public static boolean guessIfEnabled(String sectionName) {
            var lower = sectionName.toLowerCase();
            for (var keyword : DISABLED_KEYWORDS) if (lower.contains(keyword)) return false;
            for (var keyword : ENABLED_KEYWORDS) if (lower.contains(keyword)) return true;
            if (guessIfAuxiliary(sectionName)) return true;
            return false;
        }

        private static final List<String> AUXILIARY_KEYWORDS = List.of(
            "command",
            "companion",
            "partner",
            "oath",
            "signature"
        );

        public static boolean guessIfAuxiliary(String sectionName) {
            var lower = sectionName.toLowerCase();
            for (var keyword : AUXILIARY_KEYWORDS) if (lower.contains(keyword)) return true;
            return false;
        }
    }

    public record DeckCard(
        CardIdentifier.ScryfallId identifier,
        int quantity
    ) {
        public static final Endec<DeckCard> ENDEC = StructEndecBuilder.of(
            CardIdentifier.ScryfallId.ENDEC.fieldOf("identifier", DeckCard::identifier),
            Endec.INT.fieldOf("quantity", DeckCard::quantity),
            DeckCard::new
        );
    }
}
