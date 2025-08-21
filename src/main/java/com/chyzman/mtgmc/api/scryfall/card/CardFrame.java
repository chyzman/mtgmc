package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardFrame implements StringIdentifiable {
    _1993("1993"),
    _1997("1997"),
    _2003("2003"),
    _2015("2015"),
    FUTURE("future");

    public static final Endec<CardFrame> ENDEC = StringIdentifiableEndecUtils.createEndec(CardFrame.class);
    private final String name;

    CardFrame(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
