package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;

public class MtgMcComponents implements AutoRegistryContainer<ComponentType<?>> {

    public static final ComponentType<CardIdentifier.ScryfallId> CARD = ComponentType.<CardIdentifier.ScryfallId>builder()
            .endec(CardIdentifier.ScryfallId.ENDEC)
            .build();

    public static final ComponentType<List<CardIdentifier.ScryfallId>> DECK = ComponentType.<List<CardIdentifier.ScryfallId>>builder()
            .endec(CardIdentifier.ScryfallId.ENDEC.listOf())
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
