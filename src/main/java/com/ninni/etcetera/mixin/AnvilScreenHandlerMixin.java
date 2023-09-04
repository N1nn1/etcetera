package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow @Final private Property levelCost;
    @Shadow private int repairItemUsage;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void hammeringAnvil(CallbackInfo ci) {
        ItemStack itemStack = this.input.getStack(0);
        ItemStack itemStack2 = this.input.getStack(1);


        if (itemStack2.isOf(EtceteraItems.ITEM_LABEL) && itemStack2.hasCustomName() && !itemStack.isEmpty()) {
            ci.cancel();
            ItemStack itemStack3 = itemStack.copy();
            NbtCompound nbt = itemStack3.getOrCreateNbt();
            String labelText = itemStack2.getName().getString();

            if (itemStack3.hasNbt() && !itemStack3.getNbt().contains("LabelTop") && !itemStack3.getNbt().contains("LabelBottom")) {
                nbt.putString("LabelTop", labelText);
                this.levelCost.set(1);
                this.repairItemUsage = 1;
                this.output.setStack(0, itemStack3);
            } else if (itemStack3.hasNbt() && itemStack3.getNbt().contains("LabelTop") && !itemStack3.getNbt().contains("LabelBottom")) {
                nbt.putString("LabelBottom", labelText);
                this.levelCost.set(1);
                this.repairItemUsage = 1;
                this.output.setStack(0, itemStack3);
            } else {
                this.output.setStack(0, ItemStack.EMPTY);
                this.levelCost.set(0);
            }
            this.sendContentUpdates();
        }

    }
}
