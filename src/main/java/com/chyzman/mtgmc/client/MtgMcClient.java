package com.chyzman.mtgmc.client;

import com.chyzman.mtgmc.card.cache.ClientCardCache;
import com.chyzman.mtgmc.network.MtgMcPackets;
import net.fabricmc.api.ClientModInitializer;

public class MtgMcClient implements ClientModInitializer {
    public static final ClientCardCache CLIENT_CARD_CACHE = new ClientCardCache();

    @Override
    public void onInitializeClient() {
        MtgMcPackets.registerClient();
    }
}
