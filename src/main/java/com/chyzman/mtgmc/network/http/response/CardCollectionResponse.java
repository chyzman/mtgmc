package com.chyzman.mtgmc.network.http.response;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;

public record CardCollectionResponse(
                List<MtgCard> data,
                List<CardIdentifier> notFound
        ) {
            public static final Endec<CardCollectionResponse> ENDEC = StructEndecBuilder.of(
                    MtgCard.ENDEC.listOf().fieldOf("data", CardCollectionResponse::data),
                    CardIdentifier.ENDEC.listOf().fieldOf("not_found", CardCollectionResponse::notFound),
                    CardCollectionResponse::new
            );
        }
