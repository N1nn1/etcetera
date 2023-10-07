package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.client.model.WeaverModel;
import com.ninni.etcetera.client.render.entity.layer.WeaverEyesFeatureRenderer;
import com.ninni.etcetera.entity.WeaverEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value=EnvType.CLIENT)
public class WeaverRenderer extends MobEntityRenderer<WeaverEntity, WeaverModel<WeaverEntity>> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID,"textures/entity/weaver/weaver.png");

    public WeaverRenderer(EntityRendererFactory.Context context) {
        super(context, new WeaverModel<>(context.getPart(EtceteraEntityModelLayers.WEAVER)), 0.6f);
        this.addFeature(new WeaverEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(WeaverEntity weaver) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(WeaverEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        matrices.pop();
        matrices.translate(0.0F, 0.25F, 0.0F);
        matrices.push();
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }
}

