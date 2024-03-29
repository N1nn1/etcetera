package com.ninni.etcetera.client;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.registry.EtceteraItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TidalHelmetHud {

    private final MinecraftClient client = MinecraftClient.getInstance();

    private int width;
    private int height;

    private void drawTidalEye(DrawContext matrix, int x, int y) {
//        drawTexture(matrix, (width / 2 - 6), height - 49, x, y, 12, 12);
//        matrix.drawTexture(new Identifier(Etcetera.MOD_ID, "textures/gui/icons.png"), (width / 2 - 6), height - 49, x, y);
        matrix.drawTexture(new Identifier(Etcetera.MOD_ID, "textures/gui/icons.png"), (width / 2 -6), height - 49, x, y, 12, 12);
    }

    public void render(DrawContext matrix, float delta) {
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