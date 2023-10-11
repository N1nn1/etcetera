package com.ninni.etcetera.client.model;

import com.ninni.etcetera.client.render.animation.RubberChickenAnimations;
import com.ninni.etcetera.entity.RubberChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.BODY;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class RubberChickenModel<T extends RubberChickenEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;

    public RubberChickenModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 7).cuboid(-2.0F, -6.0F, -3.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.root.getChild(BODY).yaw = (float)Math.PI / 180 * entity.getYaw() + (float)Math.PI;

        //TODO this is not working
        this.updateAnimation(entity.squeezingAnimationState, RubberChickenAnimations.SQUEEZE, animationProgress);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}