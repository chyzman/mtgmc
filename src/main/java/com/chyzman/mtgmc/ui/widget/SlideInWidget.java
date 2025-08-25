package com.chyzman.mtgmc.ui.widget;

import io.wispforest.owo.braid.animation.Animation;
import io.wispforest.owo.braid.core.AppState;
import io.wispforest.owo.braid.framework.BuildContext;
import io.wispforest.owo.braid.framework.proxy.WidgetState;
import io.wispforest.owo.braid.framework.widget.StatefulWidget;
import io.wispforest.owo.braid.framework.widget.Widget;
import io.wispforest.owo.braid.widgets.basic.Transform;
import io.wispforest.owo.ui.core.Easing;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.joml.Matrix4f;

import java.time.Duration;

public class SlideInWidget extends StatefulWidget {

    public final Widget child;

    public SlideInWidget(Widget child) {
        this.child = child;
    }

    @Override
    public WidgetState<SlideInWidget> createState() {
        return new State();
    }

    public static class State extends WidgetState<SlideInWidget> {

        Animation animation;
        float offsetX = 1000000;

        @Override
        public void init() {
            animation = new Animation(
                Easing.EXPO,
                Duration.ofMillis(800),
                this::scheduleAnimationCallback,
                v -> this.setState(() -> {
                    offsetX = (float) ((AppState.of(context()).surface.width() - context().instance().computeGlobalBounds().minX) * (1 - v));}),
                Animation.Target.START
            );
            schedulePostLayoutCallback(() -> animation.towards(Animation.Target.END));
        }

        @Override
        public Widget build(BuildContext context) {
            return new Transform(
                new Matrix4f().translate(offsetX, 0,0),
                this.widget().child
            );
        }
    }
}
