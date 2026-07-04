package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class EtceteraParticleTypes {
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registries.PARTICLE_TYPE, Constants.MOD_ID);

    public static final SimpleParticleType GOLDEN_HEART = register("golden_heart", new SimpleParticleType(false) {
    });
    public static final SimpleParticleType GOLDEN_SHEEN = register("golden_sheen", new SimpleParticleType(false) {
    });
    public static final SimpleParticleType DRIPPING_RUBBER = register("dripping_rubber", new SimpleParticleType(false) {
    });
    public static final SimpleParticleType FALLING_RUBBER = register("falling_rubber", new SimpleParticleType(false) {
    });
    public static final SimpleParticleType LANDING_RUBBER = register("landing_rubber", new SimpleParticleType(false) {
    });

    private static <T extends ParticleType<?>> T register(String id, T particleType) {
        PARTICLE_TYPES.register(id, () -> particleType);
        return particleType;
    }

    public static void init() {
    }
}