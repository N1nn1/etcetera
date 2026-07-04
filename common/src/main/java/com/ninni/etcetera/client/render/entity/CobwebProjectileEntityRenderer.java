package com.ninni.etcetera.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ninni.etcetera.Constants;
import com.ninni.etcetera.client.model.CobwebProjectileModel;
import com.ninni.etcetera.entity.CobwebProjectileEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CobwebProjectileEntityRenderer extends EntityRenderer<CobwebProjectileEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/weaver/projectile.png");
    private final CobwebProjectileModel model;

    public CobwebProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CobwebProjectileModel(context.bakeLayer(EtceteraEntityModelLayers.COBWEB));
    }

    @Override
    public void render(CobwebProjectileEntity cobwebProjectile, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, cobwebProjectile.yRotO, cobwebProjectile.getYRot()) - 90.0F));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, cobwebProjectile.xRotO, cobwebProjectile.getXRot()) + 90.0F));
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entitySolid(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        matrixStack.popPose();
        super.render(cobwebProjectile, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CobwebProjectileEntity cobwebProjectile) {
        return TEXTURE;
    }
}