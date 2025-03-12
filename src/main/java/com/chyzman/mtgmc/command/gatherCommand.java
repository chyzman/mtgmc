package com.chyzman.mtgmc.command;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.UuidArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import static com.chyzman.mtgmc.MtgMc.SERVER_CARD_CACHE;
import static com.chyzman.mtgmc.registry.MtgMcItems.CARD;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class gatherCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("mtGather")
                            .requires(source -> source.hasPermissionLevel(2))
                            .then(literal("byId")
                                          .then(argument("scryfallId", UuidArgumentType.uuid())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.ScryfallId(UuidArgumentType.getUuid(context, "scryfallId"))))
                                          )
                            )
                            .then(literal("byMtgoId")
                                          .then(argument("mtgoId", IntegerArgumentType.integer())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.MtgoId(IntegerArgumentType.getInteger(context, "mtgoId"))))
                                          )
                            )
                            .then(literal("byMultiverseId")
                                          .then(argument("multiverseId", IntegerArgumentType.integer())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.MultiverseId(IntegerArgumentType.getInteger(context, "multiverseId"))))
                                          )
                            )
                            .then(literal("byOracleId")
                                          .then(argument("oracleId", UuidArgumentType.uuid())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.OracleId(UuidArgumentType.getUuid(context, "oracleId"))))
                                          )
                            )
                            .then(literal("byName")
                                          .then(argument("name", StringArgumentType.greedyString())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.NameSet(StringArgumentType.getString(context, "name"))))
                                          )
                            )
                            .then(literal("byNameAndSet")
                                          .then(argument("name", StringArgumentType.string())
                                                        .then(argument("set", StringArgumentType.string())
                                                                      .executes(context -> gatherCard(context.getSource(), new CardIdentifier.NameSet(StringArgumentType.getString(context, "name"), StringArgumentType.getString(context, "set"))))
                                                        )
                                          )
                            )
                            .then(literal("byIllustrationId")
                                          .then(argument("illustrationId", UuidArgumentType.uuid())
                                                        .executes(context -> gatherCard(context.getSource(), new CardIdentifier.IllustrationId(UuidArgumentType.getUuid(context, "illustrationId"))))
                                          )
                            )
                            .then(literal("byCollectorNumberAndSet")
                                          .then(argument("collectorNumber", StringArgumentType.string())
                                                        .then(argument("set", StringArgumentType.string())
                                                                      .executes(context -> gatherCard(context.getSource(), new CardIdentifier.CollectorNumberSet(StringArgumentType.getString(context, "collectorNumber"), StringArgumentType.getString(context, "set"))))
                                                        )
                                          )
                            )
            );
        });
    }

    private static int gatherCard(ServerCommandSource source, CardIdentifier cardId) throws CommandSyntaxException {
        if (source.getWorld().isClient) return 0;
        var future = SERVER_CARD_CACHE.getCard(cardId);
        var player = source.getPlayerOrThrow();
        future.thenAccept(card -> {
            if (card == null) {
                source.sendFeedback(() -> Text.translatable("command.mtgmc.gather.fail").withColor(Colors.LIGHT_RED), false);
                return;
            }
            var stack = CARD.getDefaultStack();
            stack.set(MtgMcComponents.CARD, new CardIdentifier.ScryfallId(card.id()));
            player.getInventory().offerOrDrop(stack);
            source.sendFeedback(() -> Text.translatable("commands.mtgmc.gather.success", card.getDisplayName(), player.getDisplayName()), false);
        });
        return 1;
    }
}
