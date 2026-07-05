package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
    private void etcetera$getOffset(BlockGetter level, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int limit = 16;
        while (limit-- > 0 && level.getBlockState(mutablePos).hasOffsetFunction()) {
            mutablePos.move(Direction.DOWN);
        }
        if (level.getBlockState(mutablePos).is(EtceteraTags.OFFSET_REMOVER)) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }
}
