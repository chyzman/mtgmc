package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.api.scryfall.symbology.MtgColor;
import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CardFace(
        @Nullable String artist,
        @Nullable String artistId,
        @Nullable Integer cmc,
        @NotNull List<MtgColor> colorIndicators,
        @NotNull List<MtgColor> colors,
        @Nullable String defense,
        @Nullable String flavorText,
        @Nullable String illustrationId,
        @Nullable CardImageUris imageUris,
        @Nullable String layout,
        @Nullable String loyalty,
        @NotNull String manaCost,
        @NotNull String name,
        @Nullable String oracleId,
        @Nullable String oracleText,
        @Nullable String power,
        @Nullable String printedName,
        @Nullable String printedText,
        @Nullable String printedTypeLine,
        @Nullable String toughness,
        @Nullable String typeLine,
        @Nullable String watermark
        ) {
    public static final Endec<CardFace> ENDEC = ExtraEndecs.of(
            Endec.STRING.optionalFieldOf("artist", CardFace::artist, (String) null),
            Endec.STRING.optionalFieldOf("artist_id", CardFace::artistId, (String) null),
            Endec.INT.optionalFieldOf("cmc", CardFace::cmc, (Integer) null),
            MtgColor.ENDEC.listOf().optionalFieldOf("color_indicator", CardFace::colorIndicators, (List<MtgColor>) null),
            MtgColor.ENDEC.listOf().optionalFieldOf("colors", CardFace::colors, (List<MtgColor>) null),
            Endec.STRING.optionalFieldOf("defense", CardFace::defense, (String) null),
            Endec.STRING.optionalFieldOf("flavor_text", CardFace::flavorText, (String) null),
            Endec.STRING.optionalFieldOf("illustration_id", CardFace::illustrationId, (String) null),
            CardImageUris.ENDEC.optionalFieldOf("image_uris", CardFace::imageUris, (CardImageUris) null),
            Endec.STRING.optionalFieldOf("layout", CardFace::layout, (String) null),
            Endec.STRING.optionalFieldOf("loyalty", CardFace::loyalty, (String) null),
            Endec.STRING.fieldOf("mana_cost", CardFace::manaCost),
            Endec.STRING.fieldOf("name", CardFace::name),
            Endec.STRING.optionalFieldOf("oracle_id", CardFace::oracleId, (String) null),
            Endec.STRING.optionalFieldOf("oracle_text", CardFace::oracleText, (String) null),
            Endec.STRING.optionalFieldOf("power", CardFace::power, (String) null),
            Endec.STRING.optionalFieldOf("printed_name", CardFace::printedName, (String) null),
            Endec.STRING.optionalFieldOf("printed_text", CardFace::printedText, (String) null),
            Endec.STRING.optionalFieldOf("printed_type_line", CardFace::printedTypeLine, (String) null),
            Endec.STRING.optionalFieldOf("toughness", CardFace::toughness, (String) null),
            Endec.STRING.optionalFieldOf("type_line", CardFace::typeLine, (String) null),
            Endec.STRING.optionalFieldOf("watermark", CardFace::watermark, (String) null),
            CardFace::new
    );
}
