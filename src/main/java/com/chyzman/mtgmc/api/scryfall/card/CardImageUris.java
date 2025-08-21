package com.chyzman.mtgmc.api.scryfall.card;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

public record CardImageUris(
        @NotNull String png,
        @NotNull String borderCrop,
        @NotNull String artCrop,
        @NotNull String large,
        @NotNull String normal,
        @NotNull String small
) {
    public static final Endec<CardImageUris> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("png", CardImageUris::png),
            Endec.STRING.fieldOf("border_crop", CardImageUris::borderCrop),
            Endec.STRING.fieldOf("art_crop", CardImageUris::artCrop),
            Endec.STRING.fieldOf("large", CardImageUris::large),
            Endec.STRING.fieldOf("normal", CardImageUris::normal),
            Endec.STRING.fieldOf("small", CardImageUris::small),
            CardImageUris::new
    );
}
