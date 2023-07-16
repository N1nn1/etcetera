package com.ninni.etcetera.block.enums;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public enum DrumType implements StringRepresentable {
    BEATBOX("beatbox"),
    DARBUKA("darbuka"),
    DHOLAK("dholak"),
    DJEMBE("djembe"),
    TABLA("tabla");

    private final String name;
//    private final RegistryObject<SoundEvent> highSound;
//    private final RegistryObject<SoundEvent> mediumSound;
//    private final RegistryObject<SoundEvent> lowSound;

    DrumType(String name) {
        this.name = name;

        String prefix = "block.drum." + name + ".";

//        highSound = EtceteraSoundEvents.register(prefix + "high");
//        mediumSound = EtceteraSoundEvents.register(prefix + "medium");
//        lowSound = EtceteraSoundEvents.register(prefix + "low");
    }

//    public RegistryObject<SoundEvent> getHighSound() {
//        return highSound;
//    }
//
//    public RegistryObject<SoundEvent> getMediumSound() {
//        return mediumSound;
//    }
//
//    public RegistryObject<SoundEvent> getLowSound() {
//        return lowSound;
//    }

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