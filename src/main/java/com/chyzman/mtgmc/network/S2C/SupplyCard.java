package com.chyzman.mtgmc.network.S2C;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;

public record SupplyCard(CardIdentifier identifier, MtgCard card) {}
