package com.chyzman.mtgmc.item;

import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.registry.MtgMcItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class DeckItem extends Item {
    public DeckItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (!stack.contains(MtgMcComponents.DECK) || stack.get(MtgMcComponents.DECK) == null) return;
        tooltip.add(Text.translatable("item.mtgmc.deck_size", stack.get(MtgMcComponents.DECK).size()));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!stack.contains(MtgMcComponents.DECK) && stack.get(MtgMcComponents.DECK) == null) return ActionResult.PASS;
        var deck = stack.get(MtgMcComponents.DECK);
        var cardStack = MtgMcItems.CARD.getDefaultStack();
        cardStack.set(MtgMcComponents.CARD, deck.removeFirst());
        user.getInventory().offerOrDrop(cardStack);
        return ActionResult.SUCCESS;
    }
}
