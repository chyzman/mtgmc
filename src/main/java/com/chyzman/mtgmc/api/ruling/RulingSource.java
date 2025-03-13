package com.chyzman.mtgmc.api.ruling;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum RulingSource implements StringIdentifiable {
    WIZARDS_OF_THE_COAST("wotc"),
    SCRYFALL("scryfall");

    public static final Endec<RulingSource> ENDEC = StringIdentifiableEndecUtils.createEndec(RulingSource.class);
    private final String name;

    RulingSource(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
