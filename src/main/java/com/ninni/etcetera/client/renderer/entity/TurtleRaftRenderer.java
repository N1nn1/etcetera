package com.ninni.etcetera.client.renderer.entity;

import com.ninni.etcetera.client.model.EtceteraEntityModelLayers;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.client.renderer.entity.layer.TurtleRaftColorRenderLayer;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class TurtleRaftRenderer extends EntityRenderer<TurtleRaftEntity> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/boat/turtle_raft_overlay.png");
    private final TurtleRaftModel model;
    private final TurtleRaftColorRenderLayer layer;

    public TurtleRaftRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = this.createModel(ctx);
        this.layer = this.createLayer(ctx);
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

    @Override
    public void render(TurtleRaftEntity raft, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
        float h = (float)raft.getDamageWobbleTicks() - g;
        float j = raft.getDamageWobbleStrength() - g;
        if (j < 0.0f) j = 0.0f;
        if (h > 0.0f) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(h) * h * j / 10.0f * (float)raft.getDamageWobbleSide()));
        }
        if (!MathHelper.approximatelyEquals(raft.interpolateBubbleWobble(g), 0.0f)) {
            matrixStack.multiply(new Quaternion(new Vec3f(1.0f, 0.0f, 1.0f), raft.interpolateBubbleWobble(g), true));
        }
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
        model.setAngles(raft, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        layer.render(matrixStack, vertexConsumerProvider, i, raft, 1.0f, 1.0f, g, 1.0f ,1 ,1);
        matrixStack.pop();

        super.render(raft, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TurtleRaftEntity entity) {
        return TEXTURE;
    }
}
