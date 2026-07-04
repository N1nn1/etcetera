package com.ninni.etcetera.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.Constants.MOD_ID;

public class TurtleRaftColorRenderLayer extends net.minecraft.client.renderer.entity.layers.RenderLayer<TurtleRaftEntity, TurtleRaftModel> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/boat/turtle_raft.png");
    private final TurtleRaftModel model;

    public TurtleRaftColorRenderLayer(RenderLayerParent<TurtleRaftEntity, TurtleRaftModel> context, EntityModelSet loader) {
        super(context);
        this.model = new TurtleRaftModel(loader.bakeLayer(EtceteraEntityModelLayers.TURTLE_RAFT));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, @NotNull TurtleRaftEntity raft, float f, float g, float h, float j, float k, float l) {
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(raft, f, g, h);
        this.model.setupAnim(raft, f, g, j, k, l);

        int m = raft.getColor();
        int packedColor = 0xFF000000 | m;

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, packedColor);
    }
}