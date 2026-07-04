package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.entity.effect.EtceteraStatusEffect;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EtceteraStatusEffects {
    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, Constants.MOD_ID);

    public static final Holder<MobEffect> DROWSY = register("drowsy", new EtceteraStatusEffect(MobEffectCategory.BENEFICIAL, 0x7D7D7D));

    private static Holder<MobEffect> register(String id, MobEffect effect) {
        MOB_EFFECTS.register(id, () -> effect);
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }

    public static void init() {
    }
}