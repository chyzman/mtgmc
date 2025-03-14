package com.chyzman.mtgmc.api.deck.api;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.util.Procrastinator;

import java.util.List;

public abstract class DeckFormat {

    public abstract boolean inputValid(String input);

    public abstract Procrastinator<List<CardIdentifier.ScryfallId>> load(String input);
}
