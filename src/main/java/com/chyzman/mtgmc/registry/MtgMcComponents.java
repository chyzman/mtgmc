package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.api.scryfall.deck.LoadedDeck;
import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;

public class MtgMcComponents implements AutoRegistryContainer<ComponentType<?>> {

    public static final ComponentType<CardIdentifier.ScryfallId> CARD = ComponentType.<CardIdentifier.ScryfallId>builder()
            .endec(CardIdentifier.ScryfallId.ENDEC)
            .build();

    public static final ComponentType<LoadedDeck> DECK = ComponentType.<LoadedDeck>builder()
            .endec(LoadedDeck.ENDEC)
            .build();

    @Override
    public Registry<ComponentType<?>> getRegistry() {
        return Registries.DATA_COMPONENT_TYPE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<ComponentType<?>> getTargetFieldType() {
        return (Class<ComponentType<?>>) (Object) ComponentType.class;
    }
}
