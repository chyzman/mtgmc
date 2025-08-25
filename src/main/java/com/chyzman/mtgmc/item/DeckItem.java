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
        var deck = stack.get(MtgMcComponents.DECK);
        if (deck == null) return;
        tooltip.add(Text.translatable("item.mtgmc.deck_size", deck.sections().stream().mapToInt(s -> s.cards().size()).sum()));
    }

    @Override
    public Text getName(ItemStack stack) {
        var deck = stack.get(MtgMcComponents.DECK);
        if (deck == null) return super.getName(stack);
        return Text.literal(deck.name());
    }

    //    @Override
//    public ActionResult use(World world, PlayerEntity user, Hand hand) {
//        ItemStack stack = user.getStackInHand(hand);
//        if (!stack.contains(MtgMcComponents.DECK) || stack.get(MtgMcComponents.DECK) == null || stack.get(MtgMcComponents.DECK).isEmpty()) {
//            user.getInventory().removeOne(stack);
//            return ActionResult.PASS;
//        }
//        var deck = stack.get(MtgMcComponents.DECK);
//        var cardStack = MtgMcItems.CARD.getDefaultStack();
//        cardStack.set(MtgMcComponents.CARD, deck.removeFirst());
//        user.getInventory().offerOrDrop(cardStack);
//        if (deck.isEmpty()) user.getInventory().removeOne(stack);
//        return ActionResult.SUCCESS;
//    }
}
