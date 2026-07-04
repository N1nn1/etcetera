package com.ninni.etcetera.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import com.ninni.etcetera.platform.Services;
import java.util.Collection;
import java.util.function.Supplier;

public interface RegistrationProvider<T> {
    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> registry, String modId) {
        return Services.PLATFORM.createRegistrationProvider(registry, modId);
    }

    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    Collection<RegistryObject<T>> getEntries();
}
