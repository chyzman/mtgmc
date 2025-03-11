package com.chyzman.mtgmc.api.set;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum MtgSetType implements StringIdentifiable {
    ALCHEMY("alchemy"),
    ARCHENEMY("archenemy"),
    ARSENAL("arsenal"),
    BOX("box"),
    COMMANDER("commander"),
    CORE("core"),
    DRAFT_INNOVATION("draft_innovation"),
    DUEL_DECK("duel_deck"),
    EXPANSION("expansion"),
    FROM_THE_VAULT("from_the_vault"),
    FUNNY("funny"),
    MASTERPIECE("masterpiece"),
    MASTERS("masters"),
    MEMORABILIA("memorabilia"),
    MINIGAME("minigame"),
    PLANECHASE("planechase"),
    PREMIUM_DECK("premium_deck"),
    PROMO("promo"),
    SPELLBOOK("spellbook"),
    STARTER("starter"),
    TOKEN("token"),
    TREASURE_CHEST("treasure_chest"),
    VANGUARD("vanguard");

    public static final Endec<MtgSetType> ENDEC = StringIdentifiableEndecUtils.createEndec(MtgSetType.class);
    private final String name;

    MtgSetType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
