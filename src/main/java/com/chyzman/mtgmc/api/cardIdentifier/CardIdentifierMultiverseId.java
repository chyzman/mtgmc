package com.chyzman.mtgmc.api.cardIdentifier;

import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonSerializer;
import io.wispforest.endec.impl.StructEndecBuilder;

public record CardIdentifierIllustrationId(
        String illustrationId
) implements CardIdentifier {
    public static final Endec<CardIdentifierIllustrationId> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("illustration_id", CardIdentifierIllustrationId::illustrationId),
            CardIdentifierIllustrationId::new
    );

    @Override
    public String toJson() {
        return ENDEC.encodeFully(GsonSerializer::of, this).toString();
    }
}
