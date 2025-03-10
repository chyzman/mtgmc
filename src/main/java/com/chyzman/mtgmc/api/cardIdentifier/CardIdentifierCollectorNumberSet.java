package com.chyzman.mtgmc.api.cardIdentifier;

import io.wispforest.endec.Endec;
import io.wispforest.endec.format.gson.GsonSerializer;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.Nullable;

public record CardIdentifierNameSet(
        String name,
        @Nullable String set
) implements CardIdentifier {
    public static final Endec<CardIdentifierNameSet> ENDEC = StructEndecBuilder.of(
            Endec.STRING.fieldOf("name", CardIdentifierNameSet::name),
            Endec.STRING.optionalFieldOf("set", CardIdentifierNameSet::set, (String) null),
            CardIdentifierNameSet::new
    );

    @Override
    public String toJson() {
        return ENDEC.encodeFully(GsonSerializer::of, this).toString();
    }
}
