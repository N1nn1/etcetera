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
public class AbstractBlockStateMixin {

    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    private void removeModelOffset(BlockGetter world, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        BlockBehaviour.BlockStateBase $this = (BlockBehaviour.BlockStateBase) (Object) this;
        if ($this.getBlock() instanceof BushBlock) {
            if (world.getBlockState(pos.below()).is(EtceteraTags.OFFSET_REMOVER) || ($this.getBlock() instanceof DoublePlantBlock && world.getBlockState(pos.below(2)).is(EtceteraTags.OFFSET_REMOVER))) {
                cir.setReturnValue(Vec3.ZERO);
            }
        }
    }
}