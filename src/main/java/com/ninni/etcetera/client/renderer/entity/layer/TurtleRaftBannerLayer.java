package com.ninni.etcetera.client.renderer.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurtleRaftBannerLayer extends RenderLayer<TurtleRaftEntity, TurtleRaftModel> {

    public TurtleRaftBannerLayer(RenderLayerParent<TurtleRaftEntity, TurtleRaftModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource source, int packedLight, TurtleRaftEntity entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack itemstack = entity.getBanner();
        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(-0.1D, -0.09D, 0.0D);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            poseStack.scale(0.625F, -0.625F, -0.625F);
            Minecraft.getInstance().getItemRenderer().renderStatic(null, itemstack, ItemDisplayContext.HEAD, false, poseStack, source, entity.level(), packedLight, OverlayTexture.NO_OVERLAY, entity.getId() + ItemDisplayContext.HEAD.ordinal());
            poseStack.popPose();
        }
    }

}
