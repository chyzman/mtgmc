package com.chyzman.mtgmc.item.renderer;

import com.chyzman.mtgmc.MtgMc;
import com.chyzman.mtgmc.api.card.MtgCard;
import com.chyzman.mtgmc.card.cache.CardCache;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.wispforest.owo.Owo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static com.chyzman.mtgmc.client.MtgMcClient.CLIENT_CARD_CACHE;

@Environment(EnvType.CLIENT)
public class CardModelRenderer implements SpecialModelRenderer<@Nullable MtgCard> {

    @Override
    public @Nullable MtgCard getData(ItemStack stack) {
        var server = Owo.currentServer();
        if (server == null) return null;

        CardCache cache = server.getOverworld().isClient() ? CLIENT_CARD_CACHE : MtgMc.SERVER_CARD_CACHE;

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

        var image = MtgMc.id("item/unknown_card");

        if (card != null) {
            var future = CLIENT_CARD_CACHE.getImage(card);
            if (future != null) image = future.getNow(image);
        }

        matrices.push();

        var entry = matrices.peek();
        var matrixStack = entry.getPositionMatrix();
        var renderLayer = RenderLayer.getItemEntityTranslucentCull(image);
        var bufferBuilder = vertexConsumers.getBuffer(renderLayer);

        bufferBuilder.vertex(matrixStack, 13/16F, 1/16F, 17/32F).color(255, 255, 255, 255).texture(1, 1).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 13/16F, 15/16F, 17/32F).color(255, 255, 255, 255).texture(1, 0).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 3/16F, 15/16F, 17/32F).color(255, 255, 255, 255).texture(0, 0).overlay(overlay).light(light).normal(entry, 0, 0,1);
        bufferBuilder.vertex(matrixStack, 3/16F, 1/16F, 17/32F).color(255, 255, 255, 255).texture(0, 1).overlay(overlay).light(light).normal(entry, 0, 0,1);

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
