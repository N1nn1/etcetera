package com.ninni.etcetera.block.enums;

import com.ninni.etcetera.Etcetera;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public enum DrumType implements StringRepresentable {
    BEATBOX("beatbox"),
    DARBUKA("darbuka"),
    DHOLAK("dholak"),
    DJEMBE("djembe"),
    TABLA("tabla");

    private final String name;

    DrumType(String name) {
        this.name = name;
    }

    public SoundEvent getHighSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(Etcetera.MOD_ID, "block.drum." + name + ".high"));
    }

    public SoundEvent getMediumSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(Etcetera.MOD_ID, "block.drum." + name + ".medium"));
    }

    public SoundEvent getLowSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(Etcetera.MOD_ID, "block.drum." + name + ".low"));
    }

    public static DrumType fromBlockState(BlockState state) {
        if (state.is(Blocks.PUMPKIN)) return BEATBOX;
        if (state.is(Blocks.GOLD_BLOCK)) return DARBUKA;
        if (state.is(Blocks.IRON_BLOCK))  return DHOLAK;
        if (state.is(Blocks.SAND)) return TABLA;
        return DJEMBE;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}