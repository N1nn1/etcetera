package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.client.model.CobwebProjectileModel;
import com.ninni.etcetera.entity.CobwebProjectileEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class CobwebProjectileEntityRenderer extends EntityRenderer<CobwebProjectileEntity> {
    public static final Identifier TEXTURE = new Identifier(Etcetera.MOD_ID, "textures/entity/weaver/projectile.png");
    private final CobwebProjectileModel model;

    public CobwebProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new CobwebProjectileModel(context.getPart(EtceteraEntityModelLayers.COBWEB));
    }

    @Override
    public void render(CobwebProjectileEntity cobwebProjectile, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, cobwebProjectile.prevYaw, cobwebProjectile.getYaw()) - 90.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, cobwebProjectile.prevPitch, cobwebProjectile.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrixStack.pop();
        super.render(cobwebProjectile, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(CobwebProjectileEntity cobwebProjectile) {
        return TEXTURE;
    }
}
