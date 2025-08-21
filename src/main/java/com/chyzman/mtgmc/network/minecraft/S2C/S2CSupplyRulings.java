package com.chyzman.mtgmc.network.minecraft.S2C;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.ruling.Ruling;

import java.util.List;

public record S2CSupplyRulings(CardIdentifier.OracleId cardId, List<Ruling> rulings) {}
