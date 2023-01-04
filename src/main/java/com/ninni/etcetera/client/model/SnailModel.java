package com.ninni.etcetera.client.model;

import com.ninni.etcetera.entity.SnailEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(value= EnvType.CLIENT)
public class SnailModel extends EntityModel<SnailEntity> {
    private static final String FOOT = "foot";
    private static final String WHISKERS = "whiskers";
    private static final String SHELL = "shell";

    private final ModelPart shell;
    private final ModelPart foot;
    private final ModelPart whiskers;
    private final ModelPart leftEye;
    private final ModelPart rightEye;

    public SnailModel(ModelPart root) {
        this.shell = root.getChild(SHELL);
        this.foot = root.getChild(FOOT);

        this.whiskers = foot.getChild(WHISKERS);
        this.leftEye = foot.getChild(LEFT_EYE);
        this.rightEye = foot.getChild(RIGHT_EYE);
    }

    @SuppressWarnings("unused")
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        
        ModelPartData shell = modelPartData.addChild(
                SHELL,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-5.0F, -7.0F, 0.0F, 10.0F, 10.0F, 10.0F)
                        .uv(0, 20)
                        .cuboid(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 19.0F, -1.0F)
        );

        ModelPartData foot = modelPartData.addChild(
                FOOT,
                ModelPartBuilder.create()
                        .uv(28, 8)
                        .cuboid(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 12.0F), 
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        ModelPartData whiskers = foot.addChild(
                WHISKERS, 
                ModelPartBuilder.create()
                        .uv(8, 24)
                        .cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 1.0F),
                ModelTransform.of(0.0F, -4.0F, -6.0F, 0.7418F, 0.0F, 0.0F)
        );

        ModelPartData leftEye = foot.addChild(
                LEFT_EYE,
                ModelPartBuilder.create()
                        .uv(12, 26)
                        .cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F)
                        .uv(0, 24)
                        .cuboid(-1.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F),
                ModelTransform.pivot(2.0F, -5.0F, -4.5F)
        );

        ModelPartData rightEye = foot.addChild(
                RIGHT_EYE,
                ModelPartBuilder.create()
                        .uv(12, 26)
                        .mirrored()
                        .cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F)
                        .mirrored(false)
                        .uv(0, 24)
                        .mirrored()
                        .cuboid(-1.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F)
                        .mirrored(false), 
                ModelTransform.pivot(-2.0F, -5.0F, -4.5F)
        );
       
        return TexturedModelData.of(modelData, 64, 32);
    }
    @Override
    public void setAngles(SnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        float pi = (float)Math.PI;

        float speed = 1;
        float degree = 1;
        float tilt = Math.min(limbSwingAmount * 2, 0.5f);

        shell.visible = entity.getShellGrowthTicks() == 0;

        //eye looking direction
        leftEye.pitch = headPitch * pi/180;
        leftEye.yaw = headYaw * pi/180;
        rightEye.pitch = headPitch * pi/180;
        rightEye.yaw = headYaw * pi/180;

        //random eye rotation
        leftEye.roll = MathHelper.sin(ageInTicks * speed * 0.05F) * degree * 0.1F;
        leftEye.pitch += MathHelper.cos(ageInTicks * speed * 0.025F) * degree * 0.2F;
        leftEye.pivotY = MathHelper.cos(ageInTicks * speed * 0.025F + pi/2) * degree - 5.0F;
        rightEye.roll = MathHelper.sin(ageInTicks * speed * 0.05F + pi) * degree * 0.1F;
        rightEye.pitch += MathHelper.cos(ageInTicks * speed * 0.025F + pi) * degree * 0.2F;
        rightEye.pivotY = MathHelper.cos(ageInTicks * speed * 0.025F - pi/2) * degree - 5.0F;

        //body scaling along when the snail slithers
        foot.zScale = 1 + tilt;
        leftEye.zScale = 1 - tilt;
        rightEye.zScale = 1 - tilt;

        //random whiskers rotation
        whiskers.pitch = MathHelper.sin(ageInTicks * speed * 0.05F + pi/2) * degree * 0.5F + pi/4;

        //retreating in its shell
        if (entity.isScared()) {
            this.foot.visible = false;
            this.shell.pivotY = 21.0F;
            this.shell.pivotZ = -4.0F;
            this.shell.roll = 0.0F;
        } else {
            shell.roll = MathHelper.sin(limbSwing * speed * 0.6F) * degree * -0.5F * limbSwingAmount;
            this.foot.visible = true;
            this.shell.pivotY = 19.0F;
            this.shell.pivotZ = -1.0F;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        shell.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        foot.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}