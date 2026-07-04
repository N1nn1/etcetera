package com.ninni.etcetera.network;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record UpdatePricklyCanC2SPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdatePricklyCanC2SPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "update_prickly_can"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdatePricklyCanC2SPacket> STREAM_CODEC = StreamCodec.unit(new UpdatePricklyCanC2SPacket());

    @Override
    public CustomPacketPayload.@NotNull Type<UpdatePricklyCanC2SPacket> type() {
        return TYPE;
    }

    public void handle(ServerPlayer player) {
        if (player.containerMenu instanceof PricklyCanScreenHandler pricklyCanScreenHandler) {
            pricklyCanScreenHandler.getInventory().clearContent();
        }
    }
}