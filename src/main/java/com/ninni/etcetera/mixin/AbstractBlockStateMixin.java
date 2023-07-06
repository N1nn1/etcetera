package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(method = "getModelOffset", at = @At("HEAD"), cancellable = true)
    private void removeModelOffset(net.minecraft.world.BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
        AbstractBlock.AbstractBlockState that = AbstractBlock.AbstractBlockState.class.cast(this);
        if (that.getBlock() instanceof PlantBlock) {
            if (world.getBlockState(pos.down(1)).isIn(EtceteraTags.OFFSET_REMOVER)
                || (that.getBlock() instanceof TallPlantBlock && world.getBlockState(pos.down(2)).isIn(EtceteraTags.OFFSET_REMOVER))
            ) cir.setReturnValue(Vec3d.ZERO);
        }
    }
}