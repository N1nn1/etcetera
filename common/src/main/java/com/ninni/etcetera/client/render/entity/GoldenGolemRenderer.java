package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.client.model.GoldenGolemModel;
import com.ninni.etcetera.client.render.entity.layer.GoldenGolemEyesFeatureRenderer;
import com.ninni.etcetera.entity.GoldenGolemEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.Constants.MOD_ID;

public class GoldenGolemRenderer extends MobRenderer<GoldenGolemEntity, GoldenGolemModel<GoldenGolemEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/golden_golem/golden_golem.png");

    public GoldenGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new GoldenGolemModel<>(context.bakeLayer(EtceteraEntityModelLayers.GOLDEN_GOLEM)), 0.4F);
        this.addLayer(new GoldenGolemEyesFeatureRenderer<>(this));
    }

    @Override
    protected int getBlockLightLevel(GoldenGolemEntity entity, BlockPos pos) {
        if (entity.isOnFire()) return 15;
        if (entity.getHealingCooldown() > 0) return entity.level().getBrightness(LightLayer.BLOCK, pos);

        return Math.min(entity.level().getBrightness(LightLayer.BLOCK, pos) + 6, 15);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GoldenGolemEntity weaver) {
        return TEXTURE;
    }
}