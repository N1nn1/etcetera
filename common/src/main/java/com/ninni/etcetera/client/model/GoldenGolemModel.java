package com.ninni.etcetera.client.model;

import com.ninni.etcetera.client.render.animation.GoldenGolemAnimations;
import com.ninni.etcetera.entity.GoldenGolemEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.model.geom.PartNames.*;

@SuppressWarnings("FieldCanBeLocal, unused")
public class GoldenGolemModel<T extends GoldenGolemEntity> extends HierarchicalModel<T> {
    public static final String ALL = "all";

    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart head;
    private final ModelPart arms;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public GoldenGolemModel(ModelPart root) {
        this.root = root;

        this.all = root.getChild(ALL);
        this.head = all.getChild(HEAD);
        this.arms = all.getChild(ARMS);
        this.rightWing = all.getChild(RIGHT_WING);
        this.leftWing = all.getChild(LEFT_WING);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition all = modelPartData.addOrReplaceChild(
                ALL,
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 18.0F, 0.0F)
        );

        PartDefinition head = all.addOrReplaceChild(
                HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 13)
                        .addBox(-3.0F, 0.0F, -2.5F, 6.0F, 1.0F, 0.0F)
                        .texOffs(0, 0)
                        .addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, -0.5F)
        );

        PartDefinition arms = all.addOrReplaceChild(
                ARMS,
                CubeListBuilder.create()
                        .texOffs(20, 8)
                        .addBox(-2.0F, 2.0F, -1.5F, 4.0F, 2.0F, 2.0F)
                        .texOffs(17, 0)
                        .addBox(1.0F, -1.0F, -1.5F, 1.0F, 3.0F, 2.0F)
                        .texOffs(17, 0)
                        .mirror()
                        .addBox(-2.0F, -1.0F, -1.5F, 1.0F, 3.0F, 2.0F)
                        .mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        PartDefinition rightWing = all.addOrReplaceChild(
                RIGHT_WING,
                CubeListBuilder.create()
                        .texOffs(0, 2)
                        .mirror()
                        .addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F)
                        .mirror(false),
                PartPose.offset(-0.5F, 0.5F, 1.0F)
        );

        PartDefinition leftWing = all.addOrReplaceChild(
                LEFT_WING,
                CubeListBuilder.create()
                        .texOffs(0, 2)
                        .addBox(0.0F, 0.0F, 0.0F, 0.0F, 3.0F, 8.0F),
                PartPose.offset(0.5F, 0.5F, 1.0F)
        );

        return LayerDefinition.create(modelData, 32, 16);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (entity.getPose() != Pose.CROAKING) {
            float l = Mth.cos(animationProgress * 20.0F * 0.017453292F + limbAngle) * 3.1415927F * 0.15F + limbDistance;
            float n = animationProgress * 9.0F * 0.017453292F;
            float tilt = Math.min(limbDistance / 0.3F, 1.0F);
            float p = 1.0F - tilt;

            this.head.xRot = headPitch * ((float) Math.PI / 180);
            this.head.yRot = headYaw * ((float) Math.PI / 180);

            this.rightWing.xRot = tilt;
            this.arms.xRot = tilt;
            this.leftWing.xRot = tilt;

            this.rightWing.yRot = -0.7853982F + l;
            this.leftWing.yRot = 0.7853982F - l;
            this.all.y += (float) Math.cos(n) * 0.25F * p;
            this.arms.zRot = Mth.cos(n + 4.712389F) * 3.1415927F * 0.025F * p;
        } else {
            this.animate(entity.grantingAnimationState, GoldenGolemAnimations.GRANT, animationProgress);
        }
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}