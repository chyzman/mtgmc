package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardGames implements StringIdentifiable {
    ARENA("arena"),
    MTGO("mtgo"),
    PAPER("paper");

    public static final Endec<CardGames> ENDEC = StringIdentifiableEndecUtils.createEndec(CardGames.class);
    private final String name;

    CardGames(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
