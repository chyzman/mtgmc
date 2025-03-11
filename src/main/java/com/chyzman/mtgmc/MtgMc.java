package com.chyzman.mtgmc;

import com.chyzman.mtgmc.card.cache.ServerCardCache;
import com.chyzman.mtgmc.command.testCommand;
import com.chyzman.mtgmc.network.MtgMcPackets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class MtgMc implements ModInitializer {
    public static final String MOD_ID = "mtgmc";

    public static final String SCRYFALL = "https://api.scryfall.com";

    public static final ServerCardCache SERVER_CARD_CACHE = new ServerCardCache();

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        MtgMcPackets.registerCommon();

        testCommand.register();

        ServerEventListeners.init();
    }
}
