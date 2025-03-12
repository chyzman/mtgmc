package com.chyzman.mtgmc;

import com.chyzman.mtgmc.card.cache.ServerCardCache;
import com.chyzman.mtgmc.command.gatherCommand;
import com.chyzman.mtgmc.network.MtgMcPackets;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.registry.MtgMcItems;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
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

        gatherCommand.register();

        ServerEventListeners.init();

        MtgMcItems.init();

        FieldRegistrationHandler.register(MtgMcComponents.class, MOD_ID, false);
    }
}
