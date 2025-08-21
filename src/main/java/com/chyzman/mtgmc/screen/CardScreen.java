package com.chyzman.mtgmc.screen;

import com.chyzman.mtgmc.blockentity.render.CardBlockEntityRenderer;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class CardScreen extends BaseOwoScreen<FlowLayout> {
    private final BlockPos cardPos;

    public CardScreen(BlockPos cardPos) {
        super();
        this.cardPos = cardPos;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent.child(
                        Containers.verticalFlow(Sizing.fill(100 / 3), Sizing.fill())
                                .child(Containers.verticalFlow(Sizing.fill(80), Sizing.content())
                                               .child(Components.button(
                                                              Text.translatable("ui.mtgmc.card.tap"),
                                                              button -> {}
                                                      )
                                               )
                                               .padding(Insets.of(4))
                                               .surface(Surface.PANEL)
                                )
                                .alignment(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
                )
                .horizontalAlignment(HorizontalAlignment.RIGHT);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void removed() {
        super.removed();
        CardBlockEntityRenderer.clickedPos = null;
    }
}
