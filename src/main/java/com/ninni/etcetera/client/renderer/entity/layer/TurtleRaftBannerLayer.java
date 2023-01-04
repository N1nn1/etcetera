package com.ninni.etcetera.client.renderer.entity.layer;

import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(value=EnvType.CLIENT)
public class TurtleRaftBannerLayer extends FeatureRenderer<TurtleRaftEntity, TurtleRaftModel> {

    public TurtleRaftBannerLayer(FeatureRendererContext<TurtleRaftEntity, TurtleRaftModel> parent) {
        super(parent);
    }

    @Override
    public void render(MatrixStack poseStack, VertexConsumerProvider source, int packedLight, TurtleRaftEntity entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack = entity.getBanner();
        if (!itemstack.isEmpty()) {
            poseStack.push();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(-0.65D, 0.4D, 0.0D);
            poseStack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(90.0F));
            poseStack.scale(0.625F, -0.625F, -0.625F);
            MinecraftClient.getInstance().getItemRenderer().renderItem(null, itemstack, ModelTransformation.Mode.HEAD, false, poseStack, source, entity.world, packedLight, OverlayTexture.DEFAULT_UV, entity.getId() + ModelTransformation.Mode.HEAD.ordinal());
            poseStack.pop();
        }
    }
}
