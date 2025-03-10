package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardLegality implements StringIdentifiable {
    BANNED("banned"),
    LEGAL("legal"),
    NOT_LEGAL("not_legal"),
    RESTRICTED("restricted");

    public static final Endec<CardLegality> ENDEC = StringIdentifiableEndecUtils.createEndec(CardLegality.class);
    private final String name;

    CardLegality(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
