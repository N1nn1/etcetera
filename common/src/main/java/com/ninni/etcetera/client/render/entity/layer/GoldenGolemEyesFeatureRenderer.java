package com.ninni.etcetera.client.render.entity.layer;

import com.ninni.etcetera.client.model.GoldenGolemModel;
import com.ninni.etcetera.entity.GoldenGolemEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.Constants.MOD_ID;

public class GoldenGolemEyesFeatureRenderer<T extends GoldenGolemEntity, M extends GoldenGolemModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SKIN = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/golden_golem/golden_golem_eyes.png"));

    public GoldenGolemEyesFeatureRenderer(RenderLayerParent<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public @NotNull RenderType renderType() {
        return SKIN;
    }
}