package com.ninni.etcetera.mixin;

import com.ninni.etcetera.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin {

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void etcetera$useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<ItemInteractionResult> cir) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        CompoundTag tag = customData.copyTag();

        if (world.getBlockState(pos).is(Blocks.WATER_CAULDRON) && (tag.contains("Label1") || tag.contains("Label2") || tag.contains("Label3") || tag.contains("Label4"))) {
            ItemStack itemStack2 = stack.copy();

            tag.remove("Label1");
            tag.remove("Label2");
            tag.remove("Label3");
            tag.remove("Label4");

            itemStack2.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            player.setItemInHand(hand, itemStack2);
            LayeredCauldronBlock.lowerFillLevel(world.getBlockState(pos), world, pos);

            cir.setReturnValue(ItemInteractionResult.sidedSuccess(world.isClientSide));
        }
    }
}