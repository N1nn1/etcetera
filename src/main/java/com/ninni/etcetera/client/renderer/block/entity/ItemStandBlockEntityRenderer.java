package com.ninni.etcetera.client.renderer.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ninni.etcetera.block.entity.ItemStandBlockEntity;
import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemStandBlockEntityRenderer implements BlockEntityRenderer<ItemStandBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ItemStandBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ItemStandBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Direction direction = entity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        NonNullList<ItemStack> defaultedList = entity.getItemsDisplayed();
        for (int l = 0; l < defaultedList.size(); ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack == ItemStack.EMPTY) continue;
            matrices.pushPose();
            Direction direction2 = Direction.from2DDataValue((l + direction.get2DDataValue()) % 4);
            float d = -direction2.toYRot();
            matrices.translate(0.5, 0.45, 0.5);
            matrices.mulPose(Axis.YP.rotationDegrees(d));
            matrices.translate(0, 0, -0.1);
            matrices.mulPose(Axis.XP.rotationDegrees(20.0f));
            matrices.scale(0.65f, 0.65f, 0.65f);

            this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, entity.getBlockState().is(EtceteraBlocks.GLOW_ITEM_STAND.get()) ? 0xF000F0 : light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.getLevel(), (int)entity.getBlockPos().asLong());
            matrices.popPose();
        }
    }
}
