package com.ninni.etcetera.client.model;

import com.ninni.etcetera.entity.WeaverEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@SuppressWarnings("FieldCanBeLocal, unused")
@Environment(value= EnvType.CLIENT)
public class WeaverModel<T extends WeaverEntity> extends SinglePartEntityModel<T> {
    private static final String RIGHT_MiddleDLE_FRONT_LEG = "right_Middle_front_leg";
    private static final String LEFT_MiddleDLE_FRONT_LEG = "left_Middle_front_leg";
    private static final String RIGHT_MiddleDLE_HIND_LEG = "right_Middle_hind_leg";
    private static final String LEFT_MiddleDLE_HIND_LEG = "left_Middle_hind_leg";

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
        this.leftMiddleFrontLeg = root.getChild(LEFT_MiddleDLE_FRONT_LEG);
        this.rightMiddleFrontLeg = root.getChild(RIGHT_MiddleDLE_FRONT_LEG);
        this.leftMiddleHindLeg = root.getChild(LEFT_MiddleDLE_HIND_LEG);
        this.rightMiddleHindLeg = root.getChild(RIGHT_MiddleDLE_HIND_LEG);
        this.leftHindLeg = root.getChild(LEFT_HIND_LEG);
        this.rightHindLeg = root.getChild(RIGHT_HIND_LEG);

        this.head = this.neck.getChild(HEAD);
        this.body = this.neck.getChild(BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData neck = modelPartData.addChild(NECK, ModelPartBuilder.create().uv(80, 52).cuboid(-3.0F, -2.5F, -4.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F))
                .uv(80, 64).cuboid(-3.0F, -2.5F, -4.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 15.5F, -3.0F));

        ModelPartData head = neck.addChild(HEAD, ModelPartBuilder.create().uv(60, 0).cuboid(-7.0F, -5.0F, -8.0F, 14.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(60, 36).cuboid(-7.0F, -5.0F, -8.0F, 14.0F, 8.0F, 8.0F, new Dilation(0.5F))
                .uv(60, 52).cuboid(-4.0F, -5.0F, 1.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -0.5F, -4.0F));

        ModelPartData body = neck.addChild(BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 16.0F, 20.0F, new Dilation(0.0F))
                .uv(0, 36).cuboid(-10.0F, -13.0F, 0.4F, 20.0F, 16.0F, 20.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.5F, 3.0F));

        ModelPartData leftFrontLeg = modelPartData.addChild(LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 72).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 76).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 17.0F, -7.0F));

        ModelPartData rightFrontLeg = modelPartData.addChild(RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 72).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 76).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(-3.0F, 17.0F, -7.0F));

        ModelPartData leftMiddleFrontLeg = modelPartData.addChild(LEFT_MiddleDLE_FRONT_LEG, ModelPartBuilder.create().uv(0, 72).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 76).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 17.0F, -4.0F));

        ModelPartData rightMiddleFrontLeg = modelPartData.addChild(RIGHT_MiddleDLE_FRONT_LEG, ModelPartBuilder.create().uv(0, 72).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 76).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(-3.0F, 17.0F, -4.0F));

        ModelPartData leftMiddleHindLeg = modelPartData.addChild(LEFT_MiddleDLE_HIND_LEG, ModelPartBuilder.create().uv(0, 72).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 76).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 17.0F, -1.0F));

        ModelPartData rightMiddleHindLeg = modelPartData.addChild(RIGHT_MiddleDLE_HIND_LEG, ModelPartBuilder.create().uv(0, 72).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 76).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(-3.0F, 17.0F, -1.0F));

        ModelPartData leftHindLeg = modelPartData.addChild(LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 72).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 76).cuboid(0.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 17.0F, 2.0F));

        ModelPartData rightHindLeg = modelPartData.addChild(RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 72).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 76).mirrored().cuboid(-20.0F, -1.0F, -1.0F, 20.0F, 2.0F, 2.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(-3.0F, 17.0F, 2.0F));
        return TexturedModelData.of(modelData, 112, 80);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.pitch = headPitch * ((float)Math.PI / 180);
        head.yaw = headYaw * ((float)Math.PI / 180);


        this.neck.pivotY = (float)(Math.cos(animationProgress * 0.075F) * 0.5f + 15.75F);
        this.head.pivotY = (float)(Math.cos(animationProgress * 0.075F - 0.5f) * -0.5f - 0.25F);
        this.body.pivotY = (float)(Math.cos(animationProgress * 0.075F + 0.25f) * -0.2f + 0.75F);
        this.body.roll = (float)(Math.cos(animationProgress * 2F + 0.5f) * 0.0075f);

        float f = 0.7853982F;
        this.rightHindLeg.roll = -0.7853982F;
        this.leftHindLeg.roll = 0.7853982F;
        this.rightMiddleHindLeg.roll = -0.58119464F;
        this.leftMiddleHindLeg.roll = 0.58119464F;
        this.rightMiddleFrontLeg.roll = -0.58119464F;
        this.leftMiddleFrontLeg.roll = 0.58119464F;
        this.rightFrontLeg.roll = -0.7853982F;
        this.leftFrontLeg.roll = 0.7853982F;
        float g = -0.0F;
        float h = 0.3926991F;
        this.rightHindLeg.yaw = 0.7853982F;
        this.leftHindLeg.yaw = -0.7853982F;
        this.rightMiddleHindLeg.yaw = 0.3926991F;
        this.leftMiddleHindLeg.yaw = -0.3926991F;
        this.rightMiddleFrontLeg.yaw = -0.3926991F;
        this.leftMiddleFrontLeg.yaw = 0.3926991F;
        this.rightFrontLeg.yaw = -0.7853982F;
        this.leftFrontLeg.yaw = 0.7853982F;
        float i = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
        float j = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbDistance;
        float k = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbDistance;
        float l = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbDistance;
        float m = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
        float n = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 3.1415927F) * 0.4F) * limbDistance;
        float o = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 1.5707964F) * 0.4F) * limbDistance;
        float p = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 4.712389F) * 0.4F) * limbDistance;
        ModelPart var10000 = this.rightHindLeg;
        var10000.yaw += i;
        var10000 = this.leftHindLeg;
        var10000.yaw += -i;
        var10000 = this.rightMiddleHindLeg;
        var10000.yaw += j;
        var10000 = this.leftMiddleHindLeg;
        var10000.yaw += -j;
        var10000 = this.rightMiddleFrontLeg;
        var10000.yaw += k;
        var10000 = this.leftMiddleFrontLeg;
        var10000.yaw += -k;
        var10000 = this.rightFrontLeg;
        var10000.yaw += l;
        var10000 = this.leftFrontLeg;
        var10000.yaw += -l;
        var10000 = this.rightHindLeg;
        var10000.roll += m;
        var10000 = this.leftHindLeg;
        var10000.roll += -m;
        var10000 = this.rightMiddleHindLeg;
        var10000.roll += n;
        var10000 = this.leftMiddleHindLeg;
        var10000.roll += -n;
        var10000 = this.rightMiddleFrontLeg;
        var10000.roll += o;
        var10000 = this.leftMiddleFrontLeg;
        var10000.roll += -o;
        var10000 = this.rightFrontLeg;
        var10000.roll += p;
        var10000 = this.leftFrontLeg;
        var10000.roll += -p;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}