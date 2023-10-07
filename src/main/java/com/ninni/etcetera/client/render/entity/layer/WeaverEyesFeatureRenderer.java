package com.ninni.etcetera.client.render.entity.layer;

import com.ninni.etcetera.client.model.WeaverModel;
import com.ninni.etcetera.entity.WeaverEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(EnvType.CLIENT)
public class WeaverEyesFeatureRenderer<T extends WeaverEntity, M extends WeaverModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(MOD_ID,"textures/entity/weaver/weaver_eyes.png"));

    public WeaverEyesFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }


}
