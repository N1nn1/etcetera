package com.ninni.etcetera.registry;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum EtceteraArmorMaterials implements ArmorMaterial {

    TIDAL("tidal", 35, new int[]{3, 6, 8, 3}, 15, EtceteraSoundEvents.ITEM_TIDEL_ARMOR_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.NAUTILUS_SHELL)),
    SILK("silk", 20, new int[]{2, 5, 6, 2}, 15, EtceteraSoundEvents.ITEM_ARMOR_SILK_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.COBWEB)),
    ADVENTURER("adventurer", 20, new int[]{3, 5, 6, 2}, 15, EtceteraSoundEvents.ITEM_ARMOR_ADVENTURER_EQUIP, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.IRON_INGOT));

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairIngredientSupplier;

    EtceteraArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<>(repairIngredientSupplier);
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * this.durabilityMultiplier;
    }
    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protectionAmounts[type.getEquipmentSlot().getEntitySlotId()];
    }
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public float getToughness() {
        return this.toughness;
    }
    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
