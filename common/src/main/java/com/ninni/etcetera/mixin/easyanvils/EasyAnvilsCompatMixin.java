package com.ninni.etcetera.mixin.easyanvils;

import com.ninni.etcetera.mixin.AnvilMenuAccessor;
import com.ninni.etcetera.registry.EtceteraItems;
import fuzs.easyanvils.world.inventory.ModAnvilMenu;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModAnvilMenu.class, remap = false)
public abstract class EasyAnvilsCompatMixin extends ItemCombinerMenu {

    public EasyAnvilsCompatMixin(@Nullable MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "createResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void easyAnvils$hammeringAnvil(ItemStack leftInput, ItemStack rightInput, String itemName, CallbackInfo ci) {

        if (rightInput.is(EtceteraItems.ITEM_LABEL.get()) && rightInput.has(DataComponents.CUSTOM_NAME) && !leftInput.isEmpty()) {
            ci.cancel();
            ItemStack outputStack = leftInput.copy();

            CustomData customData = outputStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag nbt = customData.copyTag();
            String labelText = rightInput.getHoverName().getString();

            AnvilMenuAccessor accessor = (AnvilMenuAccessor) this;
            DataSlot costSlot = accessor.getCost();

            if (!nbt.contains("Label1")) {
                nbt.putString("Label1", labelText);
                costSlot.set(1);
            } else if (!nbt.contains("Label2")) {
                nbt.putString("Label2", labelText);
                costSlot.set(2);
            } else if (!nbt.contains("Label3")) {
                nbt.putString("Label3", labelText);
                costSlot.set(3);
            } else if (!nbt.contains("Label4")) {
                nbt.putString("Label4", labelText);
                costSlot.set(4);
            } else {
                this.resultSlots.setItem(0, ItemStack.EMPTY);
                costSlot.set(0);
                return;
            }

            outputStack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

            accessor.setRepairItemCountCost(1);
            this.resultSlots.setItem(0, outputStack);
            this.broadcastChanges();
        }

        if (leftInput.is(EtceteraItems.GOLDEN_GOLEM.get()) && rightInput.is(Items.GOLD_INGOT)) {
            CustomData customData = leftInput.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag inputNbt = customData.copyTag();

            if (inputNbt.contains("HealingAmount") && inputNbt.getInt("HealingAmount") < 10) {
                ci.cancel();
                ItemStack outputStack = leftInput.copy();

                inputNbt.putInt("HealingAmount", Math.min(inputNbt.getInt("HealingAmount") + 2, 10));
                if (inputNbt.contains("Broken") && inputNbt.getBoolean("Broken")) {
                    inputNbt.putBoolean("Broken", false);
                }

                outputStack.set(DataComponents.CUSTOM_DATA, CustomData.of(inputNbt));

                AnvilMenuAccessor accessor = (AnvilMenuAccessor) this;
                accessor.getCost().set(2);
                accessor.setRepairItemCountCost(1);
                this.resultSlots.setItem(0, outputStack);
                this.broadcastChanges();
            }
        }
    }
}
