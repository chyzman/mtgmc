package com.chyzman.mtgmc.api.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardFinish implements StringIdentifiable {
    FOIL("foil"),
    NON_FOIL("nonfoil"),
    ETCHED("etched");

    public static final Endec<CardFinish> ENDEC = StringIdentifiableEndecUtils.createEndec(CardFinish.class);
    private final String name;

    CardFinish(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
