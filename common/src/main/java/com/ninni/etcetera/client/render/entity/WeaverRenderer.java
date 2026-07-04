package com.ninni.etcetera.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.etcetera.client.model.WeaverModel;
import com.ninni.etcetera.client.render.entity.layer.WeaverEyesFeatureRenderer;
import com.ninni.etcetera.entity.WeaverEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.Constants.MOD_ID;

public class WeaverRenderer extends MobRenderer<WeaverEntity, WeaverModel<WeaverEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/weaver/weaver.png");

    public WeaverRenderer(EntityRendererProvider.Context context) {
        super(context, new WeaverModel<>(context.bakeLayer(EtceteraEntityModelLayers.WEAVER)), 0.6f);
        this.addLayer(new WeaverEyesFeatureRenderer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WeaverEntity weaver) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(@NotNull WeaverEntity entity, PoseStack matrices, float bob, float yBodyRot, float partialTick, float scale) {
        matrices.popPose();
        matrices.translate(0.0F, 0.25F, 0.0F);
        matrices.pushPose();
        super.setupRotations(entity, matrices, bob, yBodyRot, partialTick, scale);
    }
}