package com.ninni.etcetera.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.block.IridescentGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getBeaconColorMultiplier(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Ljava/lang/Integer;",
                    remap = false
            )
    )
    private static Integer etcetera$wrapNeoForgeBeaconColor(BlockState instance, LevelReader level, BlockPos pos, BlockPos beaconPos, Operation<Integer> original) {
        if (instance.getBlock() instanceof IridescentGlassBlock) {
            return -1;
        }

        return original.call(instance, level, pos, beaconPos);
    }
}