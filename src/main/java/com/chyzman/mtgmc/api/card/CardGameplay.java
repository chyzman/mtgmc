package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CardGameplay {

    @NotNull
    default List<MtgRelatedCard> allParts() {return cardGameplayData().allParts();}

    @NotNull
    default List<CardFace> cardFaces() {return cardGameplayData().cardFaces();}

    default int cmc() {return cardGameplayData().cmc();}

    @NotNull
    default List<MtgColor> colorIdentity() {return cardGameplayData().colorIdentity();}

    @Nullable
    default List<MtgColor> colorIndicator() {return cardGameplayData().colorIndicator();}

    @Nullable
    default List<MtgColor> colors() {return cardGameplayData().colors();}

    @Nullable
    default String defense() {return cardGameplayData().defense();}

    @Nullable
    default Integer edhrecRank() {return cardGameplayData().edhrecRank();}

    @Nullable
    default String handModifier() {return cardGameplayData().handModifier();}

    @NotNull
    default List<String> keywords() {return cardGameplayData().keywords();}

    @NotNull
    default CardLegalities legalities() {return cardGameplayData().legalities();}

    @Nullable
    default String lifeModifier() {return cardGameplayData().lifeModifier();}

    @Nullable
    default String loyalty() {return cardGameplayData().loyalty();}

    @Nullable
    default String manaCost() {return cardGameplayData().manaCost();}

    @NotNull
    default String name() {return cardGameplayData().name();}

    @Nullable
    default String oracleText() {return cardGameplayData().oracleText();}

    @Nullable
    default Integer pennyRank() {return cardGameplayData().pennyRank();}

    @Nullable
    default String power() {return cardGameplayData().power();}

    @NotNull
    default List<MtgColor> producedMana() {return cardGameplayData().producedMana();}

    default boolean reserved() {return cardGameplayData().reserved();}

    @Nullable
    default String toughness() {return cardGameplayData().toughness();}

    @NotNull
    default String typeLine() {return cardGameplayData().typeLine();}

    StructEndec<CardGameplayImpl> ENDEC = ExtraEndecs.of(
            MtgRelatedCard.ENDEC.listOf().fieldOf("all_parts", CardGameplay::allParts),
            CardFace.ENDEC.listOf().fieldOf("card_faces", CardGameplay::cardFaces),
            Endec.INT.fieldOf("cmc", CardGameplay::cmc),
            MtgColor.ENDEC.listOf().fieldOf("color_identity", CardGameplay::colorIdentity),
            MtgColor.ENDEC.listOf().fieldOf("color_indicator", CardGameplay::colorIndicator),
            MtgColor.ENDEC.listOf().fieldOf("colors", CardGameplay::colors),
            Endec.STRING.fieldOf("defense", CardGameplay::defense),
            Endec.INT.fieldOf("edhrec_rank", CardGameplay::edhrecRank),
            Endec.STRING.fieldOf("hand_modifier", CardGameplay::handModifier),
            Endec.STRING.listOf().fieldOf("keywords", CardGameplay::keywords),
            CardLegalities.ENDEC.fieldOf("legalities", CardGameplay::legalities),
            Endec.STRING.fieldOf("life_modifier", CardGameplay::lifeModifier),
            Endec.STRING.fieldOf("loyalty", CardGameplay::loyalty),
            Endec.STRING.fieldOf("mana_cost", CardGameplay::manaCost),
            Endec.STRING.fieldOf("name", CardGameplay::name),
            Endec.STRING.fieldOf("oracle_text", CardGameplay::oracleText),
            Endec.INT.fieldOf("penny_rank", CardGameplay::pennyRank),
            Endec.STRING.fieldOf("power", CardGameplay::power),
            MtgColor.ENDEC.listOf().fieldOf("produced_mana", CardGameplay::producedMana),
            Endec.BOOLEAN.fieldOf("reserved", CardGameplay::reserved),
            Endec.STRING.fieldOf("toughness", CardGameplay::toughness),
            Endec.STRING.fieldOf("type_line", CardGameplay::typeLine),
            CardGameplayImpl::new
    );

    CardGameplayImpl cardGameplayData();

    record CardGameplayImpl(
            @NotNull List<MtgRelatedCard> allParts,
            @NotNull List<CardFace> cardFaces,
            int cmc,
            @NotNull List<MtgColor> colorIdentity,
            @Nullable List<MtgColor> colorIndicator,
            @Nullable List<MtgColor> colors,
            @Nullable String defense,
            @Nullable Integer edhrecRank,
            @Nullable String handModifier,
            @NotNull List<String> keywords,
            @NotNull CardLegalities legalities,
            @Nullable String lifeModifier,
            @Nullable String loyalty,
            @Nullable String manaCost,
            @NotNull String name,
            @Nullable String oracleText,
            @Nullable Integer pennyRank,
            @Nullable String power,
            @NotNull List<MtgColor> producedMana,
            boolean reserved,
            @Nullable String toughness,
            @NotNull String typeLine
    ) implements CardGameplay {
        @Override
        public CardGameplayImpl cardGameplayData() {
            return this;
        }
    }
}
