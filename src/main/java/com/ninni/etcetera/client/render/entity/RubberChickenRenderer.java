package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.client.model.RubberChickenModel;
import com.ninni.etcetera.entity.RubberChickenEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class RubberChickenRenderer extends LivingEntityRenderer<RubberChickenEntity, RubberChickenModel<RubberChickenEntity>> {
    private static final Identifier TEXTURE = new Identifier(Etcetera.MOD_ID, "textures/entity/rubber_chicken/rubber_chicken.png");

    public RubberChickenRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RubberChickenModel<>(ctx.getPart(EtceteraEntityModelLayers.RUBBER_CHICKEN)), 0.2F);
    }

    @Override
    protected void setupTransforms(RubberChickenEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        float i = (float)(entity.getWorld().getTime() - entity.lastHitTime) + tickDelta;
        if (i < 5.0f) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(i / 1.5f * (float)Math.PI) * 3.0f));
        }
    }

    @Override
    protected boolean hasLabel(RubberChickenEntity livingEntity) {
        double d = this.dispatcher.getSquaredDistanceToCamera(livingEntity);
        float f = livingEntity.isInSneakingPose() ? 32.0f : 64.0f;
        if (d >= (double)(f * f)) {
            return false;
        }
        return livingEntity.isCustomNameVisible();
    }

    @Override
    public Identifier getTexture(RubberChickenEntity entity) {
        return TEXTURE;
    }
}
