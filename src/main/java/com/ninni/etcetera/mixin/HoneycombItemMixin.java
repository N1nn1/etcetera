package com.ninni.etcetera.mixin;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ninni.etcetera.registry.EtceteraBlocks;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Supplier;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {

    @Inject(at = @At("HEAD"), method = "getWaxed", cancellable = true)
    private static void E$getWaxed(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (state.is(EtceteraBlocks.CRUMBLING_STONE.get())) {
            cir.setReturnValue(Optional.of(EtceteraBlocks.WAXED_CRUMBLING_STONE.get().withPropertiesOf(state)));
        }
    }

}
