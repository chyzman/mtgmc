package com.chyzman.mtgmc.api.scryfall.deck.api;

import com.chyzman.mtgmc.api.scryfall.deck.LoadedDeck;
import com.chyzman.mtgmc.util.Procrastinator;
import org.jetbrains.annotations.Nullable;

public abstract class DeckFormat {

    public abstract boolean inputValid(String input);

    public abstract Procrastinator<LoadedDeck> load(String input);
}
