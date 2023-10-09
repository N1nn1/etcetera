package com.ninni.etcetera.mixin;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Comparator;

@Mixin(ItemGroups.class)
public interface ItemGroupsAccessor {
    @Accessor
    static Comparator<RegistryEntry<PaintingVariant>> getPAINTING_VARIANT_COMPARATOR() {
        throw new UnsupportedOperationException();
    }
}
