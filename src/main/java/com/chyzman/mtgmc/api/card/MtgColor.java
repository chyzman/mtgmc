package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum MtgColor implements StringIdentifiable {
    WHITE("W"),
    BLUE("U"),
    BLACK("B"),
    RED("R"),
    GREEN("G");

    public static final Endec<MtgColor> ENDEC = StringIdentifiableEndecUtils.createEndec(MtgColor.class);
    private final String name;

    MtgColor(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
