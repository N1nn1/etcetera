package com.ninni.etcetera.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Level.class)
public class LevelMixin {

    @WrapOperation(
            method = "updateNeighbourForOutputSignal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
            )
    )
    private boolean etcetera$wrapComparatorCheck(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || (block == Blocks.COMPARATOR && instance.is(EtceteraBlocks.REDSTONE_WIRE_COMPARATOR.get()));
    }
}