package com.ninni.etcetera.platform.services;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegistryObject<T> extends Supplier<T> {
    ResourceLocation getId();

    T get();

    Holder<T> asHolder();
}
