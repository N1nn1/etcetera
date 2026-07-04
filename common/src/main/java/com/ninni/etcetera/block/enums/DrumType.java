package com.ninni.etcetera.block.enums;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum DrumType implements StringRepresentable {
    CAJON("cajon"),
    CHIPTUNE("chiptune"),
    DRUMSET("drumset"),
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
    public @NotNull String getSerializedName() {
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
        if (state.instrument() == NoteBlockInstrument.BASS) return CAJON;
        if (state.is(Blocks.EMERALD_BLOCK)) return CHIPTUNE;
        if (state.instrument() == NoteBlockInstrument.BASEDRUM) return DRUMSET;
        if (state.is(Blocks.CARVED_PUMPKIN)) return BEATBOX;
        if (state.is(Blocks.GOLD_BLOCK)) return DARBUKA;
        if (state.is(Blocks.IRON_BLOCK))  return DHOLAK;
        if (state.is(Blocks.SAND)) return TABLA;
        return DJEMBE;
    }
}