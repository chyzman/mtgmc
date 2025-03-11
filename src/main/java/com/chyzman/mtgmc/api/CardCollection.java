package com.chyzman.mtgmc.api;

import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;

public class CardCollection {
    public record Request(
            List<CardIdentifier> identifiers
    ) {
        public static final Endec<Request> ENDEC = StructEndecBuilder.of(
                CardIdentifier.ENDEC.listOf().fieldOf("identifiers", Request::identifiers),
                Request::new
        );
    }

    public record Response(
            List<MtgCard> data,
            List<CardIdentifier> notFound
    ) {
        public static final Endec<Response> ENDEC = StructEndecBuilder.of(
                MtgCard.ENDEC.listOf().fieldOf("data", Response::data),
                CardIdentifier.ENDEC.listOf().fieldOf("not_found", Response::notFound),
                Response::new
        );
    }
}
