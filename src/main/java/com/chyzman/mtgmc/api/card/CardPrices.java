package com.chyzman.mtgmc.card.api;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.Nullable;

public record CardPrices(
        @Nullable String usd,
        @Nullable String usdFoil,
        @Nullable String usdEtched,
        @Nullable String eur,
        @Nullable String eurFoil,
        @Nullable String eurEtched,
        @Nullable String tix
) {
    public static final Endec<CardPrices> ENDEC = StructEndecBuilder.of(
            Endec.STRING.optionalFieldOf("usd", CardPrices::usd, (String) null),
            Endec.STRING.optionalFieldOf("usdFoil", CardPrices::usdFoil, (String) null),
            Endec.STRING.optionalFieldOf("usdEtched", CardPrices::usdEtched, (String) null),
            Endec.STRING.optionalFieldOf("eur", CardPrices::eur, (String) null),
            Endec.STRING.optionalFieldOf("eurFoil", CardPrices::eurFoil, (String) null),
            Endec.STRING.optionalFieldOf("eurEtched", CardPrices::eurEtched, (String) null),
            Endec.STRING.optionalFieldOf("tix", CardPrices::tix, (String) null),
            CardPrices::new
    );
}
