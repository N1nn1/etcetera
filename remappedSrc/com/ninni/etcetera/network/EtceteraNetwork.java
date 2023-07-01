package com.ninni.etcetera.network;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class EtceteraNetwork {

    public static final Identifier UPDATE_PRICKLY_CAN = new Identifier(Etcetera.MOD_ID, "update_prickly_can");

    public static void initCommon() {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_PRICKLY_CAN, new UpdatePricklyCanC2SPacket());
    }

}
