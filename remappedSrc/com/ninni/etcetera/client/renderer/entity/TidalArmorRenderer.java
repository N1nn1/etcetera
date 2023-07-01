package com.ninni.etcetera.client.renderer.entity;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.*;

public class TidalArmorRenderer implements ArmorRenderer {
    private BipedEntityModel<LivingEntity> armorModel;
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/models/armor/tidal_layer_1.png");
    public static final Identifier ACTIVATED_TEXTURE = new Identifier(MOD_ID, "textures/models/armor/active_tidal_layer_1.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));

        contextModel.setAttributes(this.armorModel);
        Identifier texture = entity.isSubmergedIn(FluidTags.WATER) && entity.hasStatusEffect(StatusEffects.CONDUIT_POWER) ? ACTIVATED_TEXTURE : TEXTURE;
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, texture);
    }
}
