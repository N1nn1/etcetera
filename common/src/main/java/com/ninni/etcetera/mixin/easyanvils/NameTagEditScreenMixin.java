package com.ninni.etcetera.mixin.easyanvils;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.ninni.etcetera.registry.EtceteraItems;
import fuzs.easyanvils.client.gui.screens.inventory.NameTagEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = NameTagEditScreen.class, remap = false)
public class NameTagEditScreenMixin {

    @WrapOperation(
            method = "<init>(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/network/chat/Component;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getDescription()Lnet/minecraft/network/chat/Component;")
    )
    private static Component easyAnvils$getItemLabelDescription(Item item, Operation<Component> original, InteractionHand hand, Component title) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack stack = mc.player.getItemInHand(hand);
            if (stack.is(EtceteraItems.ITEM_LABEL.get())) {
                return EtceteraItems.ITEM_LABEL.get().getDescription();
            }
        }
        return original.call(item);
    }

    @WrapOperation(
            method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack easyAnvils$renderItemLabelIcon(ItemLike item, Operation<ItemStack> original) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            ItemStack main = mc.player.getMainHandItem();
            if (main.is(EtceteraItems.ITEM_LABEL.get())) {
                return new ItemStack(EtceteraItems.ITEM_LABEL.get());
            }
            ItemStack off = mc.player.getOffhandItem();
            if (off.is(EtceteraItems.ITEM_LABEL.get())) {
                return new ItemStack(EtceteraItems.ITEM_LABEL.get());
            }
        }
        return original.call(item);
    }
}
