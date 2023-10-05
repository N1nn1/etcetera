package com.ninni.etcetera.client.renderer.entity;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class AdventurerArmorRenderer implements ArmorRenderer {
    private BipedEntityModel<LivingEntity> armorModel;
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/models/armor/adventurer_layer_1.png");

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (this.armorModel == null) this.armorModel = new BipedEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_OUTER_ARMOR));
        contextModel.copyBipedStateTo(this.armorModel);
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, this.armorModel, TEXTURE);
    }
}
