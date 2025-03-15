package com.chyzman.mtgmc.api.deck;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.deck.api.UriDeckFormat;
import com.chyzman.mtgmc.api.deck.impl.AetherhubFormat;
import com.chyzman.mtgmc.api.deck.impl.ArchidektFormat;
import com.chyzman.mtgmc.util.Procrastinator;
import com.mojang.logging.LogUtils;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class DeckLoader {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<Identifier, UriDeckFormat> URI_DECK_FORMATS = Map.of(
            MtgMc.id("aetherhub"), new AetherhubFormat(),
            MtgMc.id("archidekt"), new ArchidektFormat()
    );

    public static Procrastinator<List<CardIdentifier.ScryfallId>> fetchDeckList(String input) {
        try {
            return fetchDeckList(Util.validateUri(input));
        } catch (URISyntaxException ignored) {}
        LOGGER.warn("Unable to load deck from: {}", input);
        return Procrastinator.procrastinated(List.of());
    }

    public static Procrastinator<List<CardIdentifier.ScryfallId>> fetchDeckList(URI uri) {
        for (Map.Entry<Identifier, UriDeckFormat> formatEntry : URI_DECK_FORMATS.entrySet()) {
            var identifier = formatEntry.getKey();
            var format = formatEntry.getValue();
            if (format.inputValid(uri)) return format.load(uri.toString());
        }
        LOGGER.warn("No deck format found for URI: {}", uri);
        return Procrastinator.procrastinated(List.of());
    }

}
