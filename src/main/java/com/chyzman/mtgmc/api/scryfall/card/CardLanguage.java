package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.util.StringIdentifiableEndecUtils;
import io.wispforest.endec.Endec;
import net.minecraft.util.StringIdentifiable;

public enum CardLanguage implements StringIdentifiable {
    ENGLISH("en", "en_"),
    SPANISH("es", "es_"),
    FRENCH("fr", "fr_"),
    GERMAN("de", "de_"),
    ITALIAN("it", "it_"),
    PORTUGUESE("pt", "pt_"),
    JAPANESE("ja", "ja_"),
    KOREAN("ko", "ko_"),
    RUSSIAN("ru", "ru_"),
    SIMPLIFIED_CHINESE("zhs", "zh_cn"),
    TRADITIONAL_CHINESE("zht", "zh_tw"),
    HEBREW("he", "he_"),
    LATIN("la", "la_"),
    ANCIENT_GREEK("grc", "el_"),
    ARABIC("ar", "ar_"),
    SANSKRIT("sa", ""),
    PHYREXIAN("ph", ""),
    QUENYA("qya", "");

    public static final Endec<CardLanguage> ENDEC = StringIdentifiableEndecUtils.createEndec(CardLanguage.class);
    private final String name;
    public final String languagePrefix;

    CardLanguage(String name, String languagePrefix) {
        this.name = name;
        this.languagePrefix = languagePrefix;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
