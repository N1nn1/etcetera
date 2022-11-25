package com.ninni.etcetera.client.renderer.block.entity;

import com.ninni.etcetera.block.entity.ItemStandBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class ItemStandBlockEntityRenderer implements BlockEntityRenderer<ItemStandBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ItemStandBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ItemStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction direction = entity.getCachedState().get(CampfireBlock.FACING);
        DefaultedList<ItemStack> defaultedList = entity.getItemsDisplayed();
        int k = (int)entity.getPos().asLong();
        for (int l = 0; l < defaultedList.size(); ++l) {
            ItemStack itemStack = defaultedList.get(l);
            if (itemStack == ItemStack.EMPTY) continue;
            matrices.push();
            Direction direction2 = Direction.fromHorizontal((l + direction.getHorizontal()) % 4);
            float d = -direction2.asRotation();
            matrices.translate(0.5, 0.45, 0.5);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(d));
            matrices.translate(0, 0, -0.1);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(20.0f));
            matrices.scale(0.65f, 0.65f, 0.65f);

            this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, k + l);
            matrices.pop();
        }
    }
}
