package com.ninni.etcetera.client;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class TidalHelmetHud {

    private final Minecraft client = Minecraft.getInstance();

    private int width;
    private int height;

    private void drawTidalEye(GuiGraphics graphics, int x, int y) {
        graphics.blit(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/icons.png"), (width / 2 - 6), height - 49, x, y, 12, 12);
    }

    public void render(GuiGraphics matrix, float delta) {
        width = client.getWindow().getGuiScaledWidth();
        height = client.getWindow().getGuiScaledHeight();

        Entity entity = client.cameraEntity;
        if (entity instanceof Player player) {
            if (player.getAbilities().instabuild || player.isSpectator() || player.isDeadOrDying()) return;
            client.getProfiler().push("tidalEye");
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(EtceteraItems.TIDAL_HELMET.get())) {
                if (player.hasEffect(MobEffects.CONDUIT_POWER)) {
                    int full = 20 * 90;
                    int duration = full - player.getEffect(MobEffects.CONDUIT_POWER).getDuration();
                    int[] sprites = {0, 12, 24, 36, 48, 60, 72, 84, 96};
                    drawTidalEye(matrix, duration == 0 ? 96 : sprites[duration / (full / 8)], 0);
                }
            }
            client.getProfiler().pop();
        }
    }
}