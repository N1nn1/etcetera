package com.ninni.etcetera.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

import ModelPart;
import TexturedModelData;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class ChappleModel<T extends Entity> extends AnimalModel<T> {
    private static final String BEAK = "beak";
    private static final String WATTLE = "wattle";
    private static final String STALK = "stalk";

    private final ModelPart head;
    private final ModelPart beak;
    private final ModelPart wattle;
    private final ModelPart body;
    private final ModelPart stalk;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public ChappleModel(ModelPart root) {
        head = root.getChild(HEAD);
        body = root.getChild(BODY);

        beak = head.getChild(BEAK);
        wattle = head.getChild(WATTLE);

        stalk = body.getChild(STALK);
        leftWing = body.getChild(LEFT_WING);
        rightWing = body.getChild(RIGHT_WING);
        leftLeg = body.getChild(LEFT_LEG);
        rightLeg = body.getChild(RIGHT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(
                HEAD,
                ModelPartBuilder.create()
                        .uv(0, 14)
                        .cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F),
                ModelTransform.pivot(0.0F, 15.0F, -4.0F)
        );

        ModelPartData bill = head.addChild(
                BEAK,
                ModelPartBuilder.create()
                        .uv(0, 23)
                        .cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );


        ModelPartData chin = head.addChild(
                WATTLE,
                ModelPartBuilder.create()
                        .uv(0, 27)
                        .cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );

        ModelPartData body = modelPartData.addChild(
                BODY,
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F),
                ModelTransform.pivot(0.0F, 16.0F, 0.0F)
        );

        ModelPartData stalk = body.addChild(
                STALK,
                ModelPartBuilder.create()
                        .uv(0, -1)
                        .cuboid(0.0F, -5.0F, -0.5F, 0.0F, 5.0F, 4.0F),
                ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        ModelPartData left_wing = body.addChild(
                LEFT_WING,
                ModelPartBuilder.create()
                        .uv(14, 14)
                        .cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                ModelTransform.pivot(4.0F, -3.0F, 0.0F)
        );

        ModelPartData right_wing = body.addChild(
                RIGHT_WING,
                ModelPartBuilder.create()
                        .uv(14, 14)
                        .mirrored()
                        .cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F)
                        .mirrored(false),
                ModelTransform.pivot(-4.0F, -3.0F, 0.0F)
        );

        ModelPartData left_leg = body.addChild(
                LEFT_LEG,
                ModelPartBuilder.create()
                        .uv(9, 24)
                        .cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                ModelTransform.pivot(1.0F, 3.0F, 1.0F)
        );

        ModelPartData right_leg = body.addChild(
                RIGHT_LEG,
                ModelPartBuilder.create()
                        .uv(9, 24)
                        .mirrored()
                        .cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                ModelTransform.pivot(-2.0F, 3.0F, 1.0F)
        );

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.pitch = headPitch * ((float)Math.PI / 180);
        head.yaw = headYaw * ((float)Math.PI / 180);
        rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
        rightWing.roll = animationProgress;
        leftWing.roll = -animationProgress;
    }
}