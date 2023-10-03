package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {

    @Shadow public abstract Iterable<ItemStack> getArmorItems();


    @Inject(method = "slowMovement", at = @At("HEAD"), cancellable = true)
    private void removeCobwebSlow(BlockState state, Vec3d multiplier, CallbackInfo ci) {

        for (ItemStack stack : this.getArmorItems()) {
            if (stack.isOf(EtceteraItems.SILKEN_SLACKS) && state.isOf(Blocks.COBWEB)) {
                ci.cancel();
            }
        }
    }


    @Inject(method = "canClimb", at = @At("HEAD"), cancellable = true)
    private void makeCobwebClimbable(BlockState state, CallbackInfoReturnable<Boolean> cir) {

        for (ItemStack stack : this.getArmorItems()) {
            if (stack.isOf(EtceteraItems.SILKEN_SLACKS) && state.isOf(Blocks.COBWEB)) {
                cir.setReturnValue(true);
            }
        }
    }
}
