package com.ninni.etcetera.client.renderer.entity;

import com.ninni.etcetera.client.model.EtceteraEntityModelLayers;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class TurtleRaftRenderer extends EntityRenderer<TurtleRaftEntity> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/boat/turtle_raft.png");
    private final TurtleRaftModel model;

    public TurtleRaftRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = this.createModel(ctx);
    }

    private TurtleRaftModel createModel(EntityRendererFactory.Context ctx) {
        return new TurtleRaftModel(ctx.getPart(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    @Override
    public void render(TurtleRaftEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.375, 0.0);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
        model.setAngles(boatEntity, g, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(model.getLayer(TEXTURE));
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(boatEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TurtleRaftEntity entity) {
        return TEXTURE;
    }
}
