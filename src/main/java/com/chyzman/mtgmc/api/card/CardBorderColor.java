package com.chyzman.mtgmc.api.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardBorderColor implements StringIdentifiable {
    BLACK("black"),
    BORDERLESS("borderless"),
    GOLD("gold"),
    SILVER("silver"),
    WHITE("white");

    public static final Endec<CardBorderColor> ENDEC = StringIdentifiableEndecUtils.createEndec(CardBorderColor.class);
    private final String name;

    CardBorderColor(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
