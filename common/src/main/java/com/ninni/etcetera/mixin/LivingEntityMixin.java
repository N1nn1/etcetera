package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Unique
    public int etcetera$footstepCooldown = 40;

    @Shadow
    private Optional<BlockPos> lastClimbablePos;

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot var1);

    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
    private void makeCobwebClimbable(CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = this.blockPosition();
        BlockState blockState = this.level().getBlockState(blockPos);
        if (blockState.is(Blocks.COBWEB) && this.getItemBySlot(EquipmentSlot.LEGS).is(EtceteraItems.SILKEN_SLACKS.get())) {
            this.lastClimbablePos = Optional.of(blockPos);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void addFootsteps(CallbackInfo ci) {
        if (etcetera$footstepCooldown > 0) etcetera$footstepCooldown--;
        if (!this.level().isClientSide && etcetera$footstepCooldown == 0 && this.getItemBySlot(EquipmentSlot.FEET).is(EtceteraItems.ADVENTURERS_BOOTS.get()) && this.onGround() && !this.isInWater()) {
            BlockState blockState = EtceteraBlocks.FOOTSTEPS.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, this.getDirection());
            BlockPos blockPos = this.blockPosition();
            if ((this.level().getBlockState(blockPos).isAir() || this.level().getBlockState(blockPos).canBeReplaced()) && this.level().getBlockState(blockPos.below()).isCollisionShapeFullBlock(this.level(), blockPos.below())) {
                this.level().setBlockAndUpdate(blockPos, blockState);
                this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
            }
            etcetera$footstepCooldown = 40;
        }
    }
}