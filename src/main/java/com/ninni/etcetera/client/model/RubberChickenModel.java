package com.ninni.etcetera.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.BODY;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class RubberChickenModel<T extends Entity> extends AnimalModel<T> {

    private final ModelPart body;

    public RubberChickenModel(ModelPart root) {
        body = root.getChild(BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild(
                BODY,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                        .uv(0, 7).cuboid(-2.0F, -6.0F, -3.0F, 4.0F, 3.0F, 3.0F)
                        .uv(0, 14).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}