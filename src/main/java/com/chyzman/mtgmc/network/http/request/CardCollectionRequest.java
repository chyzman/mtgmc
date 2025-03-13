package com.chyzman.mtgmc.network.http.request;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;

import java.util.List;

public record CardCollectionRequest(
        List<CardIdentifier> identifiers
) {
    public static final Endec<CardCollectionRequest> ENDEC = StructEndecBuilder.of(
            CardIdentifier.ENDEC.listOf().fieldOf("identifiers", CardCollectionRequest::identifiers),
            CardCollectionRequest::new
    );
}
