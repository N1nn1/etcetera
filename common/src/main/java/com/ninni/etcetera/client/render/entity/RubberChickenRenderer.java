package com.ninni.etcetera.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ninni.etcetera.Constants;
import com.ninni.etcetera.client.model.RubberChickenModel;
import com.ninni.etcetera.entity.RubberChickenEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RubberChickenRenderer extends LivingEntityRenderer<RubberChickenEntity, RubberChickenModel<RubberChickenEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/rubber_chicken/rubber_chicken.png");

    public RubberChickenRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RubberChickenModel<>(ctx.bakeLayer(EtceteraEntityModelLayers.RUBBER_CHICKEN)), 0.2F);
    }

    @Override
    protected void setupRotations(@NotNull RubberChickenEntity entity, @NotNull PoseStack matrices, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, matrices, bob, yBodyRot, partialTick, scale);
        float i = (float) (entity.level().getGameTime() - entity.lastHitTime) + partialTick;
        if (i < 5.0f) {
            matrices.mulPose(Axis.YP.rotationDegrees(Mth.sin(i / 1.5f * (float) Math.PI) * 3.0f));
        }
    }

    @Override
    protected boolean shouldShowName(@NotNull RubberChickenEntity livingEntity) {
        double d = this.entityRenderDispatcher.distanceToSqr(livingEntity);
        float f = livingEntity.isDiscrete() ? 32.0f : 64.0f;
        if (d >= (double) (f * f)) {
            return false;
        }
        return livingEntity.isCustomNameVisible();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RubberChickenEntity entity) {
        return TEXTURE;
    }
}