package com.ninni.etcetera.client.render.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class RubberChickenAnimations {

    public static final Animation SQUEEZE = Animation.Builder.create(0.5f)
            .addBoneAnimation("body",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.041676664f, AnimationHelper.createScalingVector(1f, 0.8f, 1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.4583433f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC))).build();
}
