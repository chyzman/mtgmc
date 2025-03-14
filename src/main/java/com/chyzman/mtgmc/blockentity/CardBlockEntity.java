package com.chyzman.mtgmc.blockentity;

import com.chyzman.mtgmc.api.card.CardIdentifier;
import com.chyzman.mtgmc.registry.MtgMcBlockEntities;
import com.chyzman.mtgmc.registry.MtgMcComponents;
import io.wispforest.owo.ops.WorldOps;
import io.wispforest.owo.serialization.format.nbt.NbtDeserializer;
import io.wispforest.owo.serialization.format.nbt.NbtSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CardBlockEntity extends BlockEntity implements InteractableBlockEntity {

    public CardIdentifier.ScryfallId cardId;
    public boolean tapped;

    @Environment(EnvType.CLIENT) public double time;
    @Environment(EnvType.CLIENT) public float tappedness;
    @Environment(EnvType.CLIENT) public float lookedAtness;

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
