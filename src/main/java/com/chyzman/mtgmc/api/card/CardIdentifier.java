package com.chyzman.mtgmc.api.card;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.wispforest.endec.Endec;
import io.wispforest.endec.SerializationAttributes;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.format.gson.GsonDeserializer;
import io.wispforest.endec.format.gson.GsonEndec;
import io.wispforest.endec.format.gson.GsonSerializer;
import io.wispforest.endec.impl.BuiltInEndecs;
import io.wispforest.endec.impl.StructEndecBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public sealed interface CardIdentifier permits
        CardIdentifier.ScryfallId,
        CardIdentifier.MtgoId,
        CardIdentifier.MultiverseId,
        CardIdentifier.OracleId,
        CardIdentifier.IllustrationId,
        CardIdentifier.NameSet,
        CardIdentifier.CollectorNumberSet {

    JsonElement toGson();

    @Nullable
    default CardIdentifier fromCard(MtgCard card) {
        return null;
    }

    String getKey();

    Endec<CardIdentifier> MAPPED_ENDEC = GsonEndec.INSTANCE
            .xmap(map -> {
                if (!(map instanceof JsonObject jsonObject)) throw new IllegalStateException("Unable to parse a cardIdentifier from a non-JsonObject!");

                if (jsonObject.has("id")) return ScryfallId.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("mtgo_id")) return MtgoId.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("multiverse_id")) return MultiverseId.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("oracle_id")) return OracleId.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("illustration_id")) return IllustrationId.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("name")) return NameSet.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);
                if (jsonObject.has("collector_number") && jsonObject.has("set")) return CollectorNumberSet.ENDEC.decodeFully(GsonDeserializer::of, jsonObject);

                throw new IllegalStateException("Unable to parse the given data into a valid cardIdentifier!");
            }, CardIdentifier::toGson);

    Endec<CardIdentifier> KEYED_ENDEC = Endec.dispatchedStruct(
            key -> switch (key) {
                case "id" -> ScryfallId.ENDEC;
                case "mtgo_id" -> MtgoId.ENDEC;
                case "multiverse_id" -> MultiverseId.ENDEC;
                case "oracle_id" -> OracleId.ENDEC;
                case "illustration_id" -> IllustrationId.ENDEC;
                case "name+set" -> NameSet.ENDEC;
                case "collector_number+set" -> CollectorNumberSet.ENDEC;
                default -> throw new IllegalStateException("Unexpected value: " + key);
            },
            CardIdentifier::getKey, Endec.STRING, "type"
    );

    //might need to be <? extends CardIdentifier>
    Endec<CardIdentifier> ENDEC = Endec.ifAttr(SerializationAttributes.HUMAN_READABLE, MAPPED_ENDEC).orElse(KEYED_ENDEC);

    record ScryfallId(UUID id) implements CardIdentifier {

        public static final StructEndec<ScryfallId> ENDEC = StructEndecBuilder.of(
                BuiltInEndecs.UUID.fieldOf("id", ScryfallId::id),
                ScryfallId::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public CardIdentifier fromCard(MtgCard card) {
            return new ScryfallId(card.id());
        }

        @Override
        public String getKey() {
            return "id";
        }
    }

    record MtgoId(int mtgoId) implements CardIdentifier {

        public static final StructEndec<MtgoId> ENDEC = StructEndecBuilder.of(
                Endec.INT.fieldOf("mtgo_id", MtgoId::mtgoId),
                MtgoId::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @SuppressWarnings("DataFlowIssue")
        @Override
        public CardIdentifier fromCard(MtgCard card) {
            return card.mtgoId() == null ? null : new MtgoId(card.mtgoId());
        }

        @Override
        public String getKey() {
            return "mtgo_id";
        }
    }

    record MultiverseId(int multiverseId) implements CardIdentifier {

        public static final StructEndec<MultiverseId> ENDEC = StructEndecBuilder.of(
                Endec.INT.fieldOf("multiverse_id", MultiverseId::multiverseId),
                MultiverseId::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public String getKey() {
            return "multiverse_id";
        }
    }

    record OracleId(UUID oracleId) implements CardIdentifier {

        public static final StructEndec<OracleId> ENDEC = StructEndecBuilder.of(
                BuiltInEndecs.UUID.fieldOf("oracle_id", OracleId::oracleId),
                OracleId::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public String getKey() {
            return "oracle_id";
        }
    }

    record IllustrationId(UUID illustrationId) implements CardIdentifier {

        public static final StructEndec<IllustrationId> ENDEC = StructEndecBuilder.of(
                BuiltInEndecs.UUID.fieldOf("illustration_id", IllustrationId::illustrationId),
                IllustrationId::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public String getKey() {
            return "illustration_id";
        }
    }

    record NameSet(String name, @Nullable String set) implements CardIdentifier {

        public static final StructEndec<NameSet> ENDEC = StructEndecBuilder.of(
                Endec.STRING.fieldOf("name", NameSet::name),
                Endec.STRING.optionalFieldOf("set", NameSet::set, (String) null),
                NameSet::new
        );

        public NameSet(String name) {
            this(name, null);
        }

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public String getKey() {
            return "name+set";
        }
    }

    record CollectorNumberSet(String collectorNumber, String set) implements CardIdentifier {

        public static final StructEndec<CollectorNumberSet> ENDEC = StructEndecBuilder.of(
                Endec.STRING.fieldOf("collector_number", CollectorNumberSet::collectorNumber),
                Endec.STRING.fieldOf("set", CollectorNumberSet::set),
                CollectorNumberSet::new
        );

        @Override
        public JsonElement toGson() {
            return ENDEC.encodeFully(GsonSerializer::of, this);
        }

        @Override
        public String getKey() {
            return "collector_number+set";
        }
    }

}
