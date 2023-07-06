package com.ninni.etcetera.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.LivingEntity;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class SweaterModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public SweaterModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getTexturedModelData() {
        Dilation dilation = new Dilation(0.5f);
        Dilation armsDilation = new Dilation(0.35f);
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0f);
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.BODY,
                ModelPartBuilder.create()
                        .uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f)
        );

        modelPartData.addChild(
                EntityModelPartNames.RIGHT_ARM,
                ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, armsDilation),
                ModelTransform.pivot(-5.0f, 2.0f, 0.0f)
        );

        modelPartData.addChild(
                EntityModelPartNames.LEFT_ARM,
                ModelPartBuilder.create().uv(40, 16)
                        .mirrored()
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, armsDilation),
                ModelTransform.pivot(5.0f, 2.0f, 0.0f)
        );


        return TexturedModelData.of(modelData, 64, 32);
    }
}

