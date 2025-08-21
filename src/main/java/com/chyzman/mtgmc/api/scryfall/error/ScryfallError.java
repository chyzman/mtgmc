package com.chyzman.mtgmc.api.scryfall.error;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record ScryfallError(
        int status,
        @NotNull String code,
        @NotNull String details,
        @Nullable String type,
        @NotNull List<String> warnings
) {
    public static final Endec<ScryfallError> ENDEC = StructEndecBuilder.of(
            Endec.INT.fieldOf("status", ScryfallError::status),
            Endec.STRING.fieldOf("code", ScryfallError::code),
            Endec.STRING.fieldOf("details", ScryfallError::details),
            Endec.STRING.optionalFieldOf("type", ScryfallError::type, (String) null),
            Endec.STRING.listOf().optionalFieldOf("warnings", ScryfallError::warnings, ArrayList::new),
            ScryfallError::new
    );
}
