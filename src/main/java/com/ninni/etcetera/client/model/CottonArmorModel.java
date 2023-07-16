package com.ninni.etcetera.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public class CottonArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
    public CottonArmorModel(ModelPart modelPart) {
        super(modelPart);
    }
    
    public static LayerDefinition createLayerDefinition() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(58, 66)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(1.0F))
                        .texOffs(56, 48)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.51F))
                        .texOffs(31, 79)
                        .addBox(-4.5F, 0.3163F, -4.5F, 9.0F, 0.0F, 9.0F)
                        .texOffs(-12, 64)
                        .addBox(-6.0F, -5.21F, -6.0F, 12.0F, 0.0F, 12.0F)
                        .texOffs(24, 73)
                        .addBox(-4.5F, -5.21F, -10.5F, 9.0F, 0.0F, 6.0F)
                        .texOffs(50, 71)
                        .addBox(-1.0F, -9.5F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.51F))
                        .texOffs(40, 64)
                        .addBox(5.0F, -6.0F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.51F))
                        .texOffs(50, 68)
                        .addBox(-1.0F, -9.0F, -1.0F, 2.0F, 1.0F, 2.0F)
                        .texOffs(50, 64)
                        .addBox(-0.5F, -11.0F, -0.5F, 1.0F, 3.0F, 1.0F)
                        .texOffs(24, 64)
                        .addBox(-2.0F, -11.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                        .texOffs(0, 76)
                        .addBox(-5.0F, -15.0F, -5.0F, 10.0F, 5.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create()
                        .texOffs(16, 48)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        modelPartData.addOrReplaceChild(
                PartNames.LEFT_ARM,
                CubeListBuilder.create()
                        .texOffs(40, 48)
                        .mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
                        .mirror(false),
                PartPose.offset(5.0F, 2.0F, 0.0F)
        );
        
        modelPartData.addOrReplaceChild(
                PartNames.RIGHT_ARM,
                CubeListBuilder.create()
                        .texOffs(40, 48)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F)
        );

        return LayerDefinition.create(modelData, 96, 96);
    }


    public static LayerDefinition getSlimLayerDefinition() {
        MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f);
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(58, 66)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(1.0F))
                        .texOffs(56, 48)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.51F))
                        .texOffs(31, 79)
                        .addBox(-4.5F, 0.3163F, -4.5F, 9.0F, 0.0F, 9.0F)
                        .texOffs(-12, 64)
                        .addBox(-6.0F, -5.21F, -6.0F, 12.0F, 0.0F, 12.0F)
                        .texOffs(24, 73)
                        .addBox(-4.5F, -5.21F, -10.5F, 9.0F, 0.0F, 6.0F)
                        .texOffs(50, 71)
                        .addBox(-1.0F, -9.5F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.51F))
                        .texOffs(40, 64)
                        .addBox(5.0F, -6.0F, -1.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.51F))
                        .texOffs(50, 68)
                        .addBox(-1.0F, -9.0F, -1.0F, 2.0F, 1.0F, 2.0F)
                        .texOffs(50, 64)
                        .addBox(-0.5F, -11.0F, -0.5F, 1.0F, 3.0F, 1.0F)
                        .texOffs(24, 64)
                        .addBox(-2.0F, -11.0F, -2.0F, 4.0F, 3.0F, 4.0F)
                        .texOffs(0, 76)
                        .addBox(-5.0F, -15.0F, -5.0F, 10.0F, 5.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        
        modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create()
                        .texOffs(16, 48)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        
        modelPartData.addOrReplaceChild(
                PartNames.LEFT_ARM,
                CubeListBuilder.create()
                        .texOffs(0, 48)
                        .mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.35F))
                        .mirror(false),
                PartPose.offset(5.0F, 2.0F, 0.0F)
        );

        modelPartData.addOrReplaceChild(
                PartNames.RIGHT_ARM,
                CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F)
        );

        return LayerDefinition.create(modelData, 96, 96);
    }
}

