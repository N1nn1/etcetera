package com.ninni.etcetera.client.renderer.entity.layer;

import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value= EnvType.CLIENT)
public class TurtleRaftColorRenderLayer extends FeatureRenderer<TurtleRaftEntity, TurtleRaftModel> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/boat/turtle_raft.png");
    private final TurtleRaftModel model;

    public TurtleRaftColorRenderLayer(FeatureRendererContext<TurtleRaftEntity, TurtleRaftModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new TurtleRaftModel(loader.getModelPart(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, TurtleRaftEntity raft, float f, float g, float h, float j, float k, float l) {
        this.getContextModel().copyStateTo(this.model);
        this.model.animateModel(raft, f, g, h);
        this.model.setAngles(raft, f, g, j, k, l);
        int m = raft.getColor();
        float n = (float)(m >> 16 & 0xFF) / 255.0f;
        float o = (float)(m >> 8 & 0xFF) / 255.0f;
        float p = (float)(m & 0xFF) / 255.0f;

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, n, o, p, 1.0f);
    }
}

