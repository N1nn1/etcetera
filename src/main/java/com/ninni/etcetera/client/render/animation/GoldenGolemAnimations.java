package com.ninni.etcetera.client.render.animation;

import com.ninni.etcetera.client.model.GoldenGolemModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class GoldenGolemAnimations {

    public static final Animation GRANT = Animation.Builder.create(1.3f)
            .addBoneAnimation(HEAD,
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(22.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5834334f, AnimationHelper.createRotationalVector(-40f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation(ARMS,
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-27.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation(RIGHT_WING,
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(37.5f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(37.5f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(-60.22f, -63.46f, 18.09f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(-110.28f, -70.31f, 45.22f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-67.5f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createRotationalVector(0f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation(LEFT_WING,
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(37.5f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(37.5f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(-60.22f, 63.46f, -18.09f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(-110.28f, 70.31f, -45.22f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-67.5f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createRotationalVector(0f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation(GoldenGolemModel.ALL,
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 1f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5834334f, AnimationHelper.createTranslationalVector(0f, 3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createTranslationalVector(0f, 6f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation(GoldenGolemModel.ALL,
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.3433333f, AnimationHelper.createRotationalVector(0f, -42.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5834334f, AnimationHelper.createRotationalVector(0f, 158.75f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(0f, 360f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 400f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3f, AnimationHelper.createRotationalVector(0f, 360f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
}
