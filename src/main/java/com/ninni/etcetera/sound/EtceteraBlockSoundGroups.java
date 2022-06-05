package com.ninni.etcetera.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class EtceteraBlockSoundGroups {

    public static final BlockSoundGroup SQUID_LAMP = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_SQUID_LAMP_BREAK,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_STEP,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_PLACE,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_HIT,
        EtceteraSoundEvents.BLOCK_SQUID_LAMP_FALL
    );

    public static final BlockSoundGroup TERRACOTTA_VASE = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_TERRACOTTA_VASE_BREAK,
        SoundEvents.BLOCK_ROOTED_DIRT_STEP,
        SoundEvents.BLOCK_STONE_PLACE,
        SoundEvents.BLOCK_STONE_HIT,
        SoundEvents.BLOCK_ROOTED_DIRT_FALL
    );

    public static final BlockSoundGroup CRUMBLING_STONE = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_CRUMBLING_STONE_CRUMBLE,
        SoundEvents.BLOCK_STONE_STEP,
        SoundEvents.BLOCK_STONE_PLACE,
        SoundEvents.BLOCK_STONE_HIT,
        SoundEvents.BLOCK_STONE_FALL
    );
}
