package com.ninni.etcetera.client.renderer.entity;

import com.google.common.collect.Maps;
import com.ninni.etcetera.client.model.ChappleModel;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.entity.ChappleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Map;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value=EnvType.CLIENT)
public class ChappleRenderer extends MobEntityRenderer<ChappleEntity, ChappleModel<ChappleEntity>> {
    private static final Map<ChappleEntity.Type, Identifier> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(ChappleEntity.Type.NORMAL, new Identifier(MOD_ID, "textures/entity/chapple/chapple.png"));
        map.put(ChappleEntity.Type.GOLDEN, new Identifier(MOD_ID, "textures/entity/chapple/golden_chapple.png"));
    });

    public ChappleRenderer(EntityRendererFactory.Context context) {
        super(context, new ChappleModel<>(context.getPart(EtceteraEntityModelLayers.CHAPPLE)), 0.3f);
    }

    @Override
    public Identifier getTexture(ChappleEntity chapple) {
        return TEXTURES.get(chapple.getChappleType());
    }

    @Override
    protected float getAnimationProgress(ChappleEntity chapple, float f) {
        float g = MathHelper.lerp(f, chapple.prevFlapProgress, chapple.flapProgress);
        float h = MathHelper.lerp(f, chapple.prevMaxWingDeviation, chapple.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0f) * h;
    }
}

