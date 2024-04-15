package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {

    @Inject(method = "updateComparators", at = @At("HEAD"))
    private void e$updateComparators(BlockPos pos, Block block, CallbackInfo ci) {
        World that = World.class.cast(this);
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            if (!that.isChunkLoaded(blockPos)) continue;
            BlockState blockState = that.getBlockState(blockPos);
            if (blockState.isOf(EtceteraBlocks.REDSTONE_WIRE_COMPARATOR)) {
                that.updateNeighbor(blockState, blockPos, block, pos, false);
                continue;
            }
            if (!blockState.isSolidBlock(that, blockPos) || !(blockState = that.getBlockState(blockPos = blockPos.offset(direction))).isOf(Blocks.COMPARATOR)) continue;
            that.updateNeighbor(blockState, blockPos, block, pos, false);
        }
    }
}
