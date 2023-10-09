package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EtceteraParticleTypes {

    public static final DefaultParticleType GOLDEN_HEART = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Etcetera.MOD_ID, "golden_heart"), FabricParticleTypes.simple());
    public static final DefaultParticleType GOLDEN_SHEEN = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Etcetera.MOD_ID, "golden_sheen"), FabricParticleTypes.simple());
    public static final DefaultParticleType DRIPPING_RUBBER = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Etcetera.MOD_ID, "dripping_rubber"), FabricParticleTypes.simple());
    public static final DefaultParticleType FALLING_RUBBER = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Etcetera.MOD_ID, "falling_rubber"), FabricParticleTypes.simple());
    public static final DefaultParticleType LANDING_RUBBER = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Etcetera.MOD_ID, "landing_rubber"), FabricParticleTypes.simple());

}
