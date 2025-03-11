package com.chyzman.mtgmc.api.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardPromoType implements StringIdentifiable {
    ARENA_LEAGUE("arenaleague"),
    BUY_A_BOX("buyabox"),
    CONVENTION("convention"),
    DATE_STAMPED("datestamped"),
    DRAFT_WEEKEND("draftweekend"),
    DUELS("duels"),
    EVENT("event"),
    FNM("fnm"),
    GAME_DAY("gameday"),
    GATEWAY("gateway"),
    GIFT_BOX("giftbox"),
    IN_STORE("instore"),
    INTRO_PACK("intropack"),
    JUDGE_GIFT("judgegift"),
    LEAGUE("league"),
    OPEN_HOUSE("openhouse"),
    OURNEY("ourney"),
    PLANESWALKER_DECK("planeswalkerdeck"),
    PLAYER_REWARDS("playerrewards"),
    PREMIERE_SHOP("premiereshop"),
    PRERELEASE("prerelease"),
    RELEASE("release"),
    SET_PROMOTION("setpromo"),
    STARTED_DECK("starteddeck"),
    WIZARDS_PLAY_NETWORK("wizardsplaynetwork");

    public static final Endec<CardPromoType> ENDEC = StringIdentifiableEndecUtils.createEndec(CardPromoType.class);
    private final String name;

    CardPromoType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
