package com.chyzman.mtgmc.network.minecraft.S2C;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.card.MtgCard;

public record S2CSupplyCard(CardIdentifier identifier, MtgCard card) {}
