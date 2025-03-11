package com.chyzman.mtgmc.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class DumpScreen extends BaseOwoScreen<FlowLayout> {
    private final Text title;
    private final Text dump;

    public DumpScreen(Text title, Text dump) {
        super();
        this.title = title;
        this.dump = dump;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.child(
                Containers.stack(
                                Sizing.fill(),
                                Sizing.fill()
                        )
                        .child(
                                Containers.horizontalScroll(
                                        Sizing.fill(),
                                        Sizing.fill(),
                                        Containers.verticalScroll(
                                                        Sizing.fill(),
                                                        Sizing.fill(),
                                                        Containers.verticalFlow(
                                                                        Sizing.fill(),
                                                                        Sizing.content()
                                                                ).child(
                                                                        Components.label(dump)
                                                                                .horizontalSizing(Sizing.fill())
                                                                )
                                                                .horizontalAlignment(HorizontalAlignment.CENTER)
                                                )
                                                .allowOverflow(true)
                                                .padding(Insets.horizontal(4).withTop(17).withBottom(28))
                                )
                        )
                        .child(
                                Containers.verticalFlow(
                                                Sizing.fill(),
                                                Sizing.fixed(13)
                                        ).child(
                                                Components.label(title)
                                        )
                                        .horizontalAlignment(HorizontalAlignment.CENTER)
                                        .surface(Surface.blur(4, 6).and(Surface.VANILLA_TRANSLUCENT))
                                        .padding(Insets.vertical(2))
                                        .positioning(Positioning.relative(0, 0))
                                        .zIndex(20)
                        )
                        .child(
                                Containers.verticalFlow(
                                                Sizing.fill(),
                                                Sizing.fixed(24)
                                        ).child(Components.button(
                                                                Text.translatable("ui.copy"),
                                                                component -> {
                                                                    MinecraftClient.getInstance().keyboard.setClipboard(dump.getString());
                                                                    MinecraftClient.getInstance().player.sendMessage(Text.translatable("ui.copy.success"), true);
                                                                }
                                                        )
                                                        .horizontalSizing(Sizing.fill(50))
                                        )
                                        .horizontalAlignment(HorizontalAlignment.CENTER)
                                        .surface(Surface.blur(4, 6).and(Surface.VANILLA_TRANSLUCENT))
                                        .padding(Insets.vertical(2))
                                        .positioning(Positioning.relative(0, 100))
                                        .zIndex(20)
                        )
                        .horizontalAlignment(HorizontalAlignment.CENTER)
                        .surface(Surface.VANILLA_TRANSLUCENT)
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
