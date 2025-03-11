package com.chyzman.mtgmc.api.card;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

public record MtgRelatedCard(
        @NotNull CardComponent component,
        @NotNull String id,
        @NotNull String name,
        @NotNull String typeLine,
        @NotNull String uri
) {
    public static final Endec<MtgRelatedCard> ENDEC = StructEndecBuilder.of(
            CardComponent.ENDEC.fieldOf("component", MtgRelatedCard::component),
            Endec.STRING.fieldOf("id", MtgRelatedCard::id),
            Endec.STRING.fieldOf("name", MtgRelatedCard::name),
            Endec.STRING.fieldOf("type_line", MtgRelatedCard::typeLine),
            Endec.STRING.fieldOf("uri", MtgRelatedCard::uri),
            MtgRelatedCard::new
    );
}
