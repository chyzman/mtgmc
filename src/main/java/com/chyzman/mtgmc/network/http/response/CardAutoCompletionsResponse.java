package com.chyzman.mtgmc.network.http.response;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CardAutoCompletionsResponse(
        @NotNull List<String> data
) {
    public static final Endec<CardAutoCompletionsResponse> ENDEC = StructEndecBuilder.of(
            Endec.STRING.listOf().fieldOf("data", CardAutoCompletionsResponse::data),
            CardAutoCompletionsResponse::new
    );
}
