package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public int footstepCooldown = 20 * 2;
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

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void addFootsteps(CallbackInfo ci) {


        if (footstepCooldown > 0) footstepCooldown--;
        if (!this.getWorld().isClient && footstepCooldown == 0 && this.getEquippedStack(EquipmentSlot.FEET).isOf(EtceteraItems.ADVENTURERS_BOOTS) && this.isOnGround() && !this.touchingWater) {
            BlockState blockState = EtceteraBlocks.FOOTSTEPS.getDefaultState().with(HorizontalFacingBlock.FACING, this.getMovementDirection());
                BlockPos blockPos = this.getBlockPos();
                if ((this.getWorld().getBlockState(blockPos).isAir() || this.getWorld().getBlockState(blockPos).isReplaceable()) && this.getWorld().getBlockState(blockPos.down()).isFullCube(this.getWorld(), blockPos.down())) {
                    this.getWorld().setBlockState(blockPos, blockState);
                    this.getWorld().emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(this, blockState));
                }
            footstepCooldown = 20 * 2;
        }

    }
}
