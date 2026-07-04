package com.ninni.etcetera.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.block.IridescentGlassBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {

    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/DyeColor;getTextureDiffuseColor()I")
    )
    private static int etcetera$wrapBeaconBeamColor(DyeColor instance, Operation<Integer> original) {
        if (IridescentGlassBlock.IS_RAINBOW.get()) {
            IridescentGlassBlock.IS_RAINBOW.set(false);
            return -1;
        }

        return original.call(instance);
    }
}