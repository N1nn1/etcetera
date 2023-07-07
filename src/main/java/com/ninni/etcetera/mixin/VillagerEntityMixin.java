package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "canGather", at = @At("HEAD"), cancellable = true)
    private void hammeringAnvil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue((stack.isIn(EtceteraTags.VILLAGER_CAN_PICKUP) || this.getVillagerData().getProfession().gatherableItems().contains(stack.getItem())) && this.getInventory().canInsert(stack));
    }
}
