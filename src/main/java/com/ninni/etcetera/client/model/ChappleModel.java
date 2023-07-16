package com.ninni.etcetera.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class ChappleModel<T extends Entity> extends AgeableListModel<T> {
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
        head = root.getChild(PartNames.HEAD);
        body = root.getChild(PartNames.BODY);

        beak = head.getChild(BEAK);
        wattle = head.getChild(WATTLE);

        stalk = body.getChild(STALK);
        leftWing = body.getChild(PartNames.LEFT_WING);
        rightWing = body.getChild(PartNames.RIGHT_WING);
        leftLeg = body.getChild(PartNames.LEFT_LEG);
        rightLeg = body.getChild(PartNames.RIGHT_LEG);
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 14)
                        .addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F)
        );

        PartDefinition bill = head.addOrReplaceChild(
                PartNames.BEAK,
                CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );


        PartDefinition chin = head.addOrReplaceChild(
                WATTLE,
                CubeListBuilder.create()
                        .texOffs(0, 27)
                        .addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        PartDefinition body = modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -3.0F, -4.0F, 6.0F, 6.0F, 8.0F),
                PartPose.offset(0.0F, 16.0F, 0.0F)
        );

        PartDefinition stalk = body.addOrReplaceChild(
                STALK,
                CubeListBuilder.create()
                        .texOffs(0, -1)
                        .addBox(0.0F, -5.0F, -0.5F, 0.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild(
                PartNames.LEFT_WING,
                CubeListBuilder.create()
                        .texOffs(14, 14)
                        .addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                PartPose.offset(4.0F, -3.0F, 0.0F)
        );

        PartDefinition right_wing = body.addOrReplaceChild(
                PartNames.RIGHT_WING,
                CubeListBuilder.create()
                        .texOffs(14, 14)
                        .mirror()
                        .addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F)
                        .mirror(false),
                PartPose.offset(-4.0F, -3.0F, 0.0F)
        );

        PartDefinition left_leg = body.addOrReplaceChild(
                PartNames.LEFT_LEG,
                CubeListBuilder.create()
                        .texOffs(9, 24)
                        .addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(1.0F, 3.0F, 1.0F)
        );

        PartDefinition right_leg = body.addOrReplaceChild(
                PartNames.RIGHT_LEG,
                CubeListBuilder.create()
                        .texOffs(9, 24)
                        .mirror()
                        .addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(-2.0F, 3.0F, 1.0F)
        );

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.xRot = headPitch * ((float)Math.PI / 180);
        head.yRot = headYaw * ((float)Math.PI / 180);
        rightLeg.xRot = Mth.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        leftLeg.xRot = Mth.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
        rightWing.zRot = animationProgress;
        leftWing.zRot = -animationProgress;
    }
}