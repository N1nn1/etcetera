package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraStatusEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public class MonsterMixin {

    @Inject(method = "isPreventingPlayerRest", at = @At("HEAD"), cancellable = true)
    private void isPlayerUsingDreamCatcher(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!player.hasEffect(EtceteraStatusEffects.DROWSY));
    }
}