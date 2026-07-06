package com.ninni.etcetera.mixin.easyanvils;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "fuzs.easyanvils.network.client.C2SNameTagUpdateMessage$1", remap = false)
public class C2SNameTagUpdateMessageMixin {

    @WrapOperation(
            method = "handle(Lfuzs/easyanvils/network/client/C2SNameTagUpdateMessage;Lnet/minecraft/world/entity/player/Player;Ljava/lang/Object;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
            remap = false
    )
    private boolean easyAnvils$allowItemLabel(ItemStack stack, Item item, Operation<Boolean> original) {
        return original.call(stack, item) || stack.is(EtceteraItems.ITEM_LABEL.get());
    }
}
