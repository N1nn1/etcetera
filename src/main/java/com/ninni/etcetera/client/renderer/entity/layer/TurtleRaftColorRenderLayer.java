package com.ninni.etcetera.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class TurtleRaftColorRenderLayer extends RenderLayer<TurtleRaftEntity, TurtleRaftModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/boat/turtle_raft.png");
    private final TurtleRaftModel model;

    public TurtleRaftColorRenderLayer(RenderLayerParent<TurtleRaftEntity, TurtleRaftModel> context, EntityModelSet loader) {
        super(context);
        this.model = new TurtleRaftModel(loader.bakeLayer(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, TurtleRaftEntity raft, float f, float g, float h, float j, float k, float l) {
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(raft, f, g, h);
        this.model.setupAnim(raft, f, g, j, k, l);
        int m = raft.getColor();
        float n = (float)(m >> 16 & 0xFF) / 255.0f;
        float o = (float)(m >> 8 & 0xFF) / 255.0f;
        float p = (float)(m & 0xFF) / 255.0f;

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, n, o, p, 1.0f);
    }

}

