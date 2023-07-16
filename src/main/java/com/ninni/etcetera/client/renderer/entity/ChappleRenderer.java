package com.ninni.etcetera.client.renderer.entity;

import com.google.common.collect.Maps;
import com.ninni.etcetera.client.model.ChappleModel;
import com.ninni.etcetera.entity.ChappleEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class ChappleRenderer extends MobRenderer<ChappleEntity, ChappleModel<ChappleEntity>> {
    private static final Map<ChappleEntity.Type, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(ChappleEntity.Type.NORMAL, new ResourceLocation(MOD_ID, "textures/entity/chapple/chapple.png"));
        map.put(ChappleEntity.Type.GOLDEN, new ResourceLocation(MOD_ID, "textures/entity/chapple/golden_chapple.png"));
    });

    public ChappleRenderer(EntityRendererProvider.Context context) {
        super(context, new ChappleModel<>(context.bakeLayer(EtceteraEntityModelLayers.CHAPPLE)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(ChappleEntity chapple) {
        return TEXTURES.get(chapple.getChappleType());
    }

    @Override
    protected float getBob(ChappleEntity chapple, float f) {
        float g = Mth.lerp(f, chapple.oFlap, chapple.flap);
        float h = Mth.lerp(f, chapple.oFlapSpeed, chapple.flapSpeed);
        return (Mth.sin(g) + 1.0f) * h;
    }

}

