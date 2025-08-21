package com.chyzman.mtgmc.network.minecraft.C2S;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;

public record C2SRequestRulings(CardIdentifier.OracleId cardId) {}
