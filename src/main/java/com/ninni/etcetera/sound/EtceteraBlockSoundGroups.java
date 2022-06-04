package com.ninni.etcetera.sound;

import net.minecraft.sound.BlockSoundGroup;

public class EtceteraBlockSoundGroups {

    public static final BlockSoundGroup SQUID_LAMP = new BlockSoundGroup(
        1F, 1.0F,

        EtceteraSoundEvents.BLOCK_SQUID_LAMP_BREAK,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_STEP,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_PLACE,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_HIT,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_FALL
    );

}
