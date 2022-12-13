package com.ninni.etcetera.client.renderer.entity;

import com.ninni.etcetera.client.model.EtceteraEntityModelLayers;
import com.ninni.etcetera.client.model.SnailModel;
import com.ninni.etcetera.entity.SnailEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(value= EnvType.CLIENT)
public class SnailRenderer extends MobEntityRenderer<SnailEntity, SnailModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/snail/snail.png");

    public SnailRenderer(EntityRendererFactory.Context context) {
        super(context, new SnailModel(context.getPart(EtceteraEntityModelLayers.SNAIL)), 0.4f);
    }

    @Override
    public Identifier getTexture(SnailEntity snail) {
        return TEXTURE;
    }
}

