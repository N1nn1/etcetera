package com.ninni.etcetera.mixin.quark.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.content.tools.client.render.QuarkBeaconBlockEntityRenderer;
import org.violetmoon.quark.content.tools.module.BeaconRedirectionModule.ExtendedBeamSegment;

@Mixin(QuarkBeaconBlockEntityRenderer.class)
public class QuarkBeaconRendererMixin {

    @WrapOperation(
            method = "renderBeamSegment(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;Lorg/violetmoon/quark/content/tools/module/BeaconRedirectionModule$ExtendedBeamSegment;FFJFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/violetmoon/quark/content/tools/module/BeaconRedirectionModule$ExtendedBeamSegment;getColor()I"
            )
    )
    private static int etcetera$modifyQuarkBeaconColor(ExtendedBeamSegment instance, Operation<Integer> original) {
        int color = original.call(instance);
        if (color == -1) {
            long time = Util.getMillis();
            float hue = (time % 10000L) / 10000.0F;
            return Mth.hsvToRgb(hue, 1.0F, 1.0F) & 0xFFFFFF;
        }
        return color;
    }
}
