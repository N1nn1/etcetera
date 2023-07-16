package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void hammeringAnvil(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_48809_, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).is(EtceteraItems.HAMMER.get())) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }

}
