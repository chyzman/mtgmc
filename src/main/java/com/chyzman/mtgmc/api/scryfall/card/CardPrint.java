package com.chyzman.mtgmc.api.scryfall.card;

import com.chyzman.mtgmc.api.scryfall.set.MtgSetType;
import com.chyzman.mtgmc.util.ExtraEndecs;
import io.wispforest.endec.Endec;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.BuiltInEndecs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface CardPrint {
    @Nullable
    default String artist() {return cardPrintData().artist();}

    @NotNull
    default List<String> artistIds() {return cardPrintData().artistIds();}

    @NotNull
    default List<Integer> attributionLights() {return cardPrintData().attributionLights();}

    default boolean booster() {return cardPrintData().booster();}

    @NotNull
    default String borderColor() {return cardPrintData().borderColor();}

    @Nullable
    default UUID cardBackId() {return cardPrintData().cardBackId();}

    @NotNull
    default String collectorNumber() {return cardPrintData().collectorNumber();}

    default boolean contentWarning() {return cardPrintData().contentWarning();}

    default boolean digital() {return cardPrintData().digital();}

    @NotNull
    default List<CardFinish> finishes() {return cardPrintData().finishes();}

    @Nullable
    default String flavorName() {return cardPrintData().flavorName();}

    @Nullable
    default String flavorText() {return cardPrintData().flavorText();}

    @NotNull
    default CardFrame frame() {return cardPrintData().frame();}

    @NotNull
    default List<String> frameEffects() {return cardPrintData().frameEffects();}

    default boolean fullArt() {return cardPrintData().fullArt();}

    @NotNull
    default List<CardGames> games() {return cardPrintData().games();}

    default boolean highresImage() {return cardPrintData().highresImage();}

    @Nullable
    default UUID illustrationId() {return cardPrintData().illustrationId();}

    @NotNull
    default CardImageStatus imageStatus() {return cardPrintData().imageStatus();}

    @Nullable
    default CardImageUris imageUris() {return cardPrintData().imageUris();}

    default boolean oversized() {return cardPrintData().oversized();}

    @NotNull
    default CardPreview preview() {return cardPrintData().preview();}

    @NotNull
    default CardPrices prices() {return cardPrintData().prices();}

    @Nullable
    default String printedName() {return cardPrintData().printedName();}

    @Nullable
    default String printedText() {return cardPrintData().printedText();}

    @Nullable
    default String printedTypeLine() {return cardPrintData().printedTypeLine();}

    default boolean promo() {return cardPrintData().promo();}

    @NotNull
    default List<String> promoTypes() {return cardPrintData().promoTypes();}

    @NotNull
    default Map<String, String> purchaseUris() {return cardPrintData().purchaseUris();}

    @NotNull
    default CardRarity rarity() {return cardPrintData().rarity();}

    @NotNull
    default Map<String, String> relatedUris() {return cardPrintData().relatedUris();}

    @NotNull
    default String releasedAt() {return cardPrintData().releasedAt();}

    default boolean reprint() {return cardPrintData().reprint();}

    @NotNull
    default String scryfallSetUri() {return cardPrintData().scryfallSetUri();}

    @Nullable
    default CardSecurityStamp securityStamp() {return cardPrintData().securityStamp();}

    @NotNull
    default String set() {return cardPrintData().set();}

    @NotNull
    default String setName() {return cardPrintData().setName();}

    @NotNull
    default String setSearchUri() {return cardPrintData().setSearchUri();}

    @NotNull
    default MtgSetType setType() {return cardPrintData().setType();}

    @NotNull
    default String setUri() {return cardPrintData().setUri();}

    default boolean storySpotlight() {return cardPrintData().storySpotlight();}

    default boolean textless() {return cardPrintData().textless();}

    default boolean variation() {return cardPrintData().variation();}

    @Nullable
    default String variationOf() {return cardPrintData().variationOf();}

    @Nullable
    default String watermark() {return cardPrintData().watermark();}

    StructEndec<CardPrintImpl> ENDEC = ExtraEndecs.of(
            Endec.STRING.optionalFieldOf("artist", CardPrint::artist, (String) null),
            Endec.STRING.listOf().optionalFieldOf("artist_ids", CardPrint::artistIds, ArrayList::new),
            Endec.INT.listOf().optionalFieldOf("attribution_line", CardPrint::attributionLights, ArrayList::new),
            Endec.BOOLEAN.fieldOf("booster", CardPrint::booster),
            Endec.STRING.fieldOf("border_color", CardPrint::borderColor),
            BuiltInEndecs.UUID.optionalFieldOf("card_back_id", CardPrint::cardBackId, (UUID) null),
            Endec.STRING.fieldOf("collector_number", CardPrint::collectorNumber),
            Endec.BOOLEAN.optionalFieldOf("content_warning", CardPrint::contentWarning, false),
            Endec.BOOLEAN.fieldOf("digital", CardPrint::digital),
            CardFinish.ENDEC.listOf().optionalFieldOf("finishes", CardPrint::finishes, ArrayList::new),
            Endec.STRING.optionalFieldOf("flavor_name", CardPrint::flavorName, (String) null),
            Endec.STRING.optionalFieldOf("flavor_text", CardPrint::flavorText, (String) null),
            CardFrame.ENDEC.fieldOf("frame", CardPrint::frame),
            Endec.STRING.listOf().optionalFieldOf("frame_effects", CardPrint::frameEffects, ArrayList::new),
            Endec.BOOLEAN.fieldOf("full_art", CardPrint::fullArt),
            CardGames.ENDEC.listOf().fieldOf("games", CardPrint::games),
            Endec.BOOLEAN.fieldOf("highres_image", CardPrint::highresImage),
            BuiltInEndecs.UUID.optionalFieldOf("illustration_id", CardPrint::illustrationId, (UUID) null),
            CardImageStatus.ENDEC.fieldOf("image_status", CardPrint::imageStatus),
            CardImageUris.ENDEC.optionalFieldOf("image_uris", CardPrint::imageUris, (CardImageUris) null),
            Endec.BOOLEAN.fieldOf("oversized", CardPrint::oversized),
            CardPreview.ENDEC.optionalFieldOf("preview", CardPrint::preview, (CardPreview) null),
            CardPrices.ENDEC.fieldOf("prices", CardPrint::prices),
            Endec.STRING.optionalFieldOf("printed_name", CardPrint::printedName, (String) null),
            Endec.STRING.optionalFieldOf("printed_text", CardPrint::printedText, (String) null),
            Endec.STRING.optionalFieldOf("printed_type_line", CardPrint::printedTypeLine, (String) null),
            Endec.BOOLEAN.fieldOf("promo", CardPrint::promo),
            Endec.STRING.listOf().optionalFieldOf("promo_types", CardPrint::promoTypes, ArrayList::new),
            Endec.STRING.mapOf().optionalFieldOf("purchase_uris", CardPrint::purchaseUris, HashMap::new),
            CardRarity.ENDEC.fieldOf("rarity", CardPrint::rarity),
            Endec.STRING.mapOf().fieldOf("related_uris", CardPrint::relatedUris),
            Endec.STRING.fieldOf("released_at", CardPrint::releasedAt),
            Endec.BOOLEAN.fieldOf("reprint", CardPrint::reprint),
            Endec.STRING.fieldOf("scryfall_set_uri", CardPrint::scryfallSetUri),
            CardSecurityStamp.ENDEC.optionalFieldOf("security_stamp", CardPrint::securityStamp, (CardSecurityStamp) null),
            Endec.STRING.fieldOf("set", CardPrint::set),
            Endec.STRING.fieldOf("set_name", CardPrint::setName),
            Endec.STRING.fieldOf("set_search_uri", CardPrint::setSearchUri),
            MtgSetType.ENDEC.fieldOf("set_type", CardPrint::setType),
            Endec.STRING.fieldOf("set_uri", CardPrint::setUri),
            Endec.BOOLEAN.fieldOf("story_spotlight", CardPrint::storySpotlight),
            Endec.BOOLEAN.fieldOf("textless", CardPrint::textless),
            Endec.BOOLEAN.fieldOf("variation", CardPrint::variation),
            Endec.STRING.optionalFieldOf("variation_of", CardPrint::variationOf, (String) null),
            Endec.STRING.optionalFieldOf("watermark", CardPrint::watermark, (String) null),
            CardPrintImpl::new
    );


    CardPrintImpl cardPrintData();

    record CardPrintImpl(
            @Nullable String artist,
            @NotNull List<String> artistIds,
            @NotNull List<Integer> attributionLights,
            boolean booster,
            @NotNull String borderColor,
            @Nullable UUID cardBackId,
            @NotNull String collectorNumber,
            boolean contentWarning,
            boolean digital,
            @NotNull List<CardFinish> finishes,
            @Nullable String flavorName,
            @Nullable String flavorText,
            @NotNull CardFrame frame,
            @NotNull List<String> frameEffects,
            boolean fullArt,
            @NotNull List<CardGames> games,
            boolean highresImage,
            @Nullable UUID illustrationId,
            @NotNull CardImageStatus imageStatus,
            @Nullable CardImageUris imageUris,
            boolean oversized,
            @NotNull CardPreview preview,
            @NotNull CardPrices prices,
            @Nullable String printedName,
            @Nullable String printedText,
            @Nullable String printedTypeLine,
            boolean promo,
            @NotNull List<String> promoTypes,
            @NotNull Map<String, String> purchaseUris,
            @NotNull CardRarity rarity,
            @NotNull Map<String, String> relatedUris,
            @NotNull String releasedAt,
            boolean reprint,
            @NotNull String scryfallSetUri,
            @Nullable CardSecurityStamp securityStamp,
            @NotNull String set,
            @NotNull String setName,
            @NotNull String setSearchUri,
            @NotNull MtgSetType setType,
            @NotNull String setUri,
            boolean storySpotlight,
            boolean textless,
            boolean variation,
            @Nullable String variationOf,
            @Nullable String watermark
    ) implements CardPrint {
        @Override
        public CardPrintImpl cardPrintData() {
            return this;
        }
    }
}
