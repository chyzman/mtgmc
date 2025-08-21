package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.api.scryfall.symbology.MtgColor;
import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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
            MtgRelatedCard.ENDEC.listOf().optionalFieldOf("all_parts", CardGameplay::allParts, ArrayList::new),
            CardFace.ENDEC.listOf().optionalFieldOf("card_faces", CardGameplay::cardFaces, ArrayList::new),
            Endec.INT.fieldOf("cmc", CardGameplay::cmc),
            MtgColor.ENDEC.listOf().fieldOf("color_identity", CardGameplay::colorIdentity),
            MtgColor.ENDEC.listOf().optionalFieldOf("color_indicator", CardGameplay::colorIndicator, (List<MtgColor>) null),
            MtgColor.ENDEC.listOf().optionalFieldOf("colors", CardGameplay::colors, (List<MtgColor>) null),
            Endec.STRING.optionalFieldOf("defense", CardGameplay::defense, (String) null),
            Endec.INT.optionalFieldOf("edhrec_rank", CardGameplay::edhrecRank, (Integer) null),
            Endec.STRING.optionalFieldOf("hand_modifier", CardGameplay::handModifier, (String) null),
            Endec.STRING.listOf().fieldOf("keywords", CardGameplay::keywords),
            CardLegalities.ENDEC.fieldOf("legalities", CardGameplay::legalities),
            Endec.STRING.optionalFieldOf("life_modifier", CardGameplay::lifeModifier, (String) null),
            Endec.STRING.optionalFieldOf("loyalty", CardGameplay::loyalty, (String) null),
            Endec.STRING.optionalFieldOf("mana_cost", CardGameplay::manaCost, (String) null),
            Endec.STRING.fieldOf("name", CardGameplay::name),
            Endec.STRING.optionalFieldOf("oracle_text", CardGameplay::oracleText, (String) null),
            Endec.INT.optionalFieldOf("penny_rank", CardGameplay::pennyRank, (Integer) null),
            Endec.STRING.optionalFieldOf("power", CardGameplay::power, (String) null),
            MtgColor.ENDEC.listOf().optionalFieldOf("produced_mana", CardGameplay::producedMana, ArrayList::new),
            Endec.BOOLEAN.optionalFieldOf("reserved", CardGameplay::reserved, false),
            Endec.STRING.optionalFieldOf("toughness", CardGameplay::toughness, (String) null),
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
