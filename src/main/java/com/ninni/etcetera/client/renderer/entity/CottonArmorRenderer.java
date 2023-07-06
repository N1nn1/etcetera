package com.ninni.etcetera.client.renderer.entity;

import com.ninni.etcetera.client.model.SweaterModel;
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
    private SweaterModel<LivingEntity> armorModel;
    public static final Identifier WHITE_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_white.png");
    public static final Identifier LIGHT_GRAY_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_light_gray.png");
    public static final Identifier GRAY_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_gray.png");
    public static final Identifier BLACK_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_black.png");
    public static final Identifier BROWN_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_brown.png");
    public static final Identifier RED_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_red.png");
    public static final Identifier ORANGE_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_orange.png");
    public static final Identifier YELLOW_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_yellow.png");
    public static final Identifier LIME_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_lime.png");
    public static final Identifier GREEN_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_green.png");
    public static final Identifier CYAN_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_cyan.png");
    public static final Identifier LIGHT_BLUE_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_light_blue.png");
    public static final Identifier BLUE_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_blue.png");
    public static final Identifier PURPLE_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_purple.png");
    public static final Identifier MAGENTA_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_magenta.png");
    public static final Identifier PINK_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/sweater_pink.png");
    public static final Identifier TRADER_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/trader_robe.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new SweaterModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EtceteraEntityModelLayers.PLAYER_SWEATER));
        contextModel.copyBipedStateTo(this.armorModel);

        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, getTexture(stack.getItem()));
    }


    public Identifier getTexture(Item item) {
    if (item == EtceteraItems.WHITE_SWEATER) return WHITE_TEXTURE;
    if (item == EtceteraItems.LIGHT_GRAY_SWEATER) return LIGHT_GRAY_TEXTURE;
    if (item == EtceteraItems.GRAY_SWEATER) return GRAY_TEXTURE;
    if (item == EtceteraItems.BLACK_SWEATER) return BLACK_TEXTURE;
    if (item == EtceteraItems.BROWN_SWEATER) return BROWN_TEXTURE;
    if (item == EtceteraItems.RED_SWEATER) return RED_TEXTURE;
    if (item == EtceteraItems.ORANGE_SWEATER) return ORANGE_TEXTURE;
    if (item == EtceteraItems.YELLOW_SWEATER) return YELLOW_TEXTURE;
    if (item == EtceteraItems.LIME_SWEATER) return LIME_TEXTURE;
    if (item == EtceteraItems.GREEN_SWEATER) return GREEN_TEXTURE;
    if (item == EtceteraItems.CYAN_SWEATER) return CYAN_TEXTURE;
    if (item == EtceteraItems.LIGHT_BLUE_SWEATER) return LIGHT_BLUE_TEXTURE;
    if (item == EtceteraItems.BLUE_SWEATER) return BLUE_TEXTURE;
    if (item == EtceteraItems.PURPLE_SWEATER) return PURPLE_TEXTURE;
    if (item == EtceteraItems.MAGENTA_SWEATER) return MAGENTA_TEXTURE;
    if (item == EtceteraItems.PINK_SWEATER) return PINK_TEXTURE;
    else return TRADER_TEXTURE;
    }
}
