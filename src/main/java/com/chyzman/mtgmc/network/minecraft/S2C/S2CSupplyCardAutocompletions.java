package com.chyzman.mtgmc.network.minecraft.S2C;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.card.MtgCard;

import java.util.List;

public record S2CSupplyCardAutocompletions(String query, List<String> completions) {}
