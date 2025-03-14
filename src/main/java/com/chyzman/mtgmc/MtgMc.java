package com.chyzman.mtgmc;

import com.chyzman.mtgmc.cache.impl.ServerMtgCache;
import com.chyzman.mtgmc.command.DeckCommand;
import com.chyzman.mtgmc.command.GatherCommand;
import com.chyzman.mtgmc.network.MtgMcPackets;
import com.chyzman.mtgmc.registry.MtgMcBlockEntities;
import com.chyzman.mtgmc.registry.MtgMcBlocks;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.registry.MtgMcItems;
import com.google.gson.Gson;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.net.http.HttpClient;

public class MtgMc implements ModInitializer {
    public static final String MOD_ID = "mtgmc";

    public static final String SCRYFALL = "https://api.scryfall.com";
    public static final Gson GSON = new Gson();
    public static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static final ServerMtgCache SERVER_CACHE = new ServerMtgCache();


    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        MtgMcPackets.registerCommon();

        GatherCommand.register();
        DeckCommand.register();

        ServerEventListeners.init();

        MtgMcItems.init();
        MtgMcBlocks.init();
        MtgMcBlockEntities.init();

        FieldRegistrationHandler.register(MtgMcComponents.class, MOD_ID, false);
    }
}
