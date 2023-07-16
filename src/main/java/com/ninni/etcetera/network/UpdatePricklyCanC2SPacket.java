package com.ninni.etcetera.network;

import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdatePricklyCanC2SPacket {

    public UpdatePricklyCanC2SPacket() {
    }

    public static UpdatePricklyCanC2SPacket read(FriendlyByteBuf buf) {
        return new UpdatePricklyCanC2SPacket();
    }

    public static void write(UpdatePricklyCanC2SPacket packet, FriendlyByteBuf buf) {
    }

    public static void handle(UpdatePricklyCanC2SPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender.containerMenu instanceof PricklyCanScreenHandler pricklyCanScreenHandler) {
                pricklyCanScreenHandler.getContainer().clearContent();
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
