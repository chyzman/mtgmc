package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

//TODO fix or remove this cuz its outdated
public enum CardFrameEffect implements StringIdentifiable {
    COLOR_SHIFTED("colorshifted"),
    COMPANION("companion"),
    COMPASS_LAND_DFC("compasslanddfc"),
    CONVERT_DFC("convertdfc"),
    DEVOID("devoid"),
    DRAFT("draft"),
    ETCHED("etched"),
    EXTENDED_ART("extendedart"),
    FAN_DFC("fandfc"),
    INVERTED("inverted"),
    LEGENDARY("legendary"),
    LESSON("lesson"),
    MIRACLE("miracle"),
    MOON_ELDRAZI_DFC("mooneldrazidfc"),
    NYXTOUCHED("nyxtouched"),
    ORIGIN_PW_DFC("originpwdfc"),
    SHATTERED_GLASS("shatteredglass"),
    SHOWCASE("showcase"),
    SNOW("snow"),
    SPREED("spree"),
    SUN_MOON_DFC("sunmoondfc"),
    TOMBSTONE("tombstone"),
    UPSIDE_DOWN_DFC("upsidedown"),
    WAXING_AND_WANING_MOON_DFC("waxingandwaningmoondfc");

    public static final Endec<CardFrameEffect> ENDEC = StringIdentifiableEndecUtils.createEndec(CardFrameEffect.class);
    private final String name;

    CardFrameEffect(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
