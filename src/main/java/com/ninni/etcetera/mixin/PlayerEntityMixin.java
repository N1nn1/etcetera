package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

    @Inject(method = "updateTurtleHelmet", at = @At("HEAD"))
    private void updateTidalHelmet(CallbackInfo ci) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(EtceteraItems.TIDAL_HELMET) && !this.isSubmergedIn(FluidTags.WATER))
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 20 * 90, 0, false, false, false));
    }
}
