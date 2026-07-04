package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class LevelMixin {

    @Inject(method = "updateNeighbourForOutputSignal", at = @At("HEAD"))
    private void e$updateNeighbourForOutputSignal(BlockPos pos, Block block, CallbackInfo ci) {
        Level that = Level.class.cast(this);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos = pos.relative(direction);
            if (!that.hasChunkAt(blockPos)) continue;
            BlockState blockState = that.getBlockState(blockPos);
            if (blockState.is(EtceteraBlocks.REDSTONE_WIRE_COMPARATOR.get())) {
                that.neighborChanged(blockState, blockPos, block, pos, false);
                continue;
            }
            if (!blockState.isRedstoneConductor(that, blockPos) || !(blockState = that.getBlockState(blockPos = blockPos.relative(direction))).is(Blocks.COMPARATOR))
                continue;
            that.neighborChanged(blockState, blockPos, block, pos, false);
        }
    }
}