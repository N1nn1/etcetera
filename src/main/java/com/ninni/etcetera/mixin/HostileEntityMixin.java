package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraStatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class HostileEntityMixin {

    @Inject(method = "isAngryAt", at = @At("HEAD"), cancellable = true)
    private void isPlayerUsingDreamCatcher(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!player.hasStatusEffect(EtceteraStatusEffects.DROWSY));
    }

}
