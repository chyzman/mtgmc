package com.chyzman.mtgmc.blockentity;

import com.chyzman.mtgmc.api.scryfall.card.CardIdentifier;
import com.chyzman.mtgmc.blockentity.render.CardBlockEntityRenderer;
import com.chyzman.mtgmc.registry.MtgMcBlockEntities;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import com.chyzman.mtgmc.screen.CardScreen;
import io.wispforest.owo.ops.WorldOps;
import io.wispforest.owo.serialization.format.nbt.NbtDeserializer;
import io.wispforest.owo.serialization.format.nbt.NbtSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class CardBlockEntity extends BlockEntity implements InteractableBlockEntity {

    public CardIdentifier.ScryfallId cardId;
    public boolean tapped;

    @Environment(EnvType.CLIENT) public float tappedness;
    @Environment(EnvType.CLIENT) public float lookedAtness;

    @Environment(EnvType.CLIENT) public Vec3d position = Vec3d.ZERO;
    @Environment(EnvType.CLIENT) public Quaternionf rotation = new Quaternionf();
    @Environment(EnvType.CLIENT) public Vec3d scale = new Vec3d(1, 1, 1);

    public CardBlockEntity(BlockPos pos, BlockState state) {
        super(MtgMcBlockEntities.CARD, pos, state);
    }

    @Override
    protected void addComponents(ComponentMap.Builder components) {
        super.addComponents(components);

        components.add(MtgMcComponents.CARD, cardId);
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);

        this.cardId = components.get(MtgMcComponents.CARD);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return this.createNbt(registries);
    }

    @Override
    public ActionResult onUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        this.tapped = !this.tapped;
        this.markDirty();
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult onAttack(World world, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            CardBlockEntityRenderer.clickedPos = CardBlockEntityRenderer.clickedPos == this.getPos() ? null : this.getPos();
            if (CardBlockEntityRenderer.clickedPos != null) {
                MinecraftClient.getInstance().setScreen(new CardScreen(this.getPos()));
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
//        if (world.isClient) return ActionResult.SUCCESS;
//
//        var stack = MtgMcItems.CARD.getDefaultStack();
//        stack.set(MtgMcComponents.CARD, this.cardId);
//        PlayerUtil.shoveStackIntoHotbarNicely(player, stack);
//        world.removeBlock(hit.getBlockPos(), false);
//
//        return ActionResult.SUCCESS;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.cardId = nbt.contains("cardId") ? CardIdentifier.ScryfallId.ENDEC.decodeFully(NbtDeserializer::of, nbt.getCompound("cardId")) : null;
        this.tapped = nbt.getBoolean("tapped");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        if (this.cardId != null) nbt.put("cardId", CardIdentifier.ScryfallId.ENDEC.encodeFully(NbtSerializer::of, this.cardId));
        nbt.putBoolean("tapped", this.tapped);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        WorldOps.updateIfOnServer(world, pos);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
