package com.ninni.etcetera.client.model;

import com.ninni.etcetera.entity.WeaverEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.model.geom.PartNames.*;

@SuppressWarnings("FieldCanBeLocal, unused")
public class WeaverModel<T extends WeaverEntity> extends HierarchicalModel<T> {
    private static final String RIGHT_MIDDLE_FRONT_LEG = "right_Middle_front_leg";
    private static final String LEFT_MIDDLE_FRONT_LEG = "left_Middle_front_leg";
    private static final String RIGHT_MIDDLE_HIND_LEG = "right_Middle_hind_leg";
    private static final String LEFT_MIDDLE_HIND_LEG = "left_Middle_hind_leg";

    private final ModelPart root;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftMiddleFrontLeg;
    private final ModelPart rightMiddleFrontLeg;
    private final ModelPart leftMiddleHindLeg;
    private final ModelPart rightMiddleHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;

    public WeaverModel(ModelPart root) {
        this.root = root;

        this.neck = root.getChild(NECK);
        this.leftFrontLeg = root.getChild(LEFT_FRONT_LEG);
        this.rightFrontLeg = root.getChild(RIGHT_FRONT_LEG);
        this.leftMiddleFrontLeg = root.getChild(LEFT_MIDDLE_FRONT_LEG);
        this.rightMiddleFrontLeg = root.getChild(RIGHT_MIDDLE_FRONT_LEG);
        this.leftMiddleHindLeg = root.getChild(LEFT_MIDDLE_HIND_LEG);
        this.rightMiddleHindLeg = root.getChild(RIGHT_MIDDLE_HIND_LEG);
        this.leftHindLeg = root.getChild(LEFT_HIND_LEG);
        this.rightHindLeg = root.getChild(RIGHT_HIND_LEG);

        this.head = this.neck.getChild(HEAD);
        this.body = this.neck.getChild(BODY);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition neck = modelPartData.addOrReplaceChild(
                NECK,
                CubeListBuilder.create()
                        .texOffs(80, 52)
                        .addBox(-3.0F, -2.5F, -4.0F, 6.0F, 5.0F, 7.0F)
                        .texOffs(80, 64).addBox(-3.0F, -2.5F, -4.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 15.5F, -3.0F)
        );

        PartDefinition head = neck.addOrReplaceChild(
                HEAD,
                CubeListBuilder.create()
                        .texOffs(60, 0)
                        .addBox(-7.0F, -5.0F, -8.0F, 14.0F, 8.0F, 8.0F)
                        .texOffs(60, 36)
                        .addBox(-7.0F, -5.0F, -8.0F, 14.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
                        .texOffs(60, 52)
                        .addBox(-4.0F, -5.0F, 1.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, -0.5F, -4.0F)
        );

        PartDefinition body = neck.addOrReplaceChild(
                BODY,
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-10.0F, -13.0F, 0.0F, 20.0F, 16.0F, 20.0F)
                        .texOffs(0, 36)
                        .addBox(-10.0F, -13.0F, 0.4F, 20.0F, 16.0F, 20.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.5F, 3.0F)
        );

        PartDefinition leftFrontLeg = modelPartData.addOrReplaceChild(
                LEFT_FRONT_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .texOffs(0, 76)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(3.0F, 17.0F, -7.0F)
        );

        PartDefinition rightFrontLeg = modelPartData.addOrReplaceChild(
                RIGHT_FRONT_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .mirror(false)
                        .texOffs(0, 76).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                        .mirror(false),
                PartPose.offset(-3.0F, 17.0F, -7.0F)
        );

        PartDefinition leftMiddleFrontLeg = modelPartData.addOrReplaceChild(
                LEFT_MIDDLE_FRONT_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .texOffs(0, 76)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(3.0F, 17.0F, -4.0F)
        );

        PartDefinition rightMiddleFrontLeg = modelPartData.addOrReplaceChild(
                RIGHT_MIDDLE_FRONT_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .mirror(false)
                        .texOffs(0, 76).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                        .mirror(false),
                PartPose.offset(-3.0F, 17.0F, -4.0F)
        );

        PartDefinition leftMiddleHindLeg = modelPartData.addOrReplaceChild(
                LEFT_MIDDLE_HIND_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .texOffs(0, 76)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(3.0F, 17.0F, -1.0F)
        );

        PartDefinition rightMiddleHindLeg = modelPartData.addOrReplaceChild(
                RIGHT_MIDDLE_HIND_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .mirror(false)
                        .texOffs(0, 76).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                        .mirror(false),
                PartPose.offset(-3.0F, 17.0F, -1.0F)
        );

        PartDefinition leftHindLeg = modelPartData.addOrReplaceChild(
                LEFT_HIND_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .texOffs(0, 76)
                        .addBox(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)),
                PartPose.offset(3.0F, 17.0F, 2.0F)
        );

