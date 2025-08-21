package com.chyzman.mtgmc.client;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.blockentity.render.CardBlockEntityRenderer;
import com.chyzman.mtgmc.blockentity.render.CounterBlockEntityRenderer;
import com.chyzman.mtgmc.cache.impl.ClientMtgCache;
import com.chyzman.mtgmc.item.renderer.CardModelRenderer;
import com.chyzman.mtgmc.network.MtgMcPackets;
import com.chyzman.mtgmc.registry.MtgMcBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import static net.minecraft.client.render.item.model.special.SpecialModelTypes.ID_MAPPER;

public class MtgMcClient implements ClientModInitializer {
    public static final ClientMtgCache CLIENT_CACHE = new ClientMtgCache();

    public static float fovLerp = 0;

    @Override
    public void onInitializeClient() {
        MtgMcPackets.registerClient();

        BlockEntityRendererFactories.register(MtgMcBlockEntities.CARD, CardBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(MtgMcBlockEntities.COUNTER, CounterBlockEntityRenderer::new);


        ID_MAPPER.put(MtgMc.id("card"), CardModelRenderer.Unbaked.CODEC);
    }
}
