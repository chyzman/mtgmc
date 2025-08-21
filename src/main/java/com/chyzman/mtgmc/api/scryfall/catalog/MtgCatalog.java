package com.chyzman.mtgmc.api.scryfall.catalog;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MtgCatalog(
        @NotNull String uri,
        int totalValues,
        @NotNull List<String> data
) {
    public static final Endec<MtgCatalog> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("uri", MtgCatalog::uri),
            Endec.INT.fieldOf("total_values", MtgCatalog::totalValues),
            Endec.STRING.listOf().fieldOf("data", MtgCatalog::data),
            MtgCatalog::new
    );
}
