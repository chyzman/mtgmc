package com.chyzman.mtgmc.api.scryfall.ruling;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

public record Ruling(
        @NotNull String oracleId,
        @NotNull RulingSource source,
        @NotNull String publishedAt,
        @NotNull String comment
) {
    public static final Endec<Ruling> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("oracle_id", Ruling::oracleId),
            RulingSource.ENDEC.fieldOf("source", Ruling::source),
            Endec.STRING.fieldOf("published_at", Ruling::publishedAt),
            Endec.STRING.fieldOf("comment", Ruling::comment),
            Ruling::new
    );
}