        PartDefinition rightHindLeg = modelPartData.addOrReplaceChild(
                RIGHT_HIND_LEG,
                CubeListBuilder.create()
                        .texOffs(0, 72).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F)
                        .mirror(false)
                        .texOffs(0, 76).mirror()
                        .addBox(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.25F))
                        .mirror(false),
                PartPose.offset(-3.0F, 17.0F, 2.0F)
        );
        return LayerDefinition.create(modelData, 112, 80);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.xRot = headPitch * ((float) Math.PI / 180);
        head.yRot = headYaw * ((float) Math.PI / 180);

        this.neck.y = (float) (Math.cos(animationProgress * 0.075F) * 0.5f + 15.75F);
        this.head.y = (float) (Math.cos(animationProgress * 0.075F - 0.5f) * -0.5f - 0.25F);
        this.body.y = (float) (Math.cos(animationProgress * 0.075F + 0.25f) * -0.2f + 0.75F);
        this.body.zRot = (float) (Math.cos(animationProgress * 2F + 0.5f) * 0.0075f);

        float f = 0.7853982F;
        this.rightHindLeg.zRot = -0.7853982F;
        this.leftHindLeg.zRot = 0.7853982F;
        this.rightMiddleHindLeg.zRot = -0.58119464F;
        this.leftMiddleHindLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = -0.7853982F;
        this.leftFrontLeg.zRot = 0.7853982F;
        float g = -0.0F;
        float h = 0.3926991F;
        this.rightHindLeg.yRot = 0.7853982F;
        this.leftHindLeg.yRot = -0.7853982F;
        this.rightMiddleHindLeg.yRot = 0.3926991F;
        this.leftMiddleHindLeg.yRot = -0.3926991F;
        this.rightMiddleFrontLeg.yRot = -0.3926991F;
        this.leftMiddleFrontLeg.yRot = 0.3926991F;
        this.rightFrontLeg.yRot = -0.7853982F;
        this.leftFrontLeg.yRot = 0.7853982F;
        float i = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
        float j = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbDistance;
        float k = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbDistance;
        float l = -(Mth.cos(limbAngle * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbDistance;
        float m = Math.abs(Mth.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
        float n = Math.abs(Mth.sin(limbAngle * 0.6662F + 3.1415927F) * 0.4F) * limbDistance;
        float o = Math.abs(Mth.sin(limbAngle * 0.6662F + 1.5707964F) * 0.4F) * limbDistance;
        float p = Math.abs(Mth.sin(limbAngle * 0.6662F + 4.712389F) * 0.4F) * limbDistance;

        ModelPart var10000 = this.rightHindLeg;
        var10000.yRot += i;
        var10000 = this.leftHindLeg;
        var10000.yRot -= i;
        var10000 = this.rightMiddleHindLeg;
        var10000.yRot += j;
        var10000 = this.leftMiddleHindLeg;
        var10000.yRot -= j;
        var10000 = this.rightMiddleFrontLeg;
        var10000.yRot += k;
        var10000 = this.leftMiddleFrontLeg;
        var10000.yRot -= k;
        var10000 = this.rightFrontLeg;
        var10000.yRot += l;
        var10000 = this.leftFrontLeg;
        var10000.yRot -= l;
        var10000 = this.rightHindLeg;
        var10000.zRot += m;
        var10000 = this.leftHindLeg;
        var10000.zRot -= m;
        var10000 = this.rightMiddleHindLeg;
        var10000.zRot += n;
        var10000 = this.leftMiddleHindLeg;
        var10000.zRot -= n;
        var10000 = this.rightMiddleFrontLeg;
        var10000.zRot += o;
        var10000 = this.leftMiddleFrontLeg;
        var10000.zRot -= o;
        var10000 = this.rightFrontLeg;
        var10000.zRot += p;
        var10000 = this.leftFrontLeg;
        var10000.zRot -= p;
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}