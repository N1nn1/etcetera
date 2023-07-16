package com.ninni.etcetera.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.etcetera.client.gui.HandbellItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


/**
 * This is my code now ~ Orcinus
 */
@OnlyIn(Dist.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER), method = "render", argsOnly = true)
    private BakedModel createModel(BakedModel value, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BakedModel model) {
        ModelManager models = Minecraft.getInstance().getModelManager();
        return HandbellItemRenderer.isInventory(stack, renderMode) ? models.getModel(HandbellItemRenderer.INVENTORY_MODEL_ID) : value;
    }

}
