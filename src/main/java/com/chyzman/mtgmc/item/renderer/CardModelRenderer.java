package com.chyzman.mtgmc.item.renderer;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.cache.api.MtgCache;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.mojang.serialization.MapCodec;
import io.wispforest.owo.Owo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static com.chyzman.mtgmc.client.MtgMcClient.CLIENT_CACHE;

@Environment(EnvType.CLIENT)
public class CardModelRenderer implements SpecialModelRenderer<@Nullable MtgCard> {
    public static final Identifier UNKNOWN_CARD = MtgMc.id("item/unknown_card");

    @Override
    public @Nullable MtgCard getData(ItemStack stack) {
        var server = Owo.currentServer();
        if (server == null) return null;

        MtgCache cache = server.getOverworld().isClient() ? CLIENT_CACHE : MtgMc.SERVER_CACHE;

        if (!stack.contains(MtgMcComponents.CARD)) return null;

        return cache.getCard(stack.get(MtgMcComponents.CARD)).getNow(null);
    }

    @Override
    public void render(
            @Nullable MtgCard card,
            ModelTransformationMode modelTransformationMode,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            boolean glint
    ) {
        var client = MinecraftClient.getInstance();

        var image = UNKNOWN_CARD;

        if (card != null) {
            var future = CLIENT_CACHE.getImage(card);
            if (future != null) image = future.getNow(image);
        }

        matrices.push();

        var entry = matrices.peek();
        var matrixStack = entry.getPositionMatrix();
        var renderLayer = RenderLayer.getItemEntityTranslucentCull(image == UNKNOWN_CARD ? SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE : image);
        var bufferBuilder = vertexConsumers.getBuffer(renderLayer);

        var minU = 0f;
        var minV = 0f;
        var maxU = 1f;
        var maxV = 1f;

        if (image == UNKNOWN_CARD) {
            var sprite = client.getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(image);
            var width = (sprite.getMaxU() - sprite.getMinU())/16f;
            var height = (sprite.getMaxV() - sprite.getMinV())/16f;
            minU = sprite.getMinU() + 3 * width;
            minV = sprite.getMinV() + height;
            maxU = sprite.getMaxU() - 3 * width;
            maxV = sprite.getMaxV() - height;
        }

        bufferBuilder.vertex(matrixStack, 13/16F, 1/16F, 17/32F).color(255, 255, 255, 255).texture(maxU, maxV).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 13/16F, 15/16F, 17/32F).color(255, 255, 255, 255).texture(maxU, minV).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 3/16F, 15/16F, 17/32F).color(255, 255, 255, 255).texture(minU, minV).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 3/16F, 1/16F, 17/32F).color(255, 255, 255, 255).texture(minU, maxV).overlay(overlay).light(light).normal(entry, 0, 0,1);

        client.getBufferBuilders().getEntityVertexConsumers().draw(renderLayer);

        matrices.pop();
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final CardModelRenderer.Unbaked INSTANCE = new CardModelRenderer.Unbaked();
        public static final MapCodec<CardModelRenderer.Unbaked> CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<CardModelRenderer.Unbaked> getCodec() {
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
            return new CardModelRenderer();
        }
    }
}
