package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardComponent implements StringIdentifiable {
    COMBO_PIECE("combo_piece"),
    MELD_PART("meld_part"),
    MELD_RESULT("meld_result"),
    TOKEN("token");

    public static final Endec<CardComponent> ENDEC = StringIdentifiableEndecUtils.createEndec(CardComponent.class);
    private final String name;

    CardComponent(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
