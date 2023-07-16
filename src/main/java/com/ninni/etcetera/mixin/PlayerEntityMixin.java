package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot p_36257_);

    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) { super(entityType, world); }

    @Inject(method = "turtleHelmetTick", at = @At("HEAD"))
    private void updateTidalHelmet(CallbackInfo ci) {
        if (this.getItemBySlot(EquipmentSlot.HEAD).is(EtceteraItems.TIDAL_HELMET.get()) && !this.isEyeInFluid(FluidTags.WATER))
            this.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20 * 90, 0, false, false, false));
    }
}
