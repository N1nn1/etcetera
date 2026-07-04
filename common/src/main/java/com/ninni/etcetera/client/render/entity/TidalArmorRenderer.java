package com.ninni.etcetera.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import static com.ninni.etcetera.Constants.MOD_ID;

public class TidalArmorRenderer {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/models/armor/tidal_layer_1.png");
    public static final ResourceLocation ACTIVATED_TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/models/armor/active_tidal_layer_1.png");
    private HumanoidModel<LivingEntity> armorModel;

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (this.armorModel == null)
            this.armorModel = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));

        contextModel.copyPropertiesTo(this.armorModel);
        ResourceLocation texture = entity.isEyeInFluid(FluidTags.WATER) && entity.hasEffect(MobEffects.CONDUIT_POWER) ? ACTIVATED_TEXTURE : TEXTURE;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.armorCutoutNoCull(texture));
        this.armorModel.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
    }
}