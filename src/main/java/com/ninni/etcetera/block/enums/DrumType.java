package com.ninni.etcetera.block.enums;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.StringIdentifiable;

public enum DrumType implements StringIdentifiable {
    BEATBOX("beatbox"),
    DARBUKA("darbuka"),
    DHOLAK("dholak"),
    DJEMBE("djembe"),
    TABLA("tabla");

    private final String name;
    private final SoundEvent highSound;
    private final SoundEvent mediumSound;
    private final SoundEvent lowSound;

    DrumType(String name) {
        this.name = name;

        String prefix = "block.drum." + name + ".";

        highSound = EtceteraSoundEvents.register(prefix + "high");
        mediumSound = EtceteraSoundEvents.register(prefix + "medium");
        lowSound = EtceteraSoundEvents.register(prefix + "low");
    }

    @Override
    public String asString() {
        return name;
    }

    public SoundEvent getHighSound() {
        return highSound;
    }

    public SoundEvent getMediumSound() {
        return mediumSound;
    }

    public SoundEvent getLowSound() {
        return lowSound;
    }

    public static DrumType fromBlockState(BlockState state) {
        if (state.isOf(Blocks.PUMPKIN)) return BEATBOX;
        if (state.isOf(Blocks.GOLD_BLOCK)) return DARBUKA;
        if (state.isOf(Blocks.IRON_BLOCK))  return DHOLAK;
        if (state.isOf(Blocks.SAND)) return TABLA;
        return DJEMBE;
    }
}