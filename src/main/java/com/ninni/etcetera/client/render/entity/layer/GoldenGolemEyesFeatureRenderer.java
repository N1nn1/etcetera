package com.ninni.etcetera.client.render.entity.layer;

import com.ninni.etcetera.client.model.GoldenGolemModel;
import com.ninni.etcetera.entity.GoldenGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(EnvType.CLIENT)
public class GoldenGolemEyesFeatureRenderer<T extends GoldenGolemEntity, M extends GoldenGolemModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(MOD_ID,"textures/entity/golden_golem/golden_golem_eyes.png"));

    public GoldenGolemEyesFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }


}
