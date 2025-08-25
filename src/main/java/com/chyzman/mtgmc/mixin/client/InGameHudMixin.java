package com.chyzman.mtgmc.mixin.client;

import com.chyzman.mtgmc.item.CardItem;
import com.chyzman.mtgmc.ui.screen.CardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderCrosshair", at = @At(value = "HEAD"), cancellable = true)
    private void noCrosshairInCardUi(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.currentScreen instanceof CardScreen) ci.cancel();
    }
}
