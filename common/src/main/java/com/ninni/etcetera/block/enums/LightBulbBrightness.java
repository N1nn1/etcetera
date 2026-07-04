package com.ninni.etcetera.block.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LightBulbBrightness implements StringRepresentable {
    OFF("off"),
    DARK("dark"),
    DIM("dim"),
    BRIGHT("bright");

    private final String name;

    LightBulbBrightness(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}