package com.chyzman.mtgmc.network.minecraft.S2C;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;

public record S2CSupplyCard(CardIdentifier identifier, MtgCard card) {}
