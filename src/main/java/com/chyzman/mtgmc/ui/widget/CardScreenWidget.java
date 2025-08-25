package com.chyzman.mtgmc.ui.widget;

import com.chyzman.mtgmc.api.scryfall.card.MtgCard;
import io.wispforest.owo.braid.core.Alignment;
import io.wispforest.owo.braid.core.Insets;
import io.wispforest.owo.braid.framework.BuildContext;
import io.wispforest.owo.braid.framework.widget.StatelessWidget;
import io.wispforest.owo.braid.framework.widget.Widget;
import io.wispforest.owo.braid.widgets.basic.Align;
import io.wispforest.owo.braid.widgets.basic.Padding;
import io.wispforest.owo.braid.widgets.basic.Panel;
import io.wispforest.owo.braid.widgets.flex.Column;
import io.wispforest.owo.braid.widgets.flex.Flexible;
import io.wispforest.owo.braid.widgets.flex.Row;
import io.wispforest.owo.braid.widgets.label.Label;
import io.wispforest.owo.braid.widgets.stack.Stack;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import net.minecraft.util.math.BlockPos;

public class CardScreenWidget extends StatelessWidget {
    MtgCard card;
    BlockPos pos;

    public CardScreenWidget(MtgCard card, BlockPos pos) {
        this.card = card;
        this.pos = pos;
    }

    @Override
    public Widget build(BuildContext context) {
        return new Stack(
            new Align(
                Alignment.RIGHT,
                new Row(
                    new Flexible(2, new Padding(Insets.none())),
                    new Flexible(
                        new SlideInWidget(
                            new Panel(
                                OwoUIDrawContext.PANEL_NINE_PATCH_TEXTURE,
                                new Column(
                                    new Flexible(
                                        Label.literal(card.oracleText() == null ? "well the oracle text is null so ¯\\_(ツ)_/¯" : card.oracleText())
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
    }
}
