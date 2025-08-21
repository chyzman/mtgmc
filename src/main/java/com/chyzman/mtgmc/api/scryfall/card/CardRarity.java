package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardRarity implements StringIdentifiable {
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    MYTHIC("mythic"),
    SPECIAL("special"),
    BONUS("bonus");

    public static final Endec<CardRarity> ENDEC = StringIdentifiableEndecUtils.createEndec(CardRarity.class);
    private final String name;

    CardRarity(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
