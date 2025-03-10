package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardSecurityStamp implements StringIdentifiable {
    ACORN("acorn"),
    ARENA("arena"),
    CIRCLE("circle"),
    HEART("heart"),
    OVAL("oval"),
    TRAIANGLE("triangle");

    public static final Endec<CardSecurityStamp> ENDEC = StringIdentifiableEndecUtils.createEndec(CardSecurityStamp.class);
    private final String name;

    CardSecurityStamp(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
