package com.ninni.etcetera.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ninni.etcetera.registry.EtceteraArmorMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class SweaterItem extends ArmorItem {

    public SweaterItem(Settings settings) {
        super(EtceteraArmorMaterials.COTTON, ArmorItem.Type.CHESTPLATE, settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return ImmutableMultimap.of();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
