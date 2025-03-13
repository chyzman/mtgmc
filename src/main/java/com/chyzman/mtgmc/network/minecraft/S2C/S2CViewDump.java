package com.chyzman.mtgmc.network.minecraft.S2C;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.api.ruling.Ruling;
import net.minecraft.text.Text;

import java.util.List;

public record S2CViewDump(Text title, Text dump) {}
