package com.chyzman.mtgmc.client;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.card.cache.ClientCardCache;
import com.chyzman.mtgmc.item.renderer.CardModelRenderer;
import com.chyzman.mtgmc.network.MtgMcPackets;
import net.fabricmc.api.ClientModInitializer;

import static net.minecraft.client.render.item.model.special.SpecialModelTypes.ID_MAPPER;

public class MtgMcClient implements ClientModInitializer {
    public static final ClientCardCache CLIENT_CARD_CACHE = new ClientCardCache();

    @Override
    public void onInitializeClient() {
        MtgMcPackets.registerClient();

        ID_MAPPER.put(MtgMc.id("card"), CardModelRenderer.Unbaked.CODEC);
    }
}
