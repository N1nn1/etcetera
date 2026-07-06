package com.ninni.etcetera.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnvilMenu.class)
public interface AnvilMenuAccessor {

    @Accessor("cost")
    DataSlot getCost();

    @Accessor("repairItemCountCost")
    void setRepairItemCountCost(int repairItemCountCost);
}
