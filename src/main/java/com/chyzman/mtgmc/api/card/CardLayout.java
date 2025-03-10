package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardLayout implements StringIdentifiable {
    ADVENTURE("adventure"),
    ART_SERIES("art_series"),
    AUGMENT("augment"),
    BATTLE("battle"),
    CASE("case"),
    CLASS("class"),
    DOUBLE_FACED_TOKEN("double_faced_token"),
    EMBLEM("emblem"),
    FLIP("flip"),
    HOST("host"),
    LEVELER("leveler"),
    MELD("meld"),
    MODAL_DFC("modal_dfc"),
    MUTATE("mutate"),
    NORMAL("normal"),
    PLANAR("planar"),
    PROTOTYPE("prototype"),
    REVERSIBLE("reversible_card"),
    SAGA("saga"),
    SCHEME("scheme"),
    SPLIT("split"),
    TOKEN("token"),
    TRANSFORM("transform"),
    VANGUARD("vanguard");

    public static final Endec<CardLayout> ENDEC = StringIdentifiableEndecUtils.createEndec(CardLayout.class);
    private final String name;

    CardLayout(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
