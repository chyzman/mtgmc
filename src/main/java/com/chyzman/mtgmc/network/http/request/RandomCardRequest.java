
package com.chyzman.mtgmc.network.http.request;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.Nullable;

public record RandomCardRequest(
        @Nullable String query
) {
    public RandomCardRequest() {
        this(null);
    }

    public static final Endec<RandomCardRequest> ENDEC = StructEndecBuilder.of(
            Endec.STRING.optionalFieldOf("q", RandomCardRequest::query, (String) null),
            RandomCardRequest::new
    );
}
