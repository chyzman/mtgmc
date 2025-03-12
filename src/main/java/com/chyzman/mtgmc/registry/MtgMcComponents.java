package com.chyzman.mtgmc.registry;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MtgMcComponents implements AutoRegistryContainer<ComponentType<?>> {

    public static final ComponentType<CardIdentifier.ScryfallId> CARD = ComponentType.<CardIdentifier.ScryfallId>builder()
            .endec(CardIdentifier.ScryfallId.ENDEC)
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
