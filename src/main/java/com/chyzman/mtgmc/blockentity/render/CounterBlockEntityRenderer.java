package com.chyzman.mtgmc.blockentity.render;

import com.chyzman.mtgmc.blockentity.CounterBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Colors;

public class CounterBlockEntityRenderer extends MtgMcBlockEntityRenderer<CounterBlockEntity> {

    public CounterBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    protected void render(
            CounterBlockEntity entity,
            float tickDelta,
            float frameDelta,
            long time,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        var client = MinecraftClient.getInstance();

        var text = entity.count.toString();

        matrices.push();

        matrices.translate(0.5, 1.5, 0.5);

        matrices.multiply(client.getEntityRenderDispatcher().getRotation());

        matrices.scale(0.025F, -0.025F, 0.025F);

        TextRenderer textRenderer = client.textRenderer;

        textRenderer.draw(
                text,
                -textRenderer.getWidth(text) / 2.0F,
                0,
                -2130706433,
                false,
                matrices.peek().getPositionMatrix(),
                vertexConsumers,
                TextRenderer.TextLayerType.SEE_THROUGH,
                (int) (client.options.getTextBackgroundOpacity(0.25F) * 255.0F * 2f) << 24,
                LightmapTextureManager.applyEmission(light, 2)
        );

        textRenderer.draw(
                text,
                -textRenderer.getWidth(text) / 2.0F,
                0,
                Colors.WHITE,
                false,
                matrices.peek().getPositionMatrix(),
                vertexConsumers,
                TextRenderer.TextLayerType.NORMAL,
                0,
                LightmapTextureManager.applyEmission(light, 2)
        );

        matrices.pop();
    }
}
