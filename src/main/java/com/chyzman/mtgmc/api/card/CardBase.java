package com.chyzman.mtgmc.card.api;

import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CardBase {

    @Nullable
    default Integer arenaId() {return cardBaseData().arenaId();}

    @Nullable
    default Integer cardmarketId() {return cardBaseData().cardmarketId();}

    @NotNull
    default String id() {return cardBaseData().id();}

    @NotNull
    default CardLanguage lang() {return cardBaseData().lang();}

    @NotNull
    default CardLayout layout() {return cardBaseData().layout();}

    @Nullable
    default Integer mtgoFoilId() {return cardBaseData().mtgoFoilId();}

    @Nullable
    default Integer mtgoId() {return cardBaseData().mtgoId();}

    @Nullable
    default List<Integer> multiverseIds() {return cardBaseData().multiverseIds();}

    @Nullable
    default String oracleId() {return cardBaseData().oracleId();}

    @NotNull
    default String printsSearchUri() {return cardBaseData().printsSearchUri();}

    @NotNull
    default String rulingsUri() {return cardBaseData().rulingsUri();}

    @NotNull
    default String scrufallUri() {return cardBaseData().scrufallUri();}

    @Nullable
    default Integer tcgplayerEtchedId() {return cardBaseData().tcgplayerEtchedId();}

    @Nullable
    default Integer tcgplayerId() {return cardBaseData().tcgplayerId();}

    @NotNull
    default String uri() {return cardBaseData().uri();}

    StructEndec<CardBaseImpl> ENDEC = StructEndecBuilder.of(
            Endec.INT.optionalFieldOf("arena_id", CardBase::arenaId, (Integer) null),
            Endec.INT.optionalFieldOf("cardmarket_id", CardBase::cardmarketId, (Integer) null),
            Endec.STRING.fieldOf("id", CardBase::id),
            CardLanguage.ENDEC.fieldOf("lang", CardBase::lang),
            CardLayout.ENDEC.fieldOf("layout", CardBase::layout),
            Endec.INT.optionalFieldOf("mtgo_foil_id", CardBase::mtgoFoilId, (Integer) null),
            Endec.INT.optionalFieldOf("mtgo_id", CardBase::mtgoId, (Integer) null),
            Endec.INT.listOf().optionalFieldOf("multiverse_ids", CardBase::multiverseIds, (List<Integer>) null),
            Endec.STRING.optionalFieldOf("oracle_id", CardBase::oracleId, (String) null),
            Endec.STRING.fieldOf("prints_search_uri", CardBase::printsSearchUri),
            Endec.STRING.fieldOf("rulings_uri", CardBase::rulingsUri),
            Endec.STRING.fieldOf("scryfall_uri", CardBase::scrufallUri),
            Endec.INT.optionalFieldOf("tcgplayer_etched_id", CardBase::tcgplayerEtchedId, (Integer) null),
            Endec.INT.optionalFieldOf("tcgplayer_id", CardBase::tcgplayerId, (Integer) null),
            Endec.STRING.fieldOf("uri", CardBase::uri),
            CardBaseImpl::new
    );

    CardBaseImpl cardBaseData();

    record CardBaseImpl(
            @Nullable Integer arenaId,
            @Nullable Integer cardmarketId,
            @NotNull String id,
            @NotNull CardLanguage lang,
            @NotNull CardLayout layout,
            @Nullable Integer mtgoFoilId,
            @Nullable Integer mtgoId,
            @Nullable List<Integer> multiverseIds,
            @Nullable String oracleId,
            @NotNull String printsSearchUri,
            @NotNull String rulingsUri,
            @NotNull String scrufallUri,
            @Nullable Integer tcgplayerEtchedId,
            @Nullable Integer tcgplayerId,
            @NotNull String uri
    ) implements CardBase {
        @Override
        public CardBaseImpl cardBaseData() {
            return this;
        }
    }
}
