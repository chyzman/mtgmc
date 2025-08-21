package com.chyzman.mtgmc.mixin.client;

import com.chyzman.mtgmc.blockentity.render.CardBlockEntityRenderer;
import com.chyzman.mtgmc.client.MtgMcClient;
import io.wispforest.owo.ui.util.Delta;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "getFov", at = @At(value = "RETURN"), cancellable = true)
    private void lerpFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Float> cir) {
        var shouldLerp = CardBlockEntityRenderer.clickedPos != null;
        MtgMcClient.fovLerp += Delta.compute(MtgMcClient.fovLerp, shouldLerp ? 1 : 0, tickDelta * 0.05f);
        cir.setReturnValue(MathHelper.lerp(MtgMcClient.fovLerp, cir.getReturnValueF(), 70));
    }
}
