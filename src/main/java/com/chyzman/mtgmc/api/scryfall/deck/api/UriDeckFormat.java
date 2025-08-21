package com.chyzman.mtgmc.api.scryfall.deck.api;

import net.minecraft.util.Util;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class UriDeckFormat extends DeckFormat {

    public abstract boolean inputValid(URI input);

    @Override
    public boolean inputValid(String input) {
        try {
            return inputValid(Util.validateUri(input));
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
