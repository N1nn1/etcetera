package com.ninni.etcetera.mixin;

import net.minecraft.Util;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BeaconRenderer.class)
public class BeaconRendererMixin {

    @ModifyVariable(
            method = "renderBeaconBeam(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FFJIIIFF)V",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 2
    )
    private static int etcetera$renderRainbowBeam(int color) {
        if (color == -1) {
            long time = Util.getMillis();
            float hue = (time % 10000L) / 10000.0F;

            return Mth.hsvToRgb(hue, 1.0F, 1.0F) & 0xFFFFFF;
        }
        return color;
    }
}