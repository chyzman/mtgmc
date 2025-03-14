package com.chyzman.mtgmc.api.deck.api;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public record ArchidektDeck(
        @NotNull List<ArchidektCard> cards
) {
    public static final Endec<ArchidektDeck> ENDEC = StructEndecBuilder.of(
            ArchidektCard.ENDEC.listOf().fieldOf("cards", ArchidektDeck::cards),
            ArchidektDeck::new
    );

    public record ArchidektCard(
            @NotNull InnerCard innerCard,
            int quantity
    ) {
        public static final Endec<ArchidektCard> ENDEC = StructEndecBuilder.of(
                InnerCard.ENDEC.fieldOf("card", ArchidektCard::innerCard),
                Endec.INT.fieldOf("quantity", ArchidektCard::quantity),
                ArchidektCard::new
        );

        public record InnerCard(
                @NotNull UUID uid
        ) {
            public static final Endec<InnerCard> ENDEC = StructEndecBuilder.of(
                    BuiltInEndecs.UUID.fieldOf("uid", InnerCard::uid),
                    InnerCard::new
            );
        }
    }
}
