package com.chyzman.mtgmc.api.cardIdentifier;

import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonSerializer;
import io.wispforest.endec.impl.StructEndecBuilder;

public record CardIdentifierId(
        String id
) implements CardIdentifier {
    public static final Endec<CardIdentifierId> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("id", CardIdentifierId::id),
            CardIdentifierId::new
    );

    @Override
    public String toJson() {
        return ENDEC.encodeFully(GsonSerializer::of, this).toString();
    }
}
