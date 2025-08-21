package com.chyzman.mtgmc.api.scryfall.set;

import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record MtgSet(
        @Nullable String arenaCode,
        @NotNull String block,
        @NotNull String blockCode,
        int cardCount,
        @NotNull String code,
        boolean digital,
        boolean foilOnly,
        @NotNull String iconSvgUri,
        @NotNull String id,
        @NotNull String mtgoCode,
        @NotNull String name,
        boolean nonfoilOnly,
        @Nullable String parentSetCode,
        @NotNull String releasedAt,
        @NotNull String scryfallUri,
        @NotNull String searchUri,
        @NotNull MtgSetType setType,
        @Nullable Integer tcgplayerId,
        @NotNull String uri
) {
    public static final Endec<MtgSet> ENDEC = ExtraEndecs.of(
            Endec.STRING.optionalFieldOf("arena_code", MtgSet::arenaCode, (String) null),
            Endec.STRING.fieldOf("block", MtgSet::block),
            Endec.STRING.fieldOf("block_code", MtgSet::blockCode),
            Endec.INT.fieldOf("card_count", MtgSet::cardCount),
            Endec.STRING.fieldOf("code", MtgSet::code),
            Endec.BOOLEAN.fieldOf("digital", MtgSet::digital),
            Endec.BOOLEAN.fieldOf("foil_only", MtgSet::foilOnly),
            Endec.STRING.fieldOf("icon_svg_uri", MtgSet::iconSvgUri),
            Endec.STRING.fieldOf("id", MtgSet::id),
            Endec.STRING.fieldOf("mtgo_code", MtgSet::mtgoCode),
            Endec.STRING.fieldOf("name", MtgSet::name),
            Endec.BOOLEAN.fieldOf("nonfoil_only", MtgSet::nonfoilOnly),
            Endec.STRING.optionalFieldOf("parent_set_code", MtgSet::parentSetCode, (String) null),
            Endec.STRING.fieldOf("released_at", MtgSet::releasedAt),
            Endec.STRING.fieldOf("scryfall_uri", MtgSet::scryfallUri),
            Endec.STRING.fieldOf("search_uri", MtgSet::searchUri),
            MtgSetType.ENDEC.fieldOf("set_type", MtgSet::setType),
            Endec.INT.optionalFieldOf("tcgplayer_id", MtgSet::tcgplayerId, (Integer) null),
            Endec.STRING.fieldOf("uri", MtgSet::uri),
            MtgSet::new
    );
}
