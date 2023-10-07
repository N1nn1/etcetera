package com.ninni.etcetera.client.render.entity;

import com.ninni.etcetera.client.model.GoldenGolemModel;
import com.ninni.etcetera.client.render.entity.layer.GoldenGolemEyesFeatureRenderer;
import com.ninni.etcetera.entity.GoldenGolemEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value=EnvType.CLIENT)
public class GoldenGolemRenderer extends MobEntityRenderer<GoldenGolemEntity, GoldenGolemModel<GoldenGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID,"textures/entity/golden_golem/golden_golem.png");

    public GoldenGolemRenderer(EntityRendererFactory.Context context) {
        super(context, new GoldenGolemModel<>(context.getPart(EtceteraEntityModelLayers.GOLDEN_GOLEM)), 0.4F);
        this.addFeature(new GoldenGolemEyesFeatureRenderer<>(this));
    }

    @Override
    protected int getBlockLight(GoldenGolemEntity entity, BlockPos pos) {
        return entity.isOnFire() ? 15 : Math.min(entity.getWorld().getLightLevel(LightType.BLOCK, pos) + 6, 15);
    }

    @Override
    public Identifier getTexture(GoldenGolemEntity weaver) {
        return TEXTURE;
    }
}

