package com.ninni.etcetera.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

public interface EtceteraSoundEvents {
    
    SoundEvent BLOCK_SQUID_LAMP_BREAK = squid_lamp("break");
    SoundEvent BLOCK_SQUID_LAMP_STEP  = squid_lamp("step");
    SoundEvent BLOCK_SQUID_LAMP_PLACE = squid_lamp("place");
    SoundEvent BLOCK_SQUID_LAMP_HIT   = squid_lamp("hit");
    SoundEvent BLOCK_SQUID_LAMP_FALL  = squid_lamp("fall");
    private static SoundEvent squid_lamp(String type) { return createBlockSound("squid_lamp", type); }


    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent createBlockSound(String block, String type) {
        return register("block." + block + "." + type);
    }
}
