package com.ninni.etcetera.client.renderer.entity;

import com.ninni.etcetera.client.model.CottonArmorModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.registry.EtceteraItems;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class CottonArmorRenderer implements ArmorRenderer {
    private CottonArmorModel<LivingEntity> armorModel;
    public static final Identifier WHITE_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_white.png");
    public static final Identifier LIGHT_GRAY_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_light_gray.png");
    public static final Identifier GRAY_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_gray.png");
    public static final Identifier BLACK_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_black.png");
    public static final Identifier BROWN_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_brown.png");
    public static final Identifier RED_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_red.png");
    public static final Identifier ORANGE_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_orange.png");
    public static final Identifier YELLOW_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_yellow.png");
    public static final Identifier LIME_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_lime.png");
    public static final Identifier GREEN_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_green.png");
    public static final Identifier CYAN_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_cyan.png");
    public static final Identifier LIGHT_BLUE_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_light_blue.png");
    public static final Identifier BLUE_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_blue.png");
    public static final Identifier PURPLE_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_purple.png");
    public static final Identifier MAGENTA_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_magenta.png");
    public static final Identifier PINK_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_pink.png");
    public static final Identifier TRADER_SWEATER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_sweater_trader.png");
    public static final Identifier WHITE_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_white.png");
    public static final Identifier LIGHT_GRAY_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_light_gray.png");
    public static final Identifier GRAY_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_gray.png");
    public static final Identifier BLACK_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_black.png");
    public static final Identifier BROWN_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_brown.png");
    public static final Identifier RED_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_red.png");
    public static final Identifier ORANGE_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_orange.png");
    public static final Identifier YELLOW_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_yellow.png");
    public static final Identifier LIME_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_lime.png");
    public static final Identifier GREEN_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_green.png");
    public static final Identifier CYAN_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_cyan.png");
    public static final Identifier LIGHT_BLUE_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_light_blue.png");
    public static final Identifier BLUE_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_blue.png");
    public static final Identifier PURPLE_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_purple.png");
    public static final Identifier MAGENTA_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_magenta.png");
    public static final Identifier PINK_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_pink.png");
    public static final Identifier TRADER_HAT_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/cotton_hat_trader.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new CottonArmorModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EtceteraEntityModelLayers.PLAYER_COTTON));
        contextModel.copyBipedStateTo(this.armorModel);

        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, getTexture(stack.getItem()));
    }

    public Identifier getTexture(Item item) {
        if (item == EtceteraItems.WHITE_SWEATER) return WHITE_SWEATER_TEXTURE;
        if (item == EtceteraItems.LIGHT_GRAY_SWEATER) return LIGHT_GRAY_SWEATER_TEXTURE;
        if (item == EtceteraItems.GRAY_SWEATER) return GRAY_SWEATER_TEXTURE;
        if (item == EtceteraItems.BLACK_SWEATER) return BLACK_SWEATER_TEXTURE;
        if (item == EtceteraItems.BROWN_SWEATER) return BROWN_SWEATER_TEXTURE;
        if (item == EtceteraItems.RED_SWEATER) return RED_SWEATER_TEXTURE;
        if (item == EtceteraItems.ORANGE_SWEATER) return ORANGE_SWEATER_TEXTURE;
        if (item == EtceteraItems.YELLOW_SWEATER) return YELLOW_SWEATER_TEXTURE;
        if (item == EtceteraItems.LIME_SWEATER) return LIME_SWEATER_TEXTURE;
        if (item == EtceteraItems.GREEN_SWEATER) return GREEN_SWEATER_TEXTURE;
        if (item == EtceteraItems.CYAN_SWEATER) return CYAN_SWEATER_TEXTURE;
        if (item == EtceteraItems.LIGHT_BLUE_SWEATER) return LIGHT_BLUE_SWEATER_TEXTURE;
        if (item == EtceteraItems.BLUE_SWEATER) return BLUE_SWEATER_TEXTURE;
        if (item == EtceteraItems.PURPLE_SWEATER) return PURPLE_SWEATER_TEXTURE;
        if (item == EtceteraItems.MAGENTA_SWEATER) return MAGENTA_SWEATER_TEXTURE;
        if (item == EtceteraItems.PINK_SWEATER) return PINK_SWEATER_TEXTURE;
        if (item == EtceteraItems.WHITE_HAT) return WHITE_HAT_TEXTURE;
        if (item == EtceteraItems.LIGHT_GRAY_HAT) return LIGHT_GRAY_HAT_TEXTURE;
        if (item == EtceteraItems.GRAY_HAT) return GRAY_HAT_TEXTURE;
        if (item == EtceteraItems.BLACK_HAT) return BLACK_HAT_TEXTURE;
        if (item == EtceteraItems.BROWN_HAT) return BROWN_HAT_TEXTURE;
        if (item == EtceteraItems.RED_HAT) return RED_HAT_TEXTURE;
        if (item == EtceteraItems.ORANGE_HAT) return ORANGE_HAT_TEXTURE;
        if (item == EtceteraItems.YELLOW_HAT) return YELLOW_HAT_TEXTURE;
        if (item == EtceteraItems.LIME_HAT) return LIME_HAT_TEXTURE;
        if (item == EtceteraItems.GREEN_HAT) return GREEN_HAT_TEXTURE;
        if (item == EtceteraItems.CYAN_HAT) return CYAN_HAT_TEXTURE;
        if (item == EtceteraItems.LIGHT_BLUE_HAT) return LIGHT_BLUE_HAT_TEXTURE;
        if (item == EtceteraItems.BLUE_HAT) return BLUE_HAT_TEXTURE;
        if (item == EtceteraItems.PURPLE_HAT) return PURPLE_HAT_TEXTURE;
        if (item == EtceteraItems.MAGENTA_HAT) return MAGENTA_HAT_TEXTURE;
        if (item == EtceteraItems.PINK_HAT) return PINK_HAT_TEXTURE;
        if (item == EtceteraItems.TRADER_HOOD) return TRADER_HAT_TEXTURE;
        else return TRADER_SWEATER_TEXTURE;
    }
}
