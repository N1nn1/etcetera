package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.client.render.entity.layer.TurtleRaftBannerLayer;
import com.ninni.etcetera.client.render.entity.layer.TurtleRaftColorRenderLayer;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value=EnvType.CLIENT)
public class TurtleRaftRenderer extends EntityRenderer<TurtleRaftEntity> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/boat/turtle_raft_overlay.png");
    private final TurtleRaftModel model;
    private final TurtleRaftColorRenderLayer layer;
    private final TurtleRaftBannerLayer bannerLayer;

    public TurtleRaftRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = this.createModel(ctx);
        this.layer = this.createLayer(ctx);
        this.bannerLayer = this.createBannerLayer();
    }

    private TurtleRaftModel createModel(EntityRendererFactory.Context ctx) {
        return new TurtleRaftModel(ctx.getPart(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    private TurtleRaftColorRenderLayer createLayer(EntityRendererFactory.Context ctx) {
        return new TurtleRaftColorRenderLayer(new FeatureRendererContext<>() {
            @Override
            public TurtleRaftModel getModel() {
                return TurtleRaftRenderer.this.model;
            }

            @Override
            public Identifier getTexture(TurtleRaftEntity entity) {
                return TEXTURE;
            }
        }, ctx.getModelLoader());
    }

    private TurtleRaftBannerLayer createBannerLayer() {
        return new TurtleRaftBannerLayer(new FeatureRendererContext<>() {
            @Override
            public TurtleRaftModel getModel() {
                return TurtleRaftRenderer.this.model;
            }

            @Override
            public Identifier getTexture(TurtleRaftEntity entity) {
                return TEXTURE;
            }
        });
    }

    @Override
    public void render(TurtleRaftEntity raft, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - f));
        float h = (float)raft.getDamageWobbleTicks() - g;
        float j = raft.getDamageWobbleStrength() - g;
        if (j < 0.0f) j = 0.0f;
        if (h > 0.0f) {
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.sin(h) * h * j / 10.0f * (float)raft.getDamageWobbleSide()));
        }
        if (!MathHelper.approximatelyEquals(raft.interpolateBubbleWobble(g), 0.0f)) {
            matrixStack.multiply(new Quaternionf().setAngleAxis(raft.interpolateBubbleWobble(g) * ((float)Math.PI / 180), 1.0f, 0.0f, 1.0f));
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        model.setAngles(raft, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        layer.render(matrixStack, vertexConsumerProvider, i, raft, 1.0f, 1.0f, g, 1.0f ,1 ,1);
        bannerLayer.render(matrixStack, vertexConsumerProvider, i, raft, 1.0F, 1.0F, g, 1.0F, 1, 1);
        matrixStack.pop();

        super.render(raft, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TurtleRaftEntity entity) {
        return TEXTURE;
    }
}
