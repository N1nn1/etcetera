package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    private void etcetera$removeModelOffset(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        BlockBehaviour.BlockStateBase state = (BlockBehaviour.BlockStateBase) (Object) this;

        if (state.getBlock() instanceof BushBlock) {
            if (level.getBlockState(pos.below()).is(EtceteraTags.OFFSET_REMOVER)
                    || (state.getBlock() instanceof DoublePlantBlock && level.getBlockState(pos.below(2)).is(EtceteraTags.OFFSET_REMOVER))) {
                cir.setReturnValue(Vec3.ZERO);
            }
        }
    }
}