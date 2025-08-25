package com.chyzman.mtgmc.command;

import com.chyzman.mtgmc.api.scryfall.deck.DeckLoader;
import com.chyzman.mtgmc.command.api.URIArgumentType;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.net.URI;

import static com.chyzman.mtgmc.registry.MtgMcItems.DECK;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DeckCommand {
    public static final DynamicCommandExceptionType UNABLE_TO_LOAD_DECK = new DynamicCommandExceptionType(e -> Text.translatable("command.mtgmc.deck.load.fail.format", e));

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("mtDeck")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("deckURL", URIArgumentType.uri())
                                          .executes(context -> fetchDeck(context.getSource(), URIArgumentType.getURI(context, "deckURL")))
                            )
            );
        });
    }

    private static int fetchDeck(ServerCommandSource source, URI deckUrl) throws CommandSyntaxException {
        var futureDeck = DeckLoader.fetchDeckList(deckUrl);
        if (futureDeck == null) throw UNABLE_TO_LOAD_DECK.create(deckUrl.toString());
        var player = source.getPlayerOrThrow();
        futureDeck.thenAccept(deck -> {
//            if (deck == null) {
//                source.sendError(Text.translatable("command.mtgmc.deck.load.fail.empty", deckUrl.toString()));
//                return;
//            }
            var stack = DECK.getDefaultStack();
            stack.set(MtgMcComponents.DECK, deck);
            player.getInventory().offerOrDrop(stack);
//            for (CardIdentifier.ScryfallId scryfallId : deck) {
//                var cardStack = CARD.getDefaultStack();
//                cardStack.set(MtgMcComponents.CARD, scryfallId);
//                player.getInventory().offerOrDrop(cardStack);
//            }
        });
        return 1;
    }
}
