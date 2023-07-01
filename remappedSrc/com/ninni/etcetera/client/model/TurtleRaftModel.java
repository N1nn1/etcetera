package com.ninni.etcetera.client.model;

import ModelPart;
import TexturedModelData;
import com.google.common.collect.ImmutableList;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class TurtleRaftModel extends CompositeEntityModel<TurtleRaftEntity> {
    private static final String LEFT_PADDLE = "left_paddle";
    private static final String RIGHT_PADDLE = "right_paddle";
    private static final String BOTTOM = "bottom";

    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;
    private final ModelPart bottom;

    public TurtleRaftModel(ModelPart root) {
        this.leftPaddle = root.getChild(LEFT_PADDLE);
        this.rightPaddle = root.getChild(RIGHT_PADDLE);
        this.bottom = root.getChild(BOTTOM);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.bottom, this.leftPaddle, this.rightPaddle);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                BOTTOM,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-7.0F, -4.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0f, -(float)Math.PI/2, 0.0f)
        );

        modelPartData.addChild(
                LEFT_PADDLE,
                ModelPartBuilder.create()
                        .uv(62, 0)
                        .cuboid(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f)
                        .cuboid(-1.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f),
                ModelTransform.of(3.0f, -3.0f, 9.0f, 0.0f, 0.0f, 0.19634955f)
        );

        modelPartData.addChild(
                RIGHT_PADDLE,
                ModelPartBuilder.create()
                        .uv(62, 20).cuboid(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f)
                        .cuboid(0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f),
                ModelTransform.of(3.0f, -3.0f, -9.0f, 0.0f, (float)Math.PI, 0.19634955f)
        );

        return TexturedModelData.of(modelData, 128, 64);
    }


    @Override
    public void setAngles(TurtleRaftEntity raft, float f, float g, float h, float i, float j) {
        setPaddleAngle(raft, 0, this.leftPaddle, f);
        setPaddleAngle(raft, 1, this.rightPaddle, f);
    }

    private static void setPaddleAngle(BoatEntity entity, int sigma, ModelPart part, float angle) {
        float f = entity.interpolatePaddlePhase(sigma, angle);
        part.pitch = MathHelper.clampedLerp(-1.0471976f, -0.2617994f, (MathHelper.sin(-f) + 1.0f) / 2.0f);
        part.yaw = MathHelper.clampedLerp(-0.7853982f, 0.7853982f, (MathHelper.sin(-f + 1.0f) + 1.0f) / 2.0f);
        if (sigma == 1) {
            part.yaw = (float)Math.PI - part.yaw;
        }
    }
}
