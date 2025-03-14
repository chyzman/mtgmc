package com.chyzman.mtgmc.blockentity.render;

import com.chyzman.mtgmc.block.CardBlock;
import com.chyzman.mtgmc.blockentity.CardBlockEntity;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.registry.MtgMcItems;
import io.wispforest.owo.ui.util.Delta;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.Random;

public class CardBlockEntityRenderer extends MtgMcBlockEntityRenderer<CardBlockEntity> {
    private static final Random RANDOM = new Random();

    public CardBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    protected void render(
            CardBlockEntity entity,
            float tickDelta,
            float frameDelta,
            long time,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        var client = MinecraftClient.getInstance();

        entity.tappedness += Delta.compute(entity.tappedness, entity.tapped ? 1f : 0f, frameDelta * 0.25f);

        var lookedAt = client.crosshairTarget.getType() == BlockHitResult.Type.BLOCK && client.crosshairTarget.getPos().squaredDistanceTo(entity.getPos().toCenterPos()) < 0.5f;
        entity.lookedAtness += Delta.compute(entity.lookedAtness, lookedAt ? 1f : 0f, frameDelta * 0.25f);

        matrices.push();

        matrices.translate(0.5, 0.5, 0.5);

        var scale = MathHelper.lerp(entity.tappedness, 0.68f, 0.5f);
        matrices.scale(scale, scale, scale);

        matrices.multiply(entity.getCachedState().get(CardBlock.FACING).getRotationQuaternion().rotateX((float) (Math.PI / -2)));

        RANDOM.setSeed(entity.getPos().hashCode());

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin((time + RANDOM.nextInt(1,10000)) / 2100f)));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.cos((time + RANDOM.nextInt(1,10000)) / 2300f)));
        matrices.translate(0, MathHelper.sin((time + RANDOM.nextInt(1,10000)) / 2500f) * 0.01f + (MathHelper.lerp(entity.lookedAtness, 0, 0.3f)), 0);

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(entity.lookedAtness, MathHelper.lerp(entity.tappedness, 85f, 90f), 85f)));

        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-MathHelper.lerp(entity.tappedness, 0f, 90f)));


        var stack = new ItemStack(MtgMcItems.CARD);
        stack.set(MtgMcComponents.CARD, entity.cardId);

        client.getItemRenderer().renderItem(
                stack,
                ModelTransformationMode.FIXED,
                light,
                overlay,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
        );

        matrices.pop();
    }
}
