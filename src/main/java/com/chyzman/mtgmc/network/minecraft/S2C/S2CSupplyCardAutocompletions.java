package com.chyzman.mtgmc.network.minecraft.S2C;

import java.util.List;

public record S2CSupplyCardAutocompletions(String query, List<String> completions) {}
