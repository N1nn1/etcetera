package com.ninni.etcetera.mixin;

import com.ninni.etcetera.registry.EtceteraTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ReputationEventHandler;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager implements ReputationEventHandler, VillagerDataHolder {

    public VillagerEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }


    @Inject(method = "wantsToPickUp", at = @At("HEAD"), cancellable = true)
    private void hammeringAnvil(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        cir.setReturnValue((stack.is(EtceteraTags.VILLAGER_CAN_PICKUP) || this.getVillagerData().getProfession().requestedItems().contains(stack.getItem())) && this.getInventory().canAddItem(stack));
    }
}
