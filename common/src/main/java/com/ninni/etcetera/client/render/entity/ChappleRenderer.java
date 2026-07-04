package com.ninni.etcetera.client.render.entity;

import com.google.common.collect.Maps;
import com.ninni.etcetera.client.model.ChappleModel;
import com.ninni.etcetera.entity.ChappleEntity;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.ninni.etcetera.Constants.MOD_ID;

public class ChappleRenderer extends MobRenderer<ChappleEntity, ChappleModel<ChappleEntity>> {
    private static final Map<ChappleEntity.Type, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), map -> {
        map.put(ChappleEntity.Type.NORMAL, ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/chapple/chapple.png"));
        map.put(ChappleEntity.Type.GOLDEN, ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/chapple/golden_chapple.png"));
    });

    public ChappleRenderer(EntityRendererProvider.Context context) {
        super(context, new ChappleModel<>(context.bakeLayer(EtceteraEntityModelLayers.CHAPPLE)), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ChappleEntity chapple) {
        return TEXTURES.get(chapple.getChappleType());
    }

    @Override
    protected float getBob(ChappleEntity chapple, float partialTick) {
        float f = Mth.lerp(partialTick, chapple.oFlap, chapple.flap);
        float g = Mth.lerp(partialTick, chapple.oFlapSpeed, chapple.flapSpeed);
        return (Mth.sin(f) + 1.0f) * g;
    }
}
