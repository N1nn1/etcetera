package com.ninni.etcetera.client.model;

import com.ninni.etcetera.entity.GoldenGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class GoldenGolemModel<T extends GoldenGolemEntity> extends SinglePartEntityModel<T> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public GoldenGolemModel(ModelPart root) {
        this.root = root;

        this.head = root.getChild(HEAD);
        this.arms = root.getChild(ARMS);
        this.rightWing = root.getChild(RIGHT_WING);
        this.leftWing = root.getChild(LEFT_WING);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        
        ModelPartData head = modelPartData.addChild(
                HEAD,
                ModelPartBuilder.create()
                        .uv(0, 13)
                        .cuboid(-3.0F, 0.0F, -2.5F, 6.0F, 1.0F, 0.0F)
                .uv(0, 0).cuboid(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F),
                ModelTransform.pivot(0.0F, 18.0F, -0.5F)
        );

        ModelPartData arms = modelPartData.addChild(
                ARMS,
                ModelPartBuilder.create()
                        .uv(20, 8)
                        .cuboid(-2.0F, 2.0F, -1.5F, 4.0F, 2.0F, 2.0F)
                        .uv(17, 0)
                        .cuboid(1.0F, -1.0F, -1.5F, 1.0F, 3.0F, 2.0F)
                        .uv(17, 0)
                        .mirrored()
                        .cuboid(-2.0F, -1.0F, -1.5F, 1.0F, 3.0F, 2.0F)
                        .mirrored(false),
                ModelTransform.pivot(0.0F, 18.0F, 0.0F)
        );

        ModelPartData rightWing = modelPartData.addChild(
                RIGHT_WING,
                ModelPartBuilder.create()
                        .uv(0, 2)
                        .mirrored()
                        .cuboid(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F)
                        .mirrored(false),
                ModelTransform.pivot(-0.5F, 18.5F, 1.0F)
        );

        ModelPartData leftWing = modelPartData.addChild(
                LEFT_WING,
                ModelPartBuilder.create()
                        .uv(0, 2)
                        .cuboid(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F),
                ModelTransform.pivot(0.5F, 18.5F, 1.0F)
        );


        return TexturedModelData.of(modelData, 32, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        float k = animationProgress * 20.0F * 0.017453292F + limbAngle;
        float l = MathHelper.cos(k) * 3.1415927F * 0.15F + limbDistance;
        float m = animationProgress - (float)entity.age;
        float n = animationProgress * 9.0F * 0.017453292F;
        float tilt = Math.min(limbDistance / 0.3F, 1.0F);
        float p = 1.0F - tilt;
        head.pitch = headPitch * ((float)Math.PI / 180);
        head.yaw = headYaw * ((float)Math.PI / 180);

        this.rightWing.pitch = tilt;
        this.arms.pitch = tilt;
        this.leftWing.pitch = tilt;

        this.rightWing.yaw = -0.7853982F + l;
        this.leftWing.yaw = 0.7853982F - l;
        ModelPart root = this.root;
        root.pivotY += (float)Math.cos(n) * 0.25F * p;
        this.arms.roll = MathHelper.cos(n + 4.712389F) * 3.1415927F * 0.025F * p;
    }


    @Override
    public ModelPart getPart() {
        return this.root;
    }
}