package com.chyzman.mtgmc.block;

import com.chyzman.mtgmc.blockentity.CardBlockEntity;
import com.chyzman.mtgmc.blockentity.InteractableBlockEntity;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.registry.MtgMcItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CardBlock extends BlockWithEntity {

    public static final MapCodec<CardBlock> CODEC = createCodec(CardBlock::new);

    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 10, 16);

    public CardBlock(Settings settings) {
        super(settings);

        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }


    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx)
                .with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CardBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        return InteractableBlockEntity.tryHandle(world, pos, player, Hand.MAIN_HAND, hit);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        var stack = MtgMcItems.CARD.getDefaultStack();
        if (world.getBlockEntity(pos) instanceof CardBlockEntity cardBlockEntity) stack.set(MtgMcComponents.CARD, cardBlockEntity.cardId);
        return stack;
    }

    @Override
    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof CardBlockEntity cardBlockEntity) {
            var stack = MtgMcItems.CARD.getDefaultStack();
            stack.set(MtgMcComponents.CARD, cardBlockEntity.cardId);
            player.getInventory().offerOrDrop(stack);
            world.removeBlock(pos, false);
        } else {
            super.onBlockBreakStart(state, world, pos, player);
        }
    }
}
