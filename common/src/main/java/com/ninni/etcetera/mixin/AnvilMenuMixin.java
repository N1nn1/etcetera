package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    @Shadow
    @Final
    private DataSlot cost;

    @Shadow
    private int repairItemCountCost;

    public AnvilMenuMixin(@Nullable MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void hammeringAnvil(CallbackInfo ci) {
        ItemStack inputStack = this.inputSlots.getItem(0);
        ItemStack secondStack = this.inputSlots.getItem(1);

        if (secondStack.is(EtceteraItems.ITEM_LABEL.get()) && secondStack.has(DataComponents.CUSTOM_NAME) && !inputStack.isEmpty()) {
            ci.cancel();
            ItemStack outputStack = inputStack.copy();

            CustomData customData = outputStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag nbt = customData.copyTag();
            String labelText = secondStack.getHoverName().getString();

            if (!nbt.contains("Label1")) {
                nbt.putString("Label1", labelText);
                this.cost.set(1);
            } else if (!nbt.contains("Label2")) {
                nbt.putString("Label2", labelText);
                this.cost.set(2);
            } else if (!nbt.contains("Label3")) {
                nbt.putString("Label3", labelText);
                this.cost.set(3);
            } else if (!nbt.contains("Label4")) {
                nbt.putString("Label4", labelText);
                this.cost.set(4);
            } else {
                this.resultSlots.setItem(0, ItemStack.EMPTY);
                this.cost.set(0);
                return;
            }

            outputStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

            this.repairItemCountCost = 1;
            this.resultSlots.setItem(0, outputStack);
            this.broadcastChanges();
        }

        if (inputStack.is(EtceteraItems.GOLDEN_GOLEM.get()) && secondStack.is(Items.GOLD_INGOT)) {
            CustomData customData = inputStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag inputNbt = customData.copyTag();

            if (inputNbt.contains("HealingAmount") && inputNbt.getInt("HealingAmount") < 10) {
                ci.cancel();
                ItemStack outputStack = inputStack.copy();

                inputNbt.putInt("HealingAmount", Math.min(inputNbt.getInt("HealingAmount") + 2, 10));
                if (inputNbt.contains("Broken") && inputNbt.getBoolean("Broken")) {
                    inputNbt.putBoolean("Broken", false);
                }

                outputStack.set(DataComponents.CUSTOM_DATA, CustomData.of(inputNbt));

                this.cost.set(2);
                this.repairItemCountCost = 1;
                this.resultSlots.setItem(0, outputStack);
                this.broadcastChanges();
            }
        }
    }
}