package com.ninni.etcetera.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.etcetera.client.gui.HandbellItemRenderer;
import com.ninni.etcetera.item.HandbellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class FabricItemRendererMixin {
    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private BakedModel createModel(BakedModel value, ItemStack stack, ItemDisplayContext displayContext, boolean leftHanded, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (stack.getItem() instanceof HandbellItem) {
            ModelManager manager = Minecraft.getInstance().getModelManager();
            if (!HandbellItemRenderer.isInventory(stack, displayContext)) {
                return manager.getModel(HandbellItemRenderer.INVENTORY_IN_HAND_MODEL_ID.id());
            } else {
                return manager.getModel(HandbellItemRenderer.INVENTORY_MODEL_ID);
            }
        }
        return value;
    }
}
