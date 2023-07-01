package com.ninni.etcetera.network;

import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdatePricklyCanC2SPacket implements ServerPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (player.currentScreenHandler instanceof PricklyCanScreenHandler pricklyCanScreenHandler) {
            pricklyCanScreenHandler.getInventory().clear();
        }
    }
}
