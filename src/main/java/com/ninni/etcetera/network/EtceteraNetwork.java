package com.ninni.etcetera.network;

import com.ninni.etcetera.Etcetera;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class EtceteraNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    new ResourceLocation(Etcetera.MOD_ID, "network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    protected static int packetID = 0;

    public EtceteraNetwork() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), UpdatePricklyCanC2SPacket.class, UpdatePricklyCanC2SPacket::write, UpdatePricklyCanC2SPacket::read, UpdatePricklyCanC2SPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    public static int getPacketID() {
        return packetID++;
    }

}
