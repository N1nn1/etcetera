package com.ninni.etcetera.registry;

import com.ninni.etcetera.entity.effect.EtceteraStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class EtceteraStatusEffects {

    public static final StatusEffect DROWSY = register("drowsy", new EtceteraStatusEffect(StatusEffectCategory.BENEFICIAL, 0x7D7D7D));

    private static StatusEffect register(String id, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, id), effect);
    }
}
