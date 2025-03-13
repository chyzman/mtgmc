package com.chyzman.mtgmc.network.http.response;

import com.chyzman.mtgmc.api.ruling.Ruling;
import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CardRulingsResponse(
        @NotNull List<Ruling> data
) {

    public static final Endec<CardRulingsResponse> ENDEC = StructEndecBuilder.of(
            Ruling.ENDEC.listOf().fieldOf("data", CardRulingsResponse::data),
            CardRulingsResponse::new
    );
}
