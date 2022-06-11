package com.ninni.etcetera.block.enums;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.StringIdentifiable;

public enum EtceteraInstrument implements StringIdentifiable {
    BEATBOX("beatbox", EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_HIGH, EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_MEDIUM, EtceteraSoundEvents.BLOCK_PUMPKIN_DRUM_LOW),
    DARBUKA("darbuka", EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_HIGH, EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_MEDIUM, EtceteraSoundEvents.BLOCK_GOLD_BLOCK_DRUM_LOW),
    DHOLAK("dholak", EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_HIGH, EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_MEDIUM, EtceteraSoundEvents.BLOCK_IRON_BLOCK_DRUM_LOW),
    DJEMBE("djembe", EtceteraSoundEvents.BLOCK_DRUM_HIGH, EtceteraSoundEvents.BLOCK_DRUM_MEDIUM, EtceteraSoundEvents.BLOCK_DRUM_LOW),
    TABLA("tabla", EtceteraSoundEvents.BLOCK_SAND_DRUM_HIGH, EtceteraSoundEvents.BLOCK_SAND_DRUM_MEDIUM, EtceteraSoundEvents.BLOCK_SAND_DRUM_LOW);

    private final String name;
    private final SoundEvent highSound;
    private final SoundEvent mediumSound;
    private final SoundEvent lowSound;

    EtceteraInstrument(String name, SoundEvent highSound, SoundEvent mediumSound, SoundEvent lowSound) {
        this.name = name;
        this.highSound = highSound;
        this.lowSound = lowSound;
        this.mediumSound = mediumSound;
    }

    @Override public String asString() { return this.name; }
    public SoundEvent getHighSound() { return this.highSound; }
    public SoundEvent getMediumSound() { return this.mediumSound; }
    public SoundEvent getLowSound() { return this.lowSound; }

    public static EtceteraInstrument fromBlockState(BlockState state) {
        if (state.isOf(Blocks.PUMPKIN)) {
            return BEATBOX;
        }else if (state.isOf(Blocks.GOLD_BLOCK)) {
            return DARBUKA;
        }else if (state.isOf(Blocks.IRON_BLOCK)) {
            return DHOLAK;
        }else if (state.isOf(Blocks.SAND)) {
            return TABLA;
        }else {
            return DJEMBE;
        }
    }
}
