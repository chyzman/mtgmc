package com.chyzman.mtgmc.command.api;

import com.chyzman.mtgmc.MtgMc;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.server.command.ServerCommandSource;

import static com.chyzman.mtgmc.MtgMc.SERVER_CACHE;

public class MtgMcSuggestionProviders {
    public static final SuggestionProvider<ServerCommandSource> MTG_CARD_NAMES = SuggestionProviders.register(
            MtgMc.id("card_names"),
            (context, builder) -> SERVER_CACHE.getCardAutoCompletions(context.getNodes().getLast().getRange().get(context.getInput()))
                    .thenCompose(cardNames -> CommandSource.suggestMatching(
                                         cardNames.stream(),
                                         builder
                                 )
                    ).toCompletableFuture()
    );
}
