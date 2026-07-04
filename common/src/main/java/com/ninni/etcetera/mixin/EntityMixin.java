package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "makeStuckInBlock", at = @At("HEAD"), cancellable = true)
    private void removeCobwebSlow(BlockState state, Vec3 vec3, CallbackInfo ci) {
        if (((Entity) (Object) this) instanceof LivingEntity living) {
            if (state.is(Blocks.COBWEB)) {
                for (ItemStack stack : living.getArmorSlots()) {
                    if (stack.is(EtceteraItems.SILKEN_SLACKS.get())) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}