package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardImageStatus implements StringIdentifiable {
    HIGH_RESOLUTION("highres_scan"),
    LOW_RESOLUTION("lowres"),
    MISSING("missing"),
    PLACEHOLDER("placeholder");

    public static final Endec<CardImageStatus> ENDEC = StringIdentifiableEndecUtils.createEndec(CardImageStatus.class);
    private final String name;

    CardImageStatus(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
