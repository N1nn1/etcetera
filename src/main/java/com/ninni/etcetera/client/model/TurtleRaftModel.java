package com.ninni.etcetera.client.model;

import com.google.common.collect.ImmutableList;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurtleRaftModel extends ListModel<TurtleRaftEntity> {
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
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.bottom, this.leftPaddle, this.rightPaddle);
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(
                BOTTOM,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-7.0F, -4.0F, -7.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0f, -(float)Math.PI/2, 0.0f)
        );

        modelPartData.addOrReplaceChild(
                LEFT_PADDLE,
                CubeListBuilder.create()
                        .texOffs(62, 0)
                        .addBox(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f)
                        .addBox(-1.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f),
                PartPose.offsetAndRotation(3.0f, -3.0f, 9.0f, 0.0f, 0.0f, 0.19634955f)
        );

        modelPartData.addOrReplaceChild(
                RIGHT_PADDLE,
                CubeListBuilder.create()
                        .texOffs(62, 20).addBox(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f)
                        .addBox(0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f),
                PartPose.offsetAndRotation(3.0f, -3.0f, -9.0f, 0.0f, (float)Math.PI, 0.19634955f)
        );

        return LayerDefinition.create(modelData, 128, 64);
    }


    @Override
    public void setupAnim(TurtleRaftEntity raft, float f, float g, float h, float i, float j) {
        setPaddleAngle(raft, 0, this.leftPaddle, f);
        setPaddleAngle(raft, 1, this.rightPaddle, f);
    }

    private static void setPaddleAngle(Boat entity, int sigma, ModelPart part, float angle) {
        float f = entity.getRowingTime(sigma, angle);
        part.xRot = Mth.clampedLerp(-1.0471976f, -0.2617994f, (Mth.sin(-f) + 1.0f) / 2.0f);
        part.yRot = Mth.clampedLerp(-0.7853982f, 0.7853982f, (Mth.sin(-f + 1.0f) + 1.0f) / 2.0f);
        if (sigma == 1) {
            part.yRot = (float)Math.PI - part.yRot;
        }
    }

}
