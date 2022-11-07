package com.ninni.etcetera.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum EtceteraColumnShape implements StringIdentifiable {
    TIP("tip"),
    BASE("base");

    private final String name;

    EtceteraColumnShape(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
