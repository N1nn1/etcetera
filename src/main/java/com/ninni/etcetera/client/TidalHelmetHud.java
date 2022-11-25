package com.ninni.etcetera.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.item.EtceteraItems;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TidalHelmetHud extends DrawableHelper {

    private final MinecraftClient client = MinecraftClient.getInstance();

    private int width;
    private int height;

    private void drawTidalEye(MatrixStack matrix, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, new Identifier(Etcetera.MOD_ID, "textures/gui/icons.png"));
        drawTexture(matrix, (width / 2 - 6), height - 49, x, y, 12, 12);
    }

    private void render(MatrixStack matrix, float delta) {
        width = client.getWindow().getScaledWidth();
        height = client.getWindow().getScaledHeight();

        Entity entity = client.cameraEntity;
        if (entity instanceof PlayerEntity player) {
            if (player == null || player.getAbilities().creativeMode || player.isSpectator() || player.isDead()) return;
            client.getProfiler().push("tidalEye");
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(EtceteraItems.TIDAL_HELMET)) {
                if (player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
                    int full = 20 * 90;
                    int duration = full - player.getStatusEffect(StatusEffects.CONDUIT_POWER).getDuration();
                    int[] sprites = {0, 12, 24, 36, 48, 60, 72, 84, 96};
                    drawTidalEye(matrix, duration == 0 ? 96 : sprites[duration / (full / 8)], 0);
                }
            }
            client.getProfiler().pop();
        }
    }

    public static void init() {
        final TidalHelmetHud hud = new TidalHelmetHud();
        HudRenderCallback.EVENT.register(hud::render);
    }
}