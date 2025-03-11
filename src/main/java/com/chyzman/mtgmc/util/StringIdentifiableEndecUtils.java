package com.chyzman.mtgmc.util;

import io.wispforest.endec.Endec;
import io.wispforest.endec.SerializationAttributes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StringIdentifiableEndecUtils {
    private static final Map<Class<? extends Enum>, Lookup<?>> CACHED_LOOKUPS = new HashMap<>();

    @Nullable
    public static <T extends Enum<T> & StringIdentifiable> T get(Class<T> clazz, String name) {
        return getOrCreateLookup(clazz).namesToEntry().get(name);
    }

    public static <T extends Enum<T> & StringIdentifiable> Lookup<T> getOrCreateLookup(Class<T> clazz) {
        return (Lookup<T>) CACHED_LOOKUPS.computeIfAbsent(clazz, aClazz -> {
            Map<String, T> serializedNames = new HashMap<>();
            Object2IntMap<T> enumConstantToIndex = new Object2IntOpenHashMap<>();
            T[] constants = clazz.getEnumConstants();

            for (int i = 0; i < constants.length; i++) {
                var enumConstant = constants[i];

                serializedNames.put(enumConstant.asString(), enumConstant);
                enumConstantToIndex.put(enumConstant, i);
            }

            return new Lookup<T>(clazz, serializedNames, enumConstantToIndex, constants);
        });
    }

    public static <T extends Enum<T> & StringIdentifiable> Endec<T> createEndec(Class<T> clazz) {
        Lookup<T> lookup = getOrCreateLookup(clazz);

        return Endec.ifAttr(SerializationAttributes.HUMAN_READABLE, Endec.STRING.xmap(lookup::getEntry, StringIdentifiable::asString))
                .orElse(Endec.VAR_INT.xmap(lookup::getEntry, lookup.entryToIndex()::getInt));
    }

    private record Lookup<T>(Class<T> clazz, Map<String, T> namesToEntry, Object2IntMap<T> entryToIndex, T[] entries) {
        public T getEntry(int index){
            return entries()[index];
        }

        public T getEntry(String name) {
            var entry = namesToEntry().get(name);

            if (entry != null) return entry;

            throw new IllegalArgumentException(clazz().getCanonicalName() + " constant dose not have a Identifiable name of: " + name);
        }
    }
}
