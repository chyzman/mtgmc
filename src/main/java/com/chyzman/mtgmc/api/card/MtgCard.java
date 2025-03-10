package com.chyzman.mtgmc.card.api;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

public interface MtgCard extends CardBase, CardGameplay, CardPrint {

    @Override
    CardBaseImpl cardBaseData();

    @Override
    CardGameplayImpl cardGameplayData();

    @Override
    CardPrintImpl cardPrintData();

    Endec<MtgCard> ENDEC = StructEndecBuilder.of(
            CardBaseImpl.ENDEC.flatFieldOf(MtgCard::cardBaseData),
            CardGameplayImpl.ENDEC.flatFieldOf(MtgCard::cardGameplayData),
            CardPrintImpl.ENDEC.flatFieldOf(MtgCard::cardPrintData),
            MtgCardImpl::new
    );

    record MtgCardImpl(
            CardBaseImpl cardBaseData,
            CardGameplayImpl cardGameplayData,
            CardPrintImpl cardPrintData
    ) implements MtgCard {
        @Override
        public CardBaseImpl cardBaseData() {
            return cardBaseData;
        }

        @Override
        public CardGameplayImpl cardGameplayData() {
            return cardGameplayData;
        }

        @Override
        public CardPrintImpl cardPrintData() {
            return cardPrintData;
        }
    }

}
