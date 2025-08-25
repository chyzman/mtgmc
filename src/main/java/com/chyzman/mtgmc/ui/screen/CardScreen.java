package com.chyzman.mtgmc.ui.screen;

import com.chyzman.mtgmc.api.scryfall.card.MtgCard;
import com.chyzman.mtgmc.blockentity.render.CardBlockEntityRenderer;
import com.chyzman.mtgmc.ui.widget.CardScreenWidget;
import io.wispforest.owo.braid.core.BraidScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;

public class CardScreen extends BraidScreen {

    public CardScreen(MtgCard card, BlockPos pos) {
        super(new CardScreenWidget(card, pos));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void applyBlur() {
    }

    @Override
    protected void renderDarkening(DrawContext context) {
    }

    @Override
    public void removed() {
        super.removed();
        CardBlockEntityRenderer.clickedPos = null;
    }
}
