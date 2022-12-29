package com.ninni.etcetera.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum LightBulbBrightness implements StringIdentifiable {
    OFF("off"),
    DARK("dark"),
    DIM("dim"),
    BRIGHT("bright");

    private final String name;

    LightBulbBrightness(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
