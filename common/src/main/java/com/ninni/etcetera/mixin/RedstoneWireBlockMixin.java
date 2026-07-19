package com.ninni.etcetera.mixin;

import com.ninni.etcetera.block.RedstoneWiresBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {

    @Inject(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void etcetera$connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof RedstoneWiresBlock) cir.setReturnValue(false);
    }

    @Inject(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private static void etcetera$connectsTo(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof RedstoneWiresBlock) cir.setReturnValue(false);
    }
}