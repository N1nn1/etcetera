package com.ninni.etcetera.client.render.entity.layer;

import com.ninni.etcetera.client.model.WeaverModel;
import com.ninni.etcetera.entity.WeaverEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.Constants.MOD_ID;

public class WeaverEyesFeatureRenderer<T extends WeaverEntity, M extends WeaverModel<T>> extends EyesLayer<T, M> {
    private static final RenderType SKIN = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/weaver/weaver_eyes.png"));

    public WeaverEyesFeatureRenderer(RenderLayerParent<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public @NotNull RenderType renderType() {
        return SKIN;
    }
}