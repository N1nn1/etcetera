package com.ninni.etcetera.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class EtceteraBlockSoundGroups {

    public static final BlockSoundGroup NETHER_BISMUTH_ORE = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_NETHER_BISMUTH_ORE_BREAK,
        EtceteraSoundEvents.BLOCK_NETHER_BISMUTH_ORE_STEP,
        EtceteraSoundEvents.BLOCK_NETHER_BISMUTH_ORE_PLACE,
        EtceteraSoundEvents.BLOCK_NETHER_BISMUTH_ORE_HIT,
        EtceteraSoundEvents.BLOCK_NETHER_BISMUTH_ORE_FALL
    );

    public static final BlockSoundGroup PACKED_SILT = new BlockSoundGroup(
        1.0F, 1.0F,

        SoundEvents.BLOCK_STONE_BREAK,
        SoundEvents.BLOCK_STONE_STEP,
        SoundEvents.BLOCK_STONE_PLACE,
        SoundEvents.BLOCK_STONE_HIT,
        SoundEvents.BLOCK_STONE_FALL
    );

    public static final BlockSoundGroup BISMUTH_BLOCK = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_BISMUTH_BLOCK_BREAK,
        EtceteraSoundEvents.BLOCK_BISMUTH_BLOCK_STEP,
        EtceteraSoundEvents.BLOCK_BISMUTH_BLOCK_PLACE,
        EtceteraSoundEvents.BLOCK_BISMUTH_BLOCK_HIT,
        EtceteraSoundEvents.BLOCK_BISMUTH_BLOCK_FALL
    );

    public static final BlockSoundGroup GRAVEL_BRICKS = new BlockSoundGroup(
        1.0F, 1.0F,

        EtceteraSoundEvents.BLOCK_GRAVEL_BRICKS_BREAK,
        EtceteraSoundEvents.BLOCK_GRAVEL_BRICKS_STEP,
        EtceteraSoundEvents.BLOCK_GRAVEL_BRICKS_PLACE,
        EtceteraSoundEvents.BLOCK_GRAVEL_BRICKS_HIT,
        EtceteraSoundEvents.BLOCK_GRAVEL_BRICKS_FALL
    );


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
