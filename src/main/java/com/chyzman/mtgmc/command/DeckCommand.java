package com.chyzman.mtgmc.command;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.deck.MtgDeckLoader;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.net.URI;
import java.net.URL;

import static com.chyzman.mtgmc.MtgMc.SERVER_CACHE;
import static com.chyzman.mtgmc.registry.MtgMcItems.CARD;
import static com.chyzman.mtgmc.registry.MtgMcItems.DECK;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DeckCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("mtDeck")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(argument("deckURL", StringArgumentType.greedyString())
                                          .executes(context -> fetchDeck(context.getSource(), URI.create(StringArgumentType.getString(context, "deckURL"))))
                            )
            );
        });
    }

    private static int fetchDeck(ServerCommandSource source, URI deckUrl) throws CommandSyntaxException {
        var futureDeck = MtgDeckLoader.fetchDeckList(deckUrl);
        var player = source.getPlayerOrThrow();
        futureDeck.thenAccept(deck -> {
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
