package com.chyzman.mtgmc.api.cardIdentifier;

import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonSerializer;
import io.wispforest.endec.impl.StructEndecBuilder;

public record CardIdentifierMultiverseId(
        int multiverseId
) implements CardIdentifier {
    public static final Endec<CardIdentifierMultiverseId> ENDEC = StructEndecBuilder.of(
            Endec.INT.fieldOf("multiverse_id", CardIdentifierMultiverseId::multiverseId),
            CardIdentifierMultiverseId::new
    );

    @Override
    public String toJson() {
        return ENDEC.encodeFully(GsonSerializer::of, this).toString();
    }
}
