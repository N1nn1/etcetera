package com.ninni.etcetera.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ninni.etcetera.item.EtceteraItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import static com.ninni.etcetera.Etcetera.*;

@Environment(EnvType.CLIENT)
public class TidalEyeRenderer extends DrawableHelper implements HudRenderCallback {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/icons.png");
    public static final String PROFILER_LOCATION = "tidalEye";

    private final MinecraftClient client = MinecraftClient.getInstance();
    private int scaledWidth, scaledHeight;

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        if (!this.client.interactionManager.getCurrentGameMode().isSurvivalLike()) return;

        Window window =  this.client.getWindow();
        this.scaledWidth = window.getScaledWidth();
        this.scaledHeight = window.getScaledHeight();

        Profiler profiler = client.getProfiler();
        profiler.push(PROFILER_LOCATION);

        RenderSystem.setShaderTexture(0, TEXTURE);

        ClientPlayerEntity player = this.client.player;
        if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(EtceteraItems.TIDAL_HELMET)) {
            if (player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                int duration = player.getStatusEffect(StatusEffects.CONDUIT_POWER).getDuration();

                if (duration == 20 * 90) this.drawTidalEye(matrices, 96, 0);
                else if (duration < 20 * 90 && duration >= 20 * 75) this.drawTidalEye(matrices, 0, 0);
                else if (duration < 20 * 75 && duration >= 20 * 60) this.drawTidalEye(matrices, 12, 0);
                else if (duration < 20 * 60 && duration >= 20 * 45) this.drawTidalEye(matrices, 24, 0);
                else if (duration < 20 * 45 && duration >= 20 * 30) this.drawTidalEye(matrices, 36, 0);
                else if (duration < 20 * 30 && duration >= 20 * 15) this.drawTidalEye(matrices, 48, 0);
                else if (duration < 20 * 15 && duration >= 20 * 10) this.drawTidalEye(matrices, 60, 0);
                else if (duration < 20 * 10 && duration >= 20 * 5) this.drawTidalEye(matrices, 72, 0);
                else if (duration < 20 * 5) this.drawTidalEye(matrices, 84, 0);
            } else this.drawTidalEye(matrices,96, 0);
        }

        profiler.pop();
    }

    public void drawTidalEye(MatrixStack matrices, int x, int y) {
        this.drawTexture(matrices, (this.scaledWidth / 2 - 6), this.scaledHeight - 49, x, y, 12, 12);
    }
}
