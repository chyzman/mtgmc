package com.chyzman.mtgmc.card.api;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record CardPreview(
        @NotNull String source,
        @NotNull String sourceUri,
        @NotNull Date previewedAt
        ) {
    public static final Endec<CardPreview> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("source", CardPreview::source),
            Endec.STRING.fieldOf("sourceUri", CardPreview::sourceUri),
            BuiltInEndecs.DATE.fieldOf("previewedAt", CardPreview::previewedAt),
            CardPreview::new
    );
}
