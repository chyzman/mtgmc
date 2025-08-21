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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

public class CardBlockEntityRenderer extends MtgMcBlockEntityRenderer<CardBlockEntity> {
    private static final Random RANDOM = new Random();

    @Nullable
    public static BlockPos clickedPos = null;


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
        matrices.push();

        var client = MinecraftClient.getInstance();
        var camera = client.gameRenderer.getCamera();

        var selected = clickedPos != null && clickedPos.equals(entity.getPos());

        Vec3d targetPos = Vec3d.ZERO;
        Quaternionf targetRot;
        Vec3d targetScale = new Vec3d(1, 1, 1);

        entity.tappedness += Delta.compute(entity.tappedness, entity.tapped ? 1f : 0f, frameDelta * 0.5f);

        if (selected) {
//            if (new Vector3f(0, 0, -1).rotate(camera.getRotation()).distance(entity.getPos().toCenterPos().subtract(camera.getPos()).normalize().toVector3f()) > 1f) {
//                clickedPos = null;
//            }

            var offset = new Vector3f(0, 0, -0.75f).rotate(camera.getRotation());
            targetPos = entity.getPos().toCenterPos().subtract(camera.getPos()).multiply(-1).add(offset.x, offset.y, offset.z);

            targetRot = camera.getRotation().rotateY(-((float) Math.PI));

            var scale = 0.5f;
            targetScale = new Vec3d(scale, scale, scale);
        } else {
            targetRot = entity.getCachedState().get(CardBlock.FACING).getRotationQuaternion()
                    .rotateX((float) -Math.toRadians(5));

            var scale = MathHelper.lerp(entity.tappedness, 1f, 0.7f);
            targetScale = new Vec3d(scale, scale, scale);
        }

        targetRot.rotateZ(entity.tappedness * (float) -Math.PI/2f);

        entity.position = MathHelper.lerp(frameDelta * 0.25f, entity.position, targetPos);
        entity.rotation = entity.rotation.nlerp(targetRot, frameDelta * 0.25f);
        entity.scale = MathHelper.lerp(frameDelta * 0.25f, entity.scale, targetScale);

        matrices.translate(0.5, 0.5, 0.5);

        matrices.translate(entity.position);
        matrices.multiply(entity.rotation);
        matrices.scale((float) entity.scale.x, (float) entity.scale.y, (float) entity.scale.z);

        matrices.scale(0.65f, 0.65f, 0.65f);

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

//        var lookedAt = !client.options.hudHidden && client.crosshairTarget.getType() == BlockHitResult.Type.BLOCK && client.crosshairTarget.getPos().squaredDistanceTo(entity.getPos().toCenterPos()) < 0.5f;
//        entity.lookedAtness += Delta.compute(entity.lookedAtness, selected ? 1f : 0f, frameDelta * 0.25f);

//        var targetPos = Vec3d.ZERO;
//
//        if (selected) {
//            var offset = new Vector3f(-0.75f, 0, -1).rotate(camera.getRotation());
//            targetPos = entity.getPos().toCenterPos().subtract(camera.getPos()).multiply(-1).add(offset.x, offset.y, offset.z);
//        }
//
//        entity.targetPos = MathHelper.lerp(frameDelta * 0.25f, entity.targetPos, targetPos);
//
//        var targetRot = new Quaternionf();
//
//        if (selected) {
//            targetRot = camera.getRotation();
//        }
//
//        entity.targetRot = entity.targetRot.nlerp(targetRot, frameDelta * 0.25f);

//        matrices.push();
//
//        matrices.translate(0.5, 0.5, 0.5);

//        matrices.translate(entity.targetPos.getX(), entity.targetPos.getY(), entity.targetPos.getZ());
//
//        matrices.multiply(entity.targetRot);

//        var scale = MathHelper.lerp(entity.tappedness, 0.68f, 0.5f);
//        matrices.scale(scale, scale, scale);


//        RANDOM.setSeed(entity.getPos().hashCode());
//
//        matrices.translate(0, MathHelper.sin((time + RANDOM.nextInt(1,10000)) / 2500f) * 0.01f + (MathHelper.lerp(entity.lookedAtness, 0, 0.4f)), 0);
//
//        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin((time + RANDOM.nextInt(1,10000)) / 2100f)));
//        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.cos((time + RANDOM.nextInt(1,10000)) / 2300f)));
//
//        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(entity.tappedness, 85f, 90f)));
//
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-MathHelper.lerp(entity.tappedness, 0f, 90f)));

    }
}
