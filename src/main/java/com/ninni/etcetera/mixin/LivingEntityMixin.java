package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    @Shadow private Optional<BlockPos> climbingPos;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "isClimbing", at = @At("HEAD"), cancellable = true)
    private void makeCobwebClimbable(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getBlockStateAtPos();
        if (blockState.isOf(Blocks.COBWEB) && this.getEquippedStack(EquipmentSlot.LEGS).isOf(EtceteraItems.SILKEN_SLACKS)) {
            this.climbingPos = Optional.of(blockPos);
            cir.setReturnValue(true);
        }
    }
}
