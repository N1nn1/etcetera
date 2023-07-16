package com.ninni.etcetera.client;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class TidalHelmetHud {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPostRender(RenderGuiOverlayEvent.Post event) {
        if (event.isCanceled()) return;

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        if (player == null || player.getAbilities().instabuild || player.isSpectator() || player.isDeadOrDying()) return;
        minecraft.getProfiler().push("tidalEye");
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(EtceteraItems.TIDAL_HELMET.get())) {
            if (player.hasEffect(MobEffects.CONDUIT_POWER)) {
                int full = 20 * 90;
                int duration = full - player.getEffect(MobEffects.CONDUIT_POWER).getDuration();
                int[] sprites = {0, 12, 24, 36, 48, 60, 72, 84, 96};
                drawTidalEye(guiGraphics, duration == 0 ? 96 : sprites[duration / (full / 8)], 0, width, height);
            }
        }
        minecraft.getProfiler().pop();
    }

    private void drawTidalEye(GuiGraphics matrix, int x, int y, int width, int height) {
        matrix.blit(new ResourceLocation(Etcetera.MOD_ID, "textures/gui/icons.png"), (width / 2 -6), height - 49, x, y, 12, 12);
    }

}