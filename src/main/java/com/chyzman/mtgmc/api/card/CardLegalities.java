package com.chyzman.mtgmc.api.card;

import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import org.jetbrains.annotations.NotNull;

public record CardLegalities(
        @NotNull CardLegality standard,
        @NotNull CardLegality future,
        @NotNull CardLegality historic,
        @NotNull CardLegality timeless,
        @NotNull CardLegality gladiator,
        @NotNull CardLegality pioneer,
        @NotNull CardLegality explorer,
        @NotNull CardLegality modern,
        @NotNull CardLegality legacy,
        @NotNull CardLegality pauper,
        @NotNull CardLegality vintage,
        @NotNull CardLegality penny,
        @NotNull CardLegality commander,
        @NotNull CardLegality oathBreaker,
        @NotNull CardLegality standardBrawl,
        @NotNull CardLegality brawl,
        @NotNull CardLegality alchemy,
        @NotNull CardLegality pauperCommander,
        @NotNull CardLegality duel,
        @NotNull CardLegality oldSchool,
        @NotNull CardLegality preModern,
        @NotNull CardLegality predh
        ) {
    public static final Endec<CardLegalities> ENDEC = ExtraEndecs.of(
            CardLegality.ENDEC.fieldOf("standard", CardLegalities::standard),
            CardLegality.ENDEC.fieldOf("future", CardLegalities::future),
            CardLegality.ENDEC.fieldOf("historic", CardLegalities::historic),
            CardLegality.ENDEC.fieldOf("timeless", CardLegalities::timeless),
            CardLegality.ENDEC.fieldOf("gladiator", CardLegalities::gladiator),
            CardLegality.ENDEC.fieldOf("pioneer", CardLegalities::pioneer),
            CardLegality.ENDEC.fieldOf("explorer", CardLegalities::explorer),
            CardLegality.ENDEC.fieldOf("modern", CardLegalities::modern),
            CardLegality.ENDEC.fieldOf("legacy", CardLegalities::legacy),
            CardLegality.ENDEC.fieldOf("pauper", CardLegalities::pauper),
            CardLegality.ENDEC.fieldOf("vintage", CardLegalities::vintage),
            CardLegality.ENDEC.fieldOf("penny", CardLegalities::penny),
            CardLegality.ENDEC.fieldOf("commander", CardLegalities::commander),
            CardLegality.ENDEC.fieldOf("oathbreaker", CardLegalities::oathBreaker),
            CardLegality.ENDEC.fieldOf("standardbrawl", CardLegalities::standardBrawl),
            CardLegality.ENDEC.fieldOf("brawl", CardLegalities::brawl),
            CardLegality.ENDEC.fieldOf("alchemy",CardLegalities::alchemy),
            CardLegality.ENDEC.fieldOf("paupercommander",CardLegalities::pauperCommander),
            CardLegality.ENDEC.fieldOf("duel",CardLegalities::duel),
            CardLegality.ENDEC.fieldOf("oldschool",CardLegalities::oldSchool),
            CardLegality.ENDEC.fieldOf("premodern",CardLegalities::preModern),
            CardLegality.ENDEC.fieldOf("predh",CardLegalities::predh),
            CardLegalities::new
    );
}
