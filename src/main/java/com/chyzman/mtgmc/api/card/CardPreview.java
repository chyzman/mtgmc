package com.chyzman.mtgmc.api.card;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record CardPreview(
        @Nullable String source,
        @Nullable String sourceUri,
        @Nullable String previewedAt
) {
    public static final Endec<CardPreview> ENDEC = StructEndecBuilder.of(
            Endec.STRING.optionalFieldOf("source", CardPreview::source, (String) null),
            Endec.STRING.optionalFieldOf("sourceUri",CardPreview::sourceUri, (String) null),
            Endec.STRING.optionalFieldOf("previewedAt",CardPreview::previewedAt, (String) null),
    CardPreview::new
            );
}
