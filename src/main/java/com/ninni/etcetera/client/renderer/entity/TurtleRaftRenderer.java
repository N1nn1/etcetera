package com.ninni.etcetera.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.client.renderer.entity.layer.TurtleRaftBannerLayer;
import com.ninni.etcetera.client.renderer.entity.layer.TurtleRaftColorRenderLayer;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class TurtleRaftRenderer extends EntityRenderer<TurtleRaftEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/boat/turtle_raft_overlay.png");
    private final TurtleRaftModel model;
    private final TurtleRaftColorRenderLayer layer;
    private final TurtleRaftBannerLayer bannerLayer;

    public TurtleRaftRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = this.createModel(ctx);
        this.layer = this.createLayer(ctx);
        this.bannerLayer = this.createBannerLayer();
    }

    private TurtleRaftModel createModel(EntityRendererProvider.Context ctx) {
        return new TurtleRaftModel(ctx.bakeLayer(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    private TurtleRaftColorRenderLayer createLayer(EntityRendererProvider.Context ctx) {
        return new TurtleRaftColorRenderLayer(new RenderLayerParent<>() {
            @Override
            public TurtleRaftModel getModel() {
                return TurtleRaftRenderer.this.model;
            }

            @Override
            public ResourceLocation getTextureLocation(TurtleRaftEntity entity) {
                return TEXTURE;
            }
        }, ctx.getModelSet());
    }

    private TurtleRaftBannerLayer createBannerLayer() {
        return new TurtleRaftBannerLayer(new RenderLayerParent<>() {
            @Override
            public TurtleRaftModel getModel() {
                return TurtleRaftRenderer.this.model;
            }

            @Override
            public ResourceLocation getTextureLocation(TurtleRaftEntity entity) {
                return TEXTURE;
            }
        });
    }

    @Override
    public void render(TurtleRaftEntity raft, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0f - f));
        float h = (float)raft.getHurtTime() - g;
        float j = raft.getDamage() - g;
        if (j < 0.0f) j = 0.0f;
        if (h > 0.0f) {
            matrixStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * (float)raft.getHurtDir()));
        }
        if (!Mth.equal(raft.getBubbleAngle(g), 0.0f)) {
            matrixStack.mulPose(new Quaternionf().setAngleAxis(raft.getBubbleAngle(g) * ((float)Math.PI / 180), 1.0f, 0.0f, 1.0f));
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.mulPose(Axis.YP.rotationDegrees(90.0f));
        model.setupAnim(raft, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.renderType(TEXTURE));
        model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        layer.render(matrixStack, vertexConsumerProvider, i, raft, 1.0f, 1.0f, g, 1.0f ,1 ,1);
        bannerLayer.render(matrixStack, vertexConsumerProvider, i, raft, 1.0F, 1.0F, g, 1.0F, 1, 1);
        matrixStack.popPose();

        super.render(raft, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(TurtleRaftEntity entity) {
        return TEXTURE;
    }

}
