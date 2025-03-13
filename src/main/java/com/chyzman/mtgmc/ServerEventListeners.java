package com.chyzman.mtgmc;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.Date;

import static com.chyzman.mtgmc.MtgMc.SERVER_CACHE;

public class ServerEventListeners {
    public static Date lastFetch = new Date();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var now = new Date();
            if (now.getTime() - lastFetch.getTime() > 1000) {
                lastFetch = now;
                SERVER_CACHE.updateQueue();
            }
        });
    }
}
