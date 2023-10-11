package com.ninni.etcetera.client.model;

import com.ninni.etcetera.client.render.animation.RubberChickenAnimations;
import com.ninni.etcetera.entity.RubberChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.BODY;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class RubberChickenModel<T extends RubberChickenEntity> extends SinglePartEntityModel<T> {

    private final ModelPart body;

    public RubberChickenModel(ModelPart root) {
        body = root.getChild(BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData body = modelPartData.addChild(
                BODY,
                ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                        .uv(0, 7)
                        .cuboid(-2.0F, -6.0F, -3.0F, 4.0F, 3.0F, 3.0F)
                        .uv(0, 14)
                        .cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 1.0F, 1.0F),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.body.yaw = (float)Math.PI / 180 * entity.getYaw() + (float)Math.PI;

        //TODO this is not working
        this.updateAnimation(entity.squeezingAnimationState, RubberChickenAnimations.SQUEEZE, animationProgress);
    }

    @Override
    public ModelPart getPart() {
        return this.body;
    }
}