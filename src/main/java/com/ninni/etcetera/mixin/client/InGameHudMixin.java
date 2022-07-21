package com.ninni.etcetera.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ninni.etcetera.item.EtceteraItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.ninni.etcetera.Etcetera.*;

@Environment(value= EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow protected abstract PlayerEntity getCameraPlayer();
    @Shadow private int scaledHeight;
    @Shadow @Final private MinecraftClient client;
    @Shadow private int scaledWidth;
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/icons.png");


    private void renderTidalEye(MatrixStack matrices) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.client.getProfiler().push("tidalEye");

        if (playerEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(EtceteraItems.TIDAL_HELMET)) {
            if (playerEntity.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                int duration = playerEntity.getStatusEffect(StatusEffects.CONDUIT_POWER).getDuration();

                if (duration == 20 * 90) drawTidalEye(matrices, 96, 0);
                else if (duration < 20 * 90 && duration >= 20 * 75) drawTidalEye(matrices, 0, 0);
                else if (duration < 20 * 75 && duration >= 20 * 60) drawTidalEye(matrices, 12, 0);
                else if (duration < 20 * 60 && duration >= 20 * 45) drawTidalEye(matrices, 24, 0);
                else if (duration < 20 * 45 && duration >= 20 * 30) drawTidalEye(matrices, 36, 0);
                else if (duration < 20 * 30 && duration >= 20 * 15) drawTidalEye(matrices, 48, 0);
                else if (duration < 20 * 15 && duration >= 20 * 10) drawTidalEye(matrices, 60, 0);
                else if (duration < 20 * 10 && duration >= 20 * 5) drawTidalEye(matrices, 72, 0);
                else if (duration < 20 * 5) drawTidalEye(matrices, 84, 0);
            } else drawTidalEye(matrices,96, 0);
        }
        this.client.getProfiler().pop();
    }

    private void drawTidalEye(MatrixStack matrices, int x, int y) { this.drawTexture(matrices, (this.scaledWidth / 2 - 6), this.scaledHeight - 49, x, y, 12, 12); }

    @Inject(method = "renderStatusBars", at = @At("TAIL"))
    private void customRenderStatusBars(MatrixStack matrices, CallbackInfo ci) { this.renderTidalEye(matrices); }
}
