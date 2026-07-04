package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EtceteraArmorMaterials {

    public static final Holder<ArmorMaterial> TIDAL = register("tidal", 35, makeDefense(3, 6, 8, 3), 15, EtceteraSoundEvents.ITEM_TIDEL_ARMOR_EQUIP, 0.0f, 0.0f, () -> Ingredient.of(Items.NAUTILUS_SHELL));
    public static final Holder<ArmorMaterial> SILK = register("silk", 20, makeDefense(2, 5, 6, 2), 15, EtceteraSoundEvents.ITEM_ARMOR_SILK_EQUIP, 0.0f, 0.0f, () -> Ingredient.of(Items.COBWEB));
    public static final Holder<ArmorMaterial> ADVENTURER = register("adventurer", 20, makeDefense(2, 5, 6, 2), 15, EtceteraSoundEvents.ITEM_ARMOR_ADVENTURER_EQUIP, 0.0f, 0.0f, () -> Ingredient.of(Items.IRON_INGOT));
    public static final Holder<ArmorMaterial> COTTON = register("cotton", 5, makeDefense(0, 0, 0, 0), 15, EtceteraSoundEvents.ITEM_ARMOR_EQUIP_COTTON, 0.0f, 0.0f, () -> Ingredient.of(EtceteraItems.COTTON_FLOWER.get()));

    private static Map<ArmorItem.Type, Integer> makeDefense(int boots, int leggings, int chestplate, int helmet) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.HELMET, helmet);
        return map;
    }

    private static Holder<ArmorMaterial> register(String name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> defense, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(id));

        EnumMap<ArmorItem.Type, Integer> durability = new EnumMap<>(ArmorItem.Type.class);
        durability.put(ArmorItem.Type.BOOTS, 13 * durabilityMultiplier);
        durability.put(ArmorItem.Type.LEGGINGS, 15 * durabilityMultiplier);
        durability.put(ArmorItem.Type.CHESTPLATE, 16 * durabilityMultiplier);
        durability.put(ArmorItem.Type.HELMET, 11 * durabilityMultiplier);

        ArmorMaterial material = new ArmorMaterial(defense, enchantability, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(equipSound), repairIngredient, layers, toughness, knockbackResistance);
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, id, material);
    }
}