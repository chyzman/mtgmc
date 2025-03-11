package com.chyzman.mtgmc.command;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.network.S2C.ViewDump;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import io.wispforest.owo.serialization.format.nbt.NbtDeserializer;
import io.wispforest.owo.serialization.format.nbt.NbtSerializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.nbt.visitor.NbtTextFormatter;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import static com.chyzman.mtgmc.MtgMc.SERVER_CARD_CACHE;
import static com.chyzman.mtgmc.network.MtgMcPackets.CHANNEL;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class testCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    literal("mtg")
                            .then(argument("name", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        var source = context.getSource();
                                        var cardName = StringArgumentType.getString(context, "name");
                                        return fetchAndDumpCard(source, cardName);
                                    })
                            )
            );
        });
    }

    private static int fetchAndDumpCard(ServerCommandSource source, String cardName) throws CommandSyntaxException {
        if (source.getWorld().isClient) return 0;
        var future = SERVER_CARD_CACHE.getCard(new CardIdentifier.NameSet(cardName));
        var player = source.getPlayerOrThrow();
        future.thenAccept(card -> {
            if (card == null) {
                source.sendFeedback(() -> Text.translatable("command.mtgmc.test.fail.card.none", cardName).withColor(Colors.LIGHT_RED), false);
                return;
            }
            CHANNEL.serverHandle(player).send(new ViewDump(Text.of(card.name()), new NbtTextFormatter("  ").apply(MtgCard.ENDEC.encodeFully(NbtSerializer::of, card))));
        });
        return 1;
    }
}
